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
import module.Lecture;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LectureViewFormController implements Initializable {

    public AnchorPane viewAnchor;
    public TableView<Lecture> tblLecture;
    public TableColumn colName;
    public TableColumn colSubId;
    public TableColumn colLecId;
    public TableColumn colDate;
    public TableColumn colTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("batch_name"));
        colSubId.setCellValueFactory(new PropertyValueFactory<>("subject_id"));
        colLecId.setCellValueFactory(new PropertyValueFactory<>("lec_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        try {
            loadAllData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Lecture");
        ObservableList<Lecture> lectures = FXCollections.observableArrayList();
        while (resultSet.next()){
            lectures.add(new Lecture(
                    resultSet.getString("batch_name"),
                    resultSet.getString("subject_id"),
                    resultSet.getString("lec_id"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            ));
        }
        tblLecture.setItems(lectures);
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        viewAnchor.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/scheduleLectureForm.fxml"));
        viewAnchor.getChildren().add(parent);
    }
}
