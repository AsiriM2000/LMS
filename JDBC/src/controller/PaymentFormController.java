package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.AllDetailDatabase;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import module.AllDetail;
import module.Payment;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.AllDetailTm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PaymentFormController implements Initializable {
    public TextField txtRegId;
    public JFXComboBox cmbCourseId;
    public JFXComboBox cmbMonth;
    public TextField txtAmount;
    public AnchorPane paymentAnchor;
    public TextField txtRegisterId;
    public TextField txtStudentName;
    public TextField txtCourse;
    public TextField txtMonthlyPayment;
    public TextField txtAllPayment;
    public TextField txtId;
    public TableView tblAllDetails;
    public TableColumn colRegId;
    public TableColumn colName;
    public TableColumn colCourse;
    public TableColumn colMonthPayment;
    public TableColumn colAllPayment;
    public TableColumn colOption;
    public JFXButton btnPayment;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colRegId.setCellValueFactory(new PropertyValueFactory<>("register_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colMonthPayment.setCellValueFactory(new PropertyValueFactory<>("month_fee"));
        colAllPayment.setCellValueFactory(new PropertyValueFactory<>("all_fee"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));


        try {
            cmbCourseId.setItems(FXCollections.observableArrayList(CourseCrudController.getCourseIds()));
            cmbMonth.setItems(FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        notifyAllData();

        Pattern reg_id = Pattern.compile("^(H00-)[0-9]{3,5}$");
        map.put(txtRegId,reg_id);
    }
    public void addPayment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Payment p = new Payment(
                txtRegId.getText(),
                (String) cmbCourseId.getValue(),
                (String) cmbMonth.getValue(),
                Double.parseDouble(txtAmount.getText())
        );

        if (CrudUtil.execute("INSERT INTO Payment VALUES (?,?,?,?)",p.getRegister_id(),p.getCourse_id(),p.getMonth(),p.getMonth_fee())){
                new Alert(Alert.AlertType.CONFIRMATION,"Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Failed!").show();
        }
    }

    public void PaymentViewOnAction(ActionEvent actionEvent) throws IOException {
        DBConnection.anchorPane = paymentAnchor;
        URL resorce = getClass().getResource("../View/PaymentViewForm.fxml");
        Parent root = FXMLLoader.load(resorce);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Register WHERE register_id =?",txtId.getText());
        if (result.next()){
            txtRegisterId.setText(result.getString(1));
            txtCourse.setText(result.getString(2));
            txtStudentName.setText(result.getString(4));
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Registered Id").show();
        }

        ResultSet set = CrudUtil.execute("SELECT * FROM Payment WHERE register_id =?",txtId.getText());
        while (set.next()){
            txtMonthlyPayment.setText(set.getString(4));
        }
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT SUM(month_fee) FROM Payment WHERE register_id =?",txtId.getText());
            while (resultSet.next()){
                txtAllPayment.setText(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void notifyAllData(){
        ObservableList<AllDetailTm>obList = FXCollections.observableArrayList();
        for (AllDetail t : AllDetailDatabase.allDetailTms){
            Button button = new Button("Delete");
            button.setStyle("-fx-background-color: red");
            AllDetailTm tm = new AllDetailTm(
                    t.getRegister_id(),t.getStudent_name(),t.getCourse_name(),t.getMonth_fee(),t.getAll_fee(),button
            );
            obList.add(tm);

            button.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are your Sure",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType>buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){
                    AllDetailDatabase.allDetailTms.remove(t);
                    obList.remove(tm);
                }
            });
        }
        tblAllDetails.setItems(obList);

    }

    public void LoadOnAction(ActionEvent actionEvent) {
        String reg_Id = txtRegisterId.getText();
        String student_name = txtStudentName.getText();
        String course_name = txtCourse.getText();
        double month_payment  = Double.parseDouble(txtMonthlyPayment.getText());
        double all_payment = Double.parseDouble(txtAllPayment.getText());

        AllDetail allDetail = new AllDetail(reg_Id,student_name,course_name,month_payment,all_payment);
        AllDetailDatabase.allDetailTms.add(allDetail);
        notifyAllData();
    }

    public void printMouseClicked(MouseEvent event){
        String id = txtRegisterId.getText();
        String name = txtStudentName.getText();
        String course = txtCourse.getText();
        double fee  = Double.parseDouble(txtMonthlyPayment.getText());

        HashMap map = new HashMap();
        map.put("id",id);
        map.put("name",name);
        map.put("course",course);
        map.put("fee",fee);


        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/PaymentReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }


    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnPayment);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnPayment);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }
}
