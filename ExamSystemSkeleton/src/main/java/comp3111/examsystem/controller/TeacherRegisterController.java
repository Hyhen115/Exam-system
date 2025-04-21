/**
 * Controller for the Teacher Registration UI. This class handles the process of registering
 * a new teacher in the system, including validating input fields and storing the teacher's
 * details in the database.
 * The controller includes fields for various teacher attributes such as username, name, age,
 * gender, position, department, and password. It also performs validation to ensure that all
 * fields are filled in correctly and that the username is unique.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */

package comp3111.examsystem.controller;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherRegisterService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
public class TeacherRegisterController {

    @FXML
    private ChoiceBox<String> genderComboX;
    @FXML
    private ChoiceBox<String> positionComboX;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField ageTxt;
    @FXML
    private TextField departmentTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField passwordConfirmTxt;

    private final TeacherRegisterService teacherRegisterService = new TeacherRegisterService();

    /**
     * Initializes the controller. This method sets up the available choices for gender and
     * position in the respective ComboBoxes.
     * @author Seokhyeon Hong
     */
    @FXML
    public void initialize() {
        // Set values for genderComboX
        genderComboX.getItems().addAll("Male", "Female", "Non-binary");

        // Set values for positionComboX
        positionComboX.getItems().addAll("Lecturer", "Assistant Professor", "Associate Professor", "Dean", "Teaching Assistant");
    }

    /**
     * Submits the teacher registration form. This method performs validation to ensure that all
     * required fields are filled in correctly, and checks that the password fields match.
     * If validation passes, it creates a new Teacher object and adds it to the database.
     * Validation includes:
     * 1. Matching password and password confirm
     * 2. Username must be longer than 5 characters
     * 3. Age must be an integer
     * 4. Age must be between 0 & 100
     * 5. password must be at least 8 characters
     * 6. All usernames are unique
     * @throws NumberFormatException If the entered age is not a valid integer.
     * @author Seokhyeon Hong
     */
    @FXML
    private void submit() {
        String username = usernameTxt.getText().trim();
        String name = nameTxt.getText().trim();
        String gender = genderComboX.getValue();
        String ageText = ageTxt.getText().trim();
        String position = positionComboX.getValue();
        String department = departmentTxt.getText().trim();
        String password = passwordTxt.getText();
        String passwordConfirm = passwordConfirmTxt.getText();

        if (username.isEmpty() || name.isEmpty() || gender == null || ageText.isEmpty() ||
                position == null || department.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            showAlert("All fields must be filled in.");
            return;
        }
        if (username.length() < 5) {
            showAlert("Username must be at least 5 characters.");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            showAlert("Passwords do not match.");
            return;
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

        if(!teacherRegisterService.checkUsername(username)){
            showAlert("Username is already Taken");
            return;
        }


        // set up new teacher
        Teacher teacher = new Teacher();
        teacher.setAgeUsingString(ageText);
        teacher.setGender(gender);
        teacher.setPosition(position);
        teacher.setName(name);
        teacher.setPassword(password);
        teacher.setDepartment(department);
        teacher.setUsername(username);

        teacherRegisterService.addTeacher(teacher);
        showAlert("Registration Successful");

        close();
    }

    /**
     * Closes the current registration window.
     * This method is called after a successful registration to close the stage.
     * @author Seokhyeon Hong
     */
    @FXML
    private void close() {
        // Close the current window
        Stage stage = (Stage) usernameTxt.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an informational alert with the given message.
     * This method is used to show error or success messages to the user.
     *
     * @param message The message to be displayed in the alert.
     * @author Seokhyeon Hong
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
