package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import module.Batch;
import module.Lecturer;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BatchViewFormController implements Initializable {
    public TableView<Batch> tblBatch;
    public TableColumn colBatch;
    public AnchorPane context;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBatch.setCellValueFactory(new PropertyValueFactory<>("batch_name"));
        try {
            loadAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Batch");
        ObservableList<Batch> batches = FXCollections.observableArrayList();

        while (resultSet.next()){
            batches.add(new Batch(
                    resultSet.getString("batch_name")
            ));
        }

        tblBatch.setItems(batches);
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Batch batch = tblBatch.getSelectionModel().getSelectedItem();
        if (CrudUtil.execute("DELETE FROM Batch WHERE batch_name =?",batch.getBatch_name())){
            tblBatch.refresh();
            loadAll();
        }
    }
}
