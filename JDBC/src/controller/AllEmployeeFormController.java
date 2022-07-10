package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import module.Employee;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllEmployeeFormController implements Initializable {
    public TableView<Employee> tblEmployee;
    public TableColumn colId;
    public TableColumn colFname;
    public TableColumn colLname;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colSalary;
    public AnchorPane employeeAnchor;


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        employeeAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        employeeAnchor.getChildren().add(parent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        colLname.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        try {
            loadAllEmployee();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<Employee> obList = FXCollections.observableArrayList();

        while (resultSet.next()){
            obList.add(new Employee(
                    resultSet.getString("employee_id"),
                    resultSet.getString("f_name"),
                    resultSet.getString("l_name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getDouble("salary")
            ));
        }
        tblEmployee.setItems(obList);
    }
}
