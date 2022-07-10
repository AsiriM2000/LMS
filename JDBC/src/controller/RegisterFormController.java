package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import module.Register;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterFormController implements Initializable {
    public TextField txtRegId;
    public TextField txtStudentName;
    public TextField txtEmail;
    public JFXComboBox cmbCourse;
    public TextField txtAddress;
    public TextField txtCity;
    public JFXComboBox cmbOl;
    public JFXComboBox cmbAl;
    public JFXButton btnRegisterStudent;
    public JFXButton btnUpdateStudent;
    public JFXButton btnDeleteStudent;
    public DatePicker txtDateOfBirthS;
    public TextField txtAmount;
    public AnchorPane registerAnchor;
    public JFXButton btnAllRegisterStudent;
    public JFXComboBox cmbBatch;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ObservableList<String> course = FXCollections.observableArrayList(CourseCrudController.getCourseNames());
            cmbCourse.setItems(course);

            ObservableList<String> batch = FXCollections.observableArrayList(BatchCrudController.getBatchName());
            cmbBatch.setItems(batch);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<String> olAndAl = FXCollections.observableArrayList("Yes","No");

        cmbOl.setItems(olAndAl);
        cmbAl.setItems(olAndAl);


        btnRegisterStudent.setOnMouseClicked(event -> {
            try {
                registerStudent();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            clearText();
        });

        btnUpdateStudent.setOnMouseClicked(event -> {
            try {
                updateStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnDeleteStudent.setOnMouseClicked(event -> {
            try {
                deleteStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnAllRegisterStudent.setOnMouseClicked(event -> {
            try {
                loadAllRegisterStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Pattern reg_id = Pattern.compile("^(H00-)[0-9]{3,5}$");
        Pattern name = Pattern.compile("^[A-z]+ [a-zA-Z]+$");
        Pattern email = Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+$");
        Pattern address = Pattern.compile("^[0-9/\\\\\\/# ,a-zA-Z]+[ ,]+[0-9\\\\\\/#, a-zA-Z]+$");
        Pattern city = Pattern.compile("^(?:[A-Z][a-z.-]{4,}+[ ]?)+$");
        Pattern reg_fee = Pattern.compile("^[0-9]{4,}$");

        map.put(txtRegId,reg_id);
        map.put(txtStudentName,name);
        map.put(txtEmail,email);
        map.put(txtAddress,address);
        map.put(txtCity,city);
        map.put(txtAmount,reg_fee);
    }

    private void registerStudent() throws SQLException, ClassNotFoundException {
        Register reg = new Register(
                txtRegId.getText(),
                (String) cmbCourse.getValue(),
                (String) cmbBatch.getValue(),
                txtStudentName.getText(),
                txtEmail.getText(),
                String.valueOf(txtDateOfBirthS.getValue()),
                txtAddress.getText(),
                txtCity.getText(),
                (String) cmbOl.getValue(),
                (String) cmbAl.getValue(),
                Double.parseDouble(txtAmount.getText())


        );
        if (CrudUtil.execute("INSERT INTO Register VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    reg.getRegister_id(),
                    reg.getCourse_name(),
                    reg.getBatch_name(),
                    reg.getStudent_name(),
                    reg.getEmail(),
                    reg.getDob(),
                    reg.getAddress(),
                    reg.getCity(),
                    reg.getOl(),
                    reg.getAl(),
                    reg.getAmount()
            )){
                new Alert(Alert.AlertType.CONFIRMATION,"Register Success!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Empty Set").show();
            }

    }
    private void clearText(){
        txtRegId.clear();
        txtStudentName.clear();
        txtEmail.clear();
        txtAddress.clear();
        txtCity.clear();
        txtAmount.clear();

    }
    private void updateStudent() throws IOException {
        registerAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/UpdateStudentForm.fxml"));
        registerAnchor.getChildren().add(parent);
    }

    private void deleteStudent() throws IOException {
        registerAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DeleteStudentForm.fxml"));
        registerAnchor.getChildren().add(parent);
    }

    private void loadAllRegisterStudent() throws IOException {
        registerAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AllRegisterStudentForm.fxml"));
        registerAnchor.getChildren().add(parent);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnRegisterStudent);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnRegisterStudent);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }

    public void RegisterPrintMouseClicked(MouseEvent event) {

        double fee = Double.parseDouble(txtAmount.getText());
        String id = txtRegId.getText();
        String name = txtStudentName.getText();
        String course = (String) cmbCourse.getValue();
        String batch = (String) cmbBatch.getValue();

        HashMap map = new HashMap();
        map.put("register_Fee",fee);
        map.put("id",id);
        map.put("name",name);
        map.put("course",course);
        map.put("batch",batch);


        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/RegistrationReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }
}
