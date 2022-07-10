package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import module.Batch;
import module.Subject;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SubjectViewFormController implements Initializable {
    public TableView<Subject> tblSubject;
    public TableColumn colCourseIdSub;
    public TableColumn colSubId;
    public TableColumn colSubName;
    public AnchorPane context;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCourseIdSub.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colSubId.setCellValueFactory(new PropertyValueFactory<>("subject_id"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("subject_name"));

        try {
            loadAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Subject");
        ObservableList<Subject> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(new Subject(
                    result.getString("course_id"),
                    result.getString("subject_id"),
                    result.getString("subject_name")
            ));
        }
        tblSubject.setItems(obList);
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();

    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Subject subject = tblSubject.getSelectionModel().getSelectedItem();
        if (CrudUtil.execute("DELETE FROM Subject WHERE course_id =?",subject.getCourse_id())){
            tblSubject.refresh();
            loadAll();
        }
    }
}
