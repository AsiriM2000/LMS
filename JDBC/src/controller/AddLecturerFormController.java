package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import module.Lecturer;
import util.CrudUtil;
import util.ValidationUtil;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddLecturerFormController implements Initializable {
    public TextField txtLecId;
    public TextField txtFname;
    public TextField txtLname;
    public JFXComboBox cmbGender;
    public TextField txtCity;
    public TextField txtExperience;
    public JFXButton btnAddLecturer;
    public TableView<Lecturer> tblLecturerDetails;
    public TableColumn colLecId;
    public TableColumn colLecFName;
    public TableColumn colLecLName;
    public TableColumn colLecGender;
    public TableColumn colCity;
    public TableColumn colExperience;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> obList = FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(obList);

        try {
            loadAllLecturer();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnAddLecturer.setOnMouseClicked(event -> {
            try {
                saveLecturer();
                loadAllLecturer();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        colLecId.setCellValueFactory(new PropertyValueFactory<>("lec_id"));
        colLecFName.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        colLecLName.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        colLecGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colExperience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));

        Pattern lec_id = Pattern.compile("^(L00-)[0-9]{3,5}$");
        Pattern f_name = Pattern.compile("^[A-z]{3,}$");
        Pattern l_name = Pattern.compile("^[A-z]{3,}$");
        Pattern city = Pattern.compile("^[A-z]{3,}$");
        Pattern experience = Pattern.compile("^[A-z]{3,}$");

        map.put(txtLecId,lec_id);
        map.put(txtFname,f_name);
        map.put(txtLname,l_name);
        map.put(txtCity,city);
        map.put(txtExperience,experience);

    }

    private void saveLecturer() throws SQLException, ClassNotFoundException {
        Lecturer le = new Lecturer(
                txtLecId.getText(),
                txtFname.getText(),
                txtLname.getText(),
                (String) cmbGender.getValue(),
                txtExperience.getText(),
                txtCity.getText()
        );

        if (CrudUtil.execute("INSERT INTO Lecturer VALUES (?,?,?,?,?,?)",le.getLec_id(),le.getF_name(),le.getL_name(),le.getGender(),le.getExperience(),le.getCity())){
            new Alert(Alert.AlertType.CONFIRMATION,"Added Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Added Failed!").show();
        }

        clearData();

    }

    private void loadAllLecturer() throws SQLException, ClassNotFoundException {
        ObservableList<Lecturer> lecturers = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Lecturer");
        while (resultSet.next()){
            lecturers.add(new Lecturer(
                    resultSet.getString("lec_id"),
                    resultSet.getString("f_name"),
                    resultSet.getString("l_name"),
                    resultSet.getString("gender"),
                    resultSet.getString("experience"),
                    resultSet.getString("city")
            ));
        }
        tblLecturerDetails.setItems(lecturers);
    }
    private void clearData() {
        txtLecId.clear();
        txtFname.clear();
        txtLname.clear();
        txtExperience.clear();
        txtCity.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnAddLecturer);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnAddLecturer);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }

        }
    }

    public void DeleteLecturer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Lecturer lecturer = tblLecturerDetails.getSelectionModel().getSelectedItem();
        if (CrudUtil.execute("DELETE FROM Lecturer WHERE lec_id =?",lecturer.getLec_id())){
            tblLecturerDetails.refresh();
            loadAllLecturer();
        }
    }
}
