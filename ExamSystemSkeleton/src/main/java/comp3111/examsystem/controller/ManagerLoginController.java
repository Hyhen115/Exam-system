package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.MsgSender.showMsg;

public class ManagerLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    private String lastAlertMessage;

    public void initialize(URL location, ResourceBundle resources) {

    }
    
    /**
     * The login function for verifying the manager
     * @author Wong Hon Yin
     * @param e when someone pressed the button
     */
    @FXML
    public void login(ActionEvent e) {

        // get the username and password for the input text field
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if(username.isEmpty() || password.isEmpty()) {
            showMsg("Please enter all fields");
            return;
        }

        // check if the username and password are correct
        if (Manager.authenticate(username, password)) {
            // login successful
            // show success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Hint");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Login Successful");
            successAlert.showAndWait(); // wait for the user to close the alert

            // Load the manager main UI
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManagerMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() + ", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();

            // for testing
            lastAlertMessage = "Login Successful"; // Capture message
        }
        else {
            //login fail
            // show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password");
            alert.showAndWait(); // wait for the user to close the alert

            // for testing
            lastAlertMessage = "Login Failed"; // Capture message
        }
    }

    // setters for tests
    /**
     * Function for tests
     * @author Wong Hon Yin
     * @param usernameTxt to set the Username
     */
    public void setUsernameTxt(TextField usernameTxt) {
        this.usernameTxt = usernameTxt;
    }

    /**
     * Function for tests
     * @author Wong Hon Yin
     * @param passwordTxt to set password
     */
    public void setPasswordTxt(PasswordField passwordTxt) {
        this.passwordTxt = passwordTxt;
    }

    /**
     * Function for tests
     * @author Wong Hon Yin
     * @return lastAlertMsg
     */
    public String getLastAlertMessage() {
        return lastAlertMessage;
    }

}
