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

public class EmployeeDeleteFormController {
    public TextField txtEmpId;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtSalary;
    public AnchorPane deleteEmployeeAnchor;

    public void txtEmpIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee WHERE employee_id = ?",txtEmpId.getText());
        if (resultSet.next()){
            txtFname.setText(resultSet.getString(2));
            txtLname.setText(resultSet.getString(3));
            txtEmail.setText(resultSet.getString(4));
            txtContact.setText(resultSet.getString(5));
            txtSalary.setText(resultSet.getString(6));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Set!").show();
        }
    }

    public void DeleteEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("DELETE FROM Employee WHERE employee_id =?",txtEmpId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Success").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Delete Failed!").show();
        }

        clearData();
    }

    private void clearData() {
        txtEmpId.clear();
        txtFname.clear();
        txtLname.clear();
        txtEmail.clear();
        txtContact.clear();
        txtSalary.clear();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        deleteEmployeeAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        deleteEmployeeAnchor.getChildren().add(parent);
    }


}
