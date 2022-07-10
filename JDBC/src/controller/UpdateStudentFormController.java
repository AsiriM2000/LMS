package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import module.Register;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateStudentFormController {
    public TextField txtRegId;
    public TextField txtCourse;
    public TextField txtStudentName;
    public TextField txtEmail;
    public TextField txtDateOfBirth;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtOl;
    public TextField txtAl;
    public TextField txtRegisterFee;
    public AnchorPane updateAnchor;
    public TextField txtBatchName;
    public JFXButton btnUpdate;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void txtIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet result =  CrudUtil.execute("SELECT * FROM Register WHERE register_id=?",txtRegId.getText());
        if (result.next()){
            txtCourse.setText(result.getString(2));
            txtBatchName.setText(result.getString(3));
            txtStudentName.setText(result.getString(4));
            txtEmail.setText(result.getString(5));
            txtDateOfBirth.setText(result.getString(6));
            txtAddress.setText(result.getString(7));
            txtCity.setText(result.getString(8));
            txtOl.setText(result.getString(9));
            txtAl.setText(result.getString(10));
            txtRegisterFee.setText(result.getString(11));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Set!").show();
        }

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
        map.put(txtRegisterFee,reg_fee);
    }

    public void btnUpdateStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Register r = new Register(
                txtRegId.getText(),
                txtCourse.getText(),
                txtBatchName.getText(),
                txtStudentName.getText(),
                txtEmail.getText(),
                txtDateOfBirth.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtOl.getText(),
                txtAl.getText(),
                Double.parseDouble(txtRegisterFee.getText())


        );
        boolean setData = CrudUtil.execute("UPDATE Register SET course_name =?,batch_name =?,student_name =?,email =?,dob =?,address =?,city =?,ol =?,al =?,amount =? WHERE register_id =?",
                    r.getCourse_name(),r.getBatch_name(),r.getStudent_name(),r.getEmail(),r.getDob(),r.getAddress(),r.getCity(),r.getOl(),r.getAl(),r.getAmount(),r.getRegister_id()
                );
        if (setData){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
        }

        clearData();
    }

    private void clearData() {
        txtRegId.clear();
        txtCourse.clear();
        txtBatchName.clear();
        txtStudentName.clear();
        txtEmail.clear();
        txtDateOfBirth.clear();
        txtAddress.clear();
        txtCity.clear();
        txtOl.clear();
        txtAl.clear();
        txtRegisterFee.clear();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        updateAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"));
        updateAnchor.getChildren().add(parent);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnUpdate);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnUpdate);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }
}
