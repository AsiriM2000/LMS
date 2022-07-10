package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteStudentFormController {
    public AnchorPane deleteAnchor;
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
    public TextField txtBatchName;

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
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        deleteAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"));
        deleteAnchor.getChildren().add(parent);
    }

    public void btnDeleteStudentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("DELETE FROM Register WHERE register_id =?",txtRegId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Delete Failed!").show();
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
}
