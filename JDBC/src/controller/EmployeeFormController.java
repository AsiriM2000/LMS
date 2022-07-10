package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeFormController implements Initializable {
    public TextField txtEmpId;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtSalary;
    public AnchorPane employeeAnchor;
    public JFXButton btnAllEmployee;
    public JFXButton btnEmployee;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnAllEmployee.setOnMouseClicked(event -> {
            try {
                loadAllEmployee();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

    private void loadAllEmployee() throws IOException {
        employeeAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AllEmployeeForm.fxml"));
        employeeAnchor.getChildren().add(parent);
    }

    public void btnAddEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee e = new Employee(
                txtEmpId.getText(),txtFname.getText(),txtLname.getText(),txtEmail.getText(),txtContact.getText(),Double.parseDouble(txtSalary.getText())
        );

        if (CrudUtil.execute("INSERT INTO Employee VALUES (?,?,?,?,?,?)",e.getEmployee_id(),e.getF_name(),e.getL_name(),e.getEmail(),e.getContact(),e.getSalary())){
            new Alert(Alert.AlertType.CONFIRMATION,"Added Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Added Failed!").show();
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


    public void btnUpdateEmployee(ActionEvent actionEvent) throws IOException {
        employeeAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/UpdateEmployeeForm.fxml"));
        employeeAnchor.getChildren().add(parent);
    }

    public void btnDeleteEmployee(ActionEvent actionEvent) throws IOException {
        employeeAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeDeleteForm.fxml"));
        employeeAnchor.getChildren().add(parent);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnEmployee);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnEmployee);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }
}
