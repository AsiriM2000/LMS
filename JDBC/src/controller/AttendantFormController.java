package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import module.Attendant;
import util.CrudUtil;
import util.ValidationUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AttendantFormController implements Initializable {
    public TextField txtRegId;
    public TextField txtStudentName;
    public JFXComboBox cmbSubjectId;
    public DatePicker cmbDate;
    public JFXTimePicker time;
    public TextField txtSubjectName;
    public TableView tblAttendant;
    public TableColumn colRegId;
    public TableColumn colStudentName;
    public TableColumn colSubId;
    public TableColumn colSubName;
    public TableColumn colDate;
    public TableColumn colTime;
    public JFXButton btnMark;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRegId.setCellValueFactory(new PropertyValueFactory<>("register_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colSubId.setCellValueFactory(new PropertyValueFactory<>("subject_id"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("subject_name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setSubjectId();
        loadAttendantData();

        Pattern reg_id = Pattern.compile("^(H00-)[0-9]{3,5}$");
        map.put(txtRegId,reg_id);
    }

    private void loadAttendantData() {
        ObservableList<Attendant>attendants = FXCollections.observableArrayList();
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Attendant");
            while (result.next()){
                       attendants.add(new Attendant(
                               result.getString("register_id"),
                               result.getString("student_name"),
                               result.getString("subject_id"),
                               result.getString("subject_name"),
                               result.getString("date"),
                               result.getString("time")
                       )) ;
            }
            tblAttendant.setItems(attendants);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setSubjectId() {
        try {
            cmbSubjectId.setItems(FXCollections.observableArrayList(SubjectCrudController.getSubjectId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void markOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Attendant a = new Attendant(
                txtRegId.getText(),
                txtStudentName.getText(),
                (String) cmbSubjectId.getValue(),
                txtSubjectName.getText(),
                String.valueOf(cmbDate.getValue()),
                String.valueOf(time.getValue())

        );

        if (CrudUtil.execute("INSERT INTO Attendant VALUES (?,?,?,?,?,?)",a.getRegister_id(),a.getStudent_name(),a.getSubject_id(),a.getSubject_name(),a.getDate(),a.getTime())){
            new Alert(Alert.AlertType.CONFIRMATION,"Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Failed").show();
        }
        loadAttendantData();
        clearData();
    }

    private void clearData() {
        txtRegId.clear();
        txtStudentName.clear();
        txtSubjectName.clear();
    }

    public void txtOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Register WHERE register_id =?",txtRegId.getText());
        if (resultSet.next()){
            txtStudentName.setText(resultSet.getString(4));
        }else {
            new Alert(Alert.AlertType.WARNING,"Not Registered Id").show();
        }
    }

    public void cmbSubIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Subject WHERE subject_id =?",cmbSubjectId.getValue());
        while (resultSet.next()){
            txtSubjectName.setText(resultSet.getString(3));
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnMark);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnMark);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }
}
