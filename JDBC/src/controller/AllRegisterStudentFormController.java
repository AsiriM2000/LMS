package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import module.Register;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllRegisterStudentFormController implements Initializable {
    public AnchorPane allStudentAnchor;
    public TableView<Register> tblAllStudentRegister;
    public TableColumn colId;
    public TableColumn colCourse;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colOl;
    public TableColumn colAl;
    public TableColumn colFee;
    public TableColumn colBatch;


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        allStudentAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"));
        allStudentAnchor.getChildren().add(parent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("register_id"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colBatch.setCellValueFactory(new PropertyValueFactory<>("batch_name"));
        colName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colOl.setCellValueFactory(new PropertyValueFactory<>("ol"));
        colAl.setCellValueFactory(new PropertyValueFactory<>("al"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            loadAllRegisterStudent();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllRegisterStudent() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Register");
        ObservableList<Register> obList = FXCollections.observableArrayList();

        while (resultSet.next()){
            obList.add(new Register(
                    resultSet.getString("register_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("batch_name"),
                    resultSet.getString("student_name"),
                    resultSet.getString("email"),
                    resultSet.getString("dob"),
                    resultSet.getString("address"),
                    resultSet.getString("city"),
                    resultSet.getString("ol"),
                    resultSet.getString("al"),
                    resultSet.getDouble("amount")
            ));
        }

        tblAllStudentRegister.setItems(obList);
    }
}
