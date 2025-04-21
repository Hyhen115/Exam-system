/**
 * Controller for the Teacher Main UI. This class handles the navigation between various sections
 * of the teacher interface, including question management, exam management, and grade statistics.
 * The controller is responsible for opening different UI views when a user interacts with the
 * corresponding buttons for each section. It uses FXMLLoader to load the respective FXML files
 * and open new stages for each functionality.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */

package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMainController implements Initializable {
    @FXML
    private VBox mainbox;


    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resources The resources used to localize the root object, or null if no resources are needed.
     * @author Seokhyeon Hong
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Opens the Question Bank Management UI when triggered by the user. It loads the corresponding
     * FXML file and creates a new stage for the question management interface.
     *
     * @throws Exception If there is an issue loading the FXML file or showing the stage.
     * @author Seokhyeon Hong
     */
    @FXML
    public void openQuestionManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("QuestionBankManagement.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Question Bank Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Exam Management UI when triggered by the user. It loads the corresponding
     * FXML file and creates a new stage for the exam management interface.
     *
     * @throws Exception If there is an issue loading the FXML file or showing the stage.
     * @author Seokhyeon Hong
     */
    @FXML
    public void openExamManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExamManagement.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Grade Statistics UI when triggered by the user. It loads the corresponding
     * FXML file and creates a new stage for the grade statistics interface.
     *
     * @throws Exception If there is an issue loading the FXML file or showing the stage.
     * @author Seokhyeon Hong
     */
    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherGradeStatistic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the application when the user clicks the exit button. This method terminates the
     * JavaFX application by invoking `System.exit(0)`.
     * @author Seokhyeon Hong
     */
    @FXML
    public void exit() {
        System.exit(0);
    }
}
