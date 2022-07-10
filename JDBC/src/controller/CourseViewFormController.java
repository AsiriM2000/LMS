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
import module.Course;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CourseViewFormController implements Initializable {
    public TableView<Course> tblCourses;
    public TableColumn colCourseId;
    public TableColumn colCourseName;
    public AnchorPane context;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colCourseId.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        try {
            loadAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAll() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM Course");
        ObservableList<Course> obList = FXCollections.observableArrayList();

        while (set.next()){
            obList.add(new Course(
                    set.getString("course_id"),
                    set.getString("course_name")
            ));
        }
        tblCourses.setItems(obList);
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Course course = tblCourses.getSelectionModel().getSelectedItem();
        if (CrudUtil.execute("DELETE FROM Course WHERE course_id =?",course.getCourse_id())){
            tblCourses.refresh();
            loadAll();
        }
    }
}
