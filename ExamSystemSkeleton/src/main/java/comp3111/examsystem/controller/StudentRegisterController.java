package comp3111.examsystem.controller;
import java.io.IOException;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import java.util.List;

import comp3111.examsystem.service.StudentRegisterService;

public class StudentRegisterController {

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField ageTxt;
    @FXML
    private TextField departmentTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField passwordConfirmTxt;

    /**
     * Initialize the register page. This involves filling the combobox with the gender choices.
     */
    @FXML
    public void initialize() {
        // Populate the gender ComboBox with options
        genderComboBox.getItems().addAll("Male", "Female", "Non-binary");
    }

    /**
     * Function submit() for the submission of the registration. This involves checking each field if they are empty,
     * and if they meet certain criteria. If all conditions are fulfilled, the student is saved into the database.
     */
    @FXML
    private void submit() {
        String username = usernameTxt.getText().trim();
        String name = nameTxt.getText().trim();
        String gender = genderComboBox.getValue();
        String ageText = ageTxt.getText().trim();
        String department = departmentTxt.getText().trim();
        String password = passwordTxt.getText();
        String passwordConfirm = passwordConfirmTxt.getText();

        if (username.isEmpty() || name.isEmpty() || gender.isEmpty() || ageText.isEmpty() ||
                department.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            showAlert("All fields must be filled in.");
            return;
        }

        if (username.length() < 5) {
            showAlert("Username must be at least 5 characters.");
            return;
        }

        List<Student> existingStudent = DatabaseService.getStudentDatabase().getAll();
        // check for existing students
        for (Student student : existingStudent) {
            // if you have existed username -> return
            if (student.getUsername().equals(username)) {
                showAlert("Username is already taken.");
            }
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showAlert("Age must be a valid integer.");
            return;
        }

        if (age < 0 || age > 100) {
            showAlert("Age must be between 0 and 100.");
            return;
        }

        if (password.length() < 8) {
            showAlert("Password must be at least 8 characters.");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            showAlert("Passwords do not match.");
            return;
        }

        // fetch data


        // set up new student

        StudentRegisterService.addStudent(username, name, gender, ageText, department, password);
        showAlert("Registration Successful");

        Stage currentStage = (Stage) usernameTxt.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Function close() to close the interface.
     */
    @FXML
    private void close() {
        // Close the current window
        Stage stage = (Stage) usernameTxt.getScene().getWindow();
        stage.close();
    }

    /**
     * Function showAlert(message) to show the user an alert.
     * @param message is the message to be displayed in the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}

