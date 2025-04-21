package comp3111.examsystem.controller;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.Main;
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

public class StudentLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    /**
     * The function for the setup of initializing the stage
     * @param location The location used to resolve relative paths for the root object, or
     *            {@code null} if the location is not known.
     * @param resources  The resources used to localize the root object, or {@code null} if
     *            the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * The login function for verifying the student
     * @param e when someone pressed the button
     */
    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        //If one of fields is empty ask them to try again
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Hint","Username or password is empty.");
            return;
        }

        //Check if student detail exists in database
        List<Student> students = DatabaseService.getStudentDatabase().getAll();
        Student LoggedInStudent = null;
        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                LoggedInStudent = student;
                break;
            }
        }

        //If there is a logged in student
        if (LoggedInStudent != null) {
            //Save this student to SessionManager
            SessionManager.setLoggedInStudentId(LoggedInStudent.getId());
            //Alert user that they have been logged in
            showAlert("Hint", "Login Successful");

            //Open the Main UI
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() +", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();

            //Close the login page
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        }

        //If there is no such student in the database
        else {
            showAlert("Hint","Username or password is incorrect.");
        }

    }

    /**
     * The register function for opening the register page
     */
    @FXML
    public void register() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentRegisterUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Student Register");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The showAlert function to show the user an alert
     * @param title for the title of the alert
     * @param text for the main content of the alert
     */
    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    /**
     * The SessionManager class to store the details of the logged in student. Used to retrieve
     * items specific to the logged in student later on in the program.
     */
    public class SessionManager {
        private static Long loggedInStudentId;

        public static void setLoggedInStudentId(Long studentId) {
            loggedInStudentId = studentId;
        }

        public static Long getLoggedInStudentId() {
            return loggedInStudentId;
        }
    }

}
