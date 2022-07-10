package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import module.Batch;
import module.Course;
import module.Subject;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagementFormController implements Initializable {
    public TextField txtBatchName;
    public AnchorPane manageAnchor;
    public TextField txtCourseName;
    public TextField txtCourseId;
    public TextField txtSubName;
    public TextField txtSubId;
    public JFXComboBox cmbCourseId;
    public ImageView refresh;
    public Button btnRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList(
                    CourseCrudController.getCourseIds()
            );
            cmbCourseId.setItems(obList);

         btnRefresh.setOnAction(event -> {
             obList.clear();
             try {
                 setData();
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }
         });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void setData() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList(
                CourseCrudController.getCourseIds()
        );
        cmbCourseId.setItems(obList);
    }
    public void addBatch(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Batch b = new Batch(
                txtBatchName.getText()
        );

        if (CrudUtil.execute("INSERT INTO Batch VALUES (?)",b.getBatch_name())){
            new Alert(Alert.AlertType.CONFIRMATION,"Add Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Add Failed!").show();
        }
        clearData();
    }

    public void BatchViewOnAction(ActionEvent actionEvent) throws IOException {
        setPopUp("BatchViewForm");
    }

    public void addCourse(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Course course = new Course(
                txtCourseId.getText(),
                txtCourseName.getText()
        );
        if (CrudUtil.execute("INSERT INTO Course VALUES (?,?)",course.getCourse_id(),course.getCourse_name())){
            new Alert(Alert.AlertType.CONFIRMATION,"Add Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Add Failed!").show();
        }
        clearData();
    }

    public void CourseViewOnAction(ActionEvent actionEvent) throws IOException {
        setPopUp("CourseViewForm");
    }

    public void addSubject(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Subject sub = new Subject(
                (String) cmbCourseId.getValue(),
                txtSubId.getText(),
                txtSubName.getText()
        );

        if (CrudUtil.execute("INSERT INTO Subject VALUES (?,?,?)",sub.getCourse_id(),sub.getSubject_id(),sub.getSubject_name())){
            new Alert(Alert.AlertType.CONFIRMATION,"Add Success!").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Add Failed!").show();
        }
        clearData();
    }

    public void SubjectViewOnAction(ActionEvent actionEvent) throws IOException {
        setPopUp("SubjectViewForm");
    }
    public void setPopUp(String location) throws IOException {
        DBConnection.anchorPane = manageAnchor;
        URL resorce = getClass().getResource("../View/"+location+".fxml");
        Parent root = FXMLLoader.load(resorce);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    private void clearData() {
        txtBatchName.clear();
        txtCourseId.clear();
        txtCourseName.clear();
        txtSubId.clear();
        txtSubName.clear();
    }
}
