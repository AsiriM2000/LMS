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
import module.Payment;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentViewFormController implements Initializable {
    public TableView<Payment> tblPayment;
    public TableColumn colRegId;
    public TableColumn colCourseId;
    public TableColumn colMonth;
    public TableColumn colPayment;
    public AnchorPane paymentContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRegId.setCellValueFactory(new PropertyValueFactory<>("register_id"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("month_fee"));

            loadPaymentDetails();
    }

    private void loadPaymentDetails() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Payment");
            ObservableList<Payment> obList  = FXCollections.observableArrayList();
            while (result.next()){
                obList.add(new Payment(
                        result.getString("register_id"),
                        result.getString("course_id"),
                        result.getString("month"),
                        result.getDouble("month_fee")
                ));
            }
            tblPayment.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) paymentContext.getScene().getWindow();
        stage.close();
    }
}
