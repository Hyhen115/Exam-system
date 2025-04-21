package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMainController implements Initializable {
    @FXML
    private VBox mainbox;

    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * open student manage UI
     * ManagerStudentManagementUI.fxml
     * @author Wong Hon Yin
     */
    @FXML
    public void openStudentManageUI() {
        openNewStage("ManagerStudentManagementUI.fxml", "Student Management");
    }

    /**
     * open teacher manage UI
     * ManagerTeacherManageUI.fxml
     * @author Wong Hon Yin
     */
    @FXML
    public void openTeacherManageUI() {
        openNewStage("ManagerTeacherManagementUI.fxml", "Teacher Management");
    }

    /**
     * open course manage UI
     * ManagerCourseManagementUI.fxml
     * @author Wong Hon Yin
     */
    @FXML
    public void openCourseManageUI() {
        openNewStage("ManagerCourseManagementUI.fxml", "Course Management");
    }

    /**
     * open course register manage UI
     * ManagerCourseRegManagementUI.fxml
     * @author Wong Hon Yin
     */
    @FXML
    public void openCourseRegisterManagement() {
        openNewStage("ManagerCourseRegManagementUI.fxml", "Course Registration");
    }

    /**
     * exit program
     * @author Wong Hon Yin
     */
    @FXML
    public void exit() {
        System.exit(0);
    }

    /**
     * function to load new stage
     * @author Wong Hon Yin
     * @param fxmlFile file name to load new stage
     * @param title    stage title to set
     */
    private void openNewStage(String fxmlFile, String title) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
