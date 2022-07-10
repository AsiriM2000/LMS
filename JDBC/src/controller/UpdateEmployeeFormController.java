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
import module.Employee;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateEmployeeFormController {

    public TextField txtEmpId;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtSalary;
    public AnchorPane employeeUpdateAnchor;
    public JFXButton btnUpdate;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void EmpIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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

        Pattern emp_id = Pattern.compile("^(E00-)[0-9]{3,5}$");
        Pattern f_name = Pattern.compile("^[A-z]{3,}$");
        Pattern l_name = Pattern.compile("^[A-z]{3,}$");
        Pattern email = Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+$");
        Pattern contact = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
        Pattern salary = Pattern.compile("^[0-9]{5,}$");

        map.put(txtEmpId,emp_id);
        map.put(txtFname,f_name);
        map.put(txtLname,l_name);
        map.put(txtEmail,email);
        map.put(txtContact,contact);
        map.put(txtSalary,salary);
    }

    public void UpdateEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee em = new Employee(
            txtEmpId.getText(),
            txtFname.getText(),
            txtLname.getText(),
            txtEmail.getText(),
            txtContact.getText(),
            Double.parseDouble(txtSalary.getText())
        );

        boolean update = CrudUtil.execute("UPDATE Employee SET f_name =?,l_name =?,email =?,contact =?,salary =? WHERE employee_id =?",em.getF_name(),em.getL_name(),
                    em.getEmail(),em.getContact(),em.getSalary(),em.getEmployee_id()
                );
        if (update){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Update Failed!").show();
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
        employeeUpdateAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        employeeUpdateAnchor.getChildren().add(parent);
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
