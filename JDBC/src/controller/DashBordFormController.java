package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import module.Register;
import module.Student;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class DashBordFormController{
    public AnchorPane dashboardContext;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane dashboardAnchorContext;
    public JFXButton btnDashboard;
    public JFXButton btnStudent;
    public JFXButton btnRegister;
    public Label lblStudentCount;
    public JFXButton btnEmployee;
    public Label lblLecturerCount;
    public Label lblMemberCount;
    public JFXButton btnLecturers;
    public JFXButton btnManage;
    public JFXButton btnLectures;
    public JFXButton btnAttendant;
    public JFXButton btnPayment;
    public PieChart pieChart;
    public TableView tblStudent;
    public TableColumn colFName;
    public TableColumn colEmail;
    public TableColumn colContact;

    ObservableList<PieChart.Data>pieChartData =FXCollections.observableArrayList();
    public void initialize() throws SQLException, ClassNotFoundException {

        colFName.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));


        loadDateAndTime();
        loadWindow();
        chartView();
        studentList();
        pieChart.setData(pieChartData);
    }
    private void studentList(){

        try {
            ObservableList<Student>obList = FXCollections.observableArrayList();
            ResultSet result = CrudUtil.execute("SELECT * FROM Student");
            while (result.next()){
                     obList.add(new Student(
                             result.getString("f_name"),
                             result.getString("l_name"),
                             result.getString("email"),
                             result.getString("gender"),
                             result.getString("contact")
                     ));
            }
            tblStudent.setItems(obList);
        }catch (Exception e){}
    }
    private void chartView(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee WHERE salary BETWEEN  35000 AND 100000");
            while (resultSet.next()){
                pieChartData.add(new PieChart.Data(
                        resultSet.getString("f_name"),
                        resultSet.getDouble("salary")

                ));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void loadWindow() throws SQLException, ClassNotFoundException {
        btnDashboard.setOnMouseClicked(event -> {

            try {
                dashboardContext.getChildren().clear();
                Parent parent= FXMLLoader.load(getClass().getResource("../view/DashBordForm.fxml"));
                dashboardContext.getChildren().add(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        btnStudent.setOnMouseClicked(event -> {
            try {
                setUi("AddStudentForm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnRegister.setOnMouseClicked(event -> {
            try {
                setUi("RegisterForm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnEmployee.setOnMouseClicked(event -> {
            try {
                setUi("EmployeeForm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnLecturers.setOnMouseClicked(event -> {
            try {
                setUi("AddLecturerForm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnManage.setOnMouseClicked(event -> {
            try {
                setUi("ManagementForm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

       btnLectures.setOnMouseClicked(event -> {

           try {
               DBConnection.anchorPane = dashboardAnchorContext;
               URL resorce = getClass().getResource("../view/scheduleLectureForm.fxml");
               Parent root = FXMLLoader.load(resorce);
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.initStyle(StageStyle.TRANSPARENT);
               stage.setScene(scene);
               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
           }

       });

       btnAttendant.setOnMouseClicked(event -> {
           try {
               setUi("AttendantForm");
           } catch (IOException e) {
               e.printStackTrace();
           }
       });

       btnPayment.setOnMouseClicked(event -> {
           try {
               setUi("PaymentForm");
           } catch (IOException e) {
               e.printStackTrace();
           }
       });

        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(register_id) FROM Register");
        while (resultSet.next()){
            lblStudentCount.setText(resultSet.getString(1));
        }

        ResultSet result = CrudUtil.execute("SELECT COUNT(lec_id) FROM Lecturer");
        while (result.next()){
            lblLecturerCount.setText(result.getString(1));
        }

    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/FirstAdminLoginForm.fxml"))));
        stage.centerOnScreen();
    }

    public void loadDateAndTime(){
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+ ":" +currentTime.getMinute()+ ":" +currentTime.getSecond());

        }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setUi(String location) throws IOException {
        dashboardAnchorContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"));
        dashboardAnchorContext.getChildren().add(parent);

    }
}
