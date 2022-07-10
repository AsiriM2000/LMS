package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import module.Batch;
import module.Student;
import util.CrudUtil;
import util.ValidationUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddStudentFormController implements Initializable {
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtContact;
    public JFXComboBox cmbGender;
    public TableView<Student> tblStudentDetails;
    public TableColumn colFname;
    public TableColumn colLname;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colGender;
    public AnchorPane addStudentAnchor;
    public JFXButton btnAddStudent;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(observableList);

        colFname.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        colLname.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            loadAllStudent();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnAddStudent.setOnMouseClicked(event -> {
            try {
                saveStudent();
                loadAllStudent();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Pattern f_namePattern = Pattern.compile("^[A-z]{3,}$");
        Pattern l_namePattern = Pattern.compile("^[A-z]{3,}$");
        Pattern emailPattern = Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+$");
        Pattern contactPattern = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");

        map.put(txtFname,f_namePattern);
        map.put(txtLname,l_namePattern);
        map.put(txtEmail,emailPattern);
        map.put(txtContact,contactPattern);



    }

    private void saveStudent() throws SQLException, ClassNotFoundException {
        Student s = new Student(
                txtFname.getText(),txtLname.getText(),txtEmail.getText(), (String) cmbGender.getValue(), txtContact.getText()
        );

        if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?)",s.getF_name(),s.getL_name(),s.getEmail(),s.getGender(),s.getContact())){

            new Alert(Alert.AlertType.CONFIRMATION,"Add Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty").show();
        }
        clearText();
    }

    private void loadAllStudent() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Student");
        ObservableList<Student> observableList = FXCollections.observableArrayList();

        while (resultSet.next()){
            observableList.add(new Student(
                    resultSet.getString("f_name"),
                    resultSet.getString("l_name"),
                    resultSet.getString("email"),
                    resultSet.getString("gender"),
                    resultSet.getString("contact")
            ));
        }
        tblStudentDetails.setItems(observableList);
    }

    private void clearText(){
        txtFname.clear();
        txtLname.clear();
        txtEmail.clear();
        txtContact.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnAddStudent);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnAddStudent);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student student = tblStudentDetails.getSelectionModel().getSelectedItem();
        if (CrudUtil.execute("DELETE FROM Student WHERE f_name =?",student.getF_name())){
            tblStudentDetails.refresh();
            loadAllStudent();
        }
    }
}
