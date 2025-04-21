/**
 * Controller for the Login Selection UI. This class is responsible for handling the login selection
 * for different types of users (Student, Teacher, and Manager). When a user selects a login type,
 * the corresponding login UI is displayed.
 *
 * The controller contains methods to load the respective login pages for students, teachers, and managers.
 * Each method uses FXMLLoader to load the respective FXML file, creates a new stage, and displays the login UI.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */
package comp3111.examsystem.controller;

import java.io.IOException;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SelectLoginController {

    /**
     * Opens the Student Login UI when the student login option is selected.
     * This method loads the "StudentLoginUI.fxml" file, creates a new stage,
     * and displays the student login screen.
     * @author Seokhyeon Hong
     */
    @FXML
    public void studentLogin() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentLoginUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Student Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Teacher Login UI when the teacher login option is selected.
     * This method loads the "TeacherLoginUI.fxml" file, creates a new stage,
     * and displays the teacher login screen.
     * @author Seokhyeon Hong
     */
    @FXML
    public void teacherLogin() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherLoginUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Teacher Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Manager Login UI when the manager login option is selected.
     * This method loads the "ManagerLoginUI.fxml" file, creates a new stage,
     * and displays the manager login screen.
     * @author Seokhyeon Hong
     */
    public void managerLogin() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManagerLoginUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Manager Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
