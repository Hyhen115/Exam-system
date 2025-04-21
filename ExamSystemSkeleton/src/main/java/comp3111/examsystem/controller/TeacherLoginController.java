/**
 * This Teacher login controller implements login system for Exam System
 * This allows teacher to either login or select to register their account
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */

package comp3111.examsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Teacher;
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

/**
 * Controller class for managing login function of the exam system
 * Provides functionality for logging in and registering.
 * @author Seokhyeon Hong
 */

public class TeacherLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    private Database<Teacher> database;

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resources The resources used to localize the root object, or null if no resources are needed.
     * @author Seokhyeon Hong
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    /**
     * Handles the login action when the user clicks the login button. It validates the entered
     * username and password by checking them against the database. If the credentials are valid,
     * the teacher is redirected to the main UI. Otherwise, an error message is displayed.
     *
     * @param e The event triggered by the login button click.
     * @author Seokhyeon Hong
     */
    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        if (DatabaseService.getTeacherDatabase().checkUser(username, password)) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() +", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Failed");
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }
    }
    /**
     * Navigates to the teacher registration page where new teachers can register.
     *
     * @throws IOException If the FXML file for the registration page cannot be loaded.
     * @author Seokhyeon Hong
     */
    @FXML
    public void register() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherRegisterPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register New Teacher");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
