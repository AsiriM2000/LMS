package controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstAdminLoginFormController implements Initializable {
    public Text text;
    public Pane loginForm;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public AnchorPane loginContext;

    private String Username;
    private String Password;

    TextAnimator textAnimator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextOutput textOutput = new  TextOutput() {
            @Override
            public void writeText(String textOut) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(textOut);
                    }
                });
            }
        };

        textAnimator = new TextAnimator("WELCOME TO LPEC LEARNING MANAGEMENT SYSTEM",
                50, textOutput);

        Thread thread = new Thread(textAnimator);
        thread.start();

    }
    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        Username = txtUsername.getText();
        Password = txtPassword.getText();

        if (Username.equals("Lpec") && Password.equals("Lpec1234")){
            Image image = new Image("/assets/style/T Image/check.png");

            Notifications notifications = Notifications.create()
                    .title("Login Success!")
                    .text("You have successful login to the system")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);

            notifications.darkStyle();
            notifications.show();

            Stage stage = (Stage) loginContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBordForm.fxml"))));
            stage.centerOnScreen();

        }else {
            Image image = new Image("/assets/style/T Image/close.png");
            Notifications notifications = Notifications.create()
                    .title("Login Failed!")
                    .text("You have Failed login to the system")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);

            notifications.darkStyle();
            notifications.show();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            txtPassword.requestFocus();
        }
    }
}
