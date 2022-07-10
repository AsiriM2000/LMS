package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import module.Lecture;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ScheduleLectureFormController implements Initializable {
    public JFXComboBox cmbBatchName;
    public JFXComboBox cmbLecturerId;
    public DatePicker cmbDate;
    public AnchorPane scheduleAnchor;
    public JFXTimePicker time;
    public JFXComboBox cmbSubjectId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<String> batchName = FXCollections.observableArrayList(BatchCrudController.getBatchName());
            ObservableList<String> subjectId = FXCollections.observableArrayList(SubjectCrudController.getSubjectId());
            ObservableList<String> lecturerId = FXCollections.observableArrayList(LecturerCrudController.getLecturerId());

            cmbBatchName.setItems(batchName);
            cmbSubjectId.setItems(subjectId);
            cmbLecturerId.setItems(lecturerId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Lecture l = new Lecture(
                (String) cmbBatchName.getValue(),
                (String) cmbSubjectId.getValue(),
                (String) cmbLecturerId.getValue(),
                String.valueOf(cmbDate.getValue()),
                String.valueOf(time.getValue())
        );

        if (CrudUtil.execute("INSERT INTO Lecture VALUES (?,?,?,?,?)",l.getBatch_name(),l.getSubject_id(),l.getLec_id(),l.getDate(),l.getTime())){
            new Alert(Alert.AlertType.CONFIRMATION,"Save Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Save Failed!").show();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) scheduleAnchor.getScene().getWindow();
        stage.close();
    }

    public void viewOnAction(ActionEvent actionEvent) throws IOException {
        scheduleAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/LectureViewForm.fxml"));
        scheduleAnchor.getChildren().add(parent);
    }
}
