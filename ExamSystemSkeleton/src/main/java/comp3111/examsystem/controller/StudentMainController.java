package comp3111.examsystem.controller;
import comp3111.examsystem.Database;
import comp3111.examsystem.entity.*;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.Main;

import comp3111.examsystem.entity.Record;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentMainController implements Initializable {
    @FXML
    ComboBox<String> examCombox;

    /**
     * Initializes the controller class with the provided location and resources.
     *
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources the resources used to localize the root object, or null if the root object was not localized.
     * This method performs the following tasks:
     * - Checks if the exam combo box is null and returns if it is.
     * - Retrieves the ID of the currently logged-in student.
     * - Finds all courses that the student is enrolled in from the course registration records.
     * - Retrieves the grade database.
     * - Finds all exams in the selected courses.
     * - For each exam, checks if the student needs to take the exam by verifying:
     *   - If the exam is associated with any of the student's enrolled courses.
     *   - If there is no existing record of the student's score for the exam.
     *   - If the exam has been published.
     * - Adds the exam to the exam combo box if all conditions are met.
     */
    public void initialize(URL location, ResourceBundle resources) {

        //examCombox null check
        if (examCombox == null) {
            return;
        }

        //Get id of student
        Long currentStudentId = StudentLoginController.SessionManager.getLoggedInStudentId();

        //Find all courses that share the student id in CourseRegRecord
        Database<CourseRegRecord> courseRegRecordDatabase = DatabaseService.getCourseRegRecordDatabase();
        List<CourseRegRecord> enrolledCourses = courseRegRecordDatabase.queryAllOfKey(Long.toString(currentStudentId), "studentKey");
        Database<Grade> gradeDatabase = DatabaseService.getGradeDatabase();

        //Find all exams in the selected courses
        List<Exam> examDatabase = DatabaseService.getExamDatabase().getAll();
        for (Exam Exams : examDatabase) {
            for (CourseRegRecord CourseRegRecord : enrolledCourses) {
                //Check if student needs to take exam
                if (Exams.getCourseKey().equals(CourseRegRecord.getCourseKey())) {
                    //Check if there is any record of score on the exam. If not, display it.
                    if (gradeDatabase.queryByTwoFields(Exams.getId().toString(), "examId", currentStudentId.toString(), "studentId") == null) {
                        //Check if the exam has been published
                        if (Exams.getPublish().equals("yes")) {
                            examCombox.getItems().addAll(CourseRegRecord.getCourseName() + " - " + Exams.getExamName());
                            break;
                        }
                    }

                }
            }
        }
    }

    /**
     * Class ExamAndQuestionData to store the details of the selected exam.
     */
    public class ExamAndQuestionData {
        private static Exam exam;
        private static List<Question> questions;
        private static String SelectedExamName;
        public static Exam getExam() {
            return exam;
        }
        public static void setExam(Exam exam) {
            ExamAndQuestionData.exam = exam;
        }

        public static List<Question> getQuestions() {
            return questions;
        }

        public static void setQuestions(List<Question> questions) {
            ExamAndQuestionData.questions = questions;
        }

        public static String getSelectedExamName() {
            return SelectedExamName;
        }

        public static void setSelectedExamName(String selectedExamName) {
            ExamAndQuestionData.SelectedExamName = selectedExamName;
        }
    }

    /**
     * Function openExamUI() to open the exam interface to allow student to take the exam they have selected
     * in the combobox. It uses the details inside the combobox to find the specific exam and its questions to
     * display it in the exam UI.
     */
    @FXML
    public void openExamUI() {
        //Get the string 'courseName - examName' from the combox
        String selectedExamName = examCombox.getSelectionModel().getSelectedItem();
        ExamAndQuestionData.setSelectedExamName(selectedExamName);
        if (selectedExamName == null) return;

        //Remove item from combox
        examCombox.getItems().remove(selectedExamName);

        //Split the string into courseName and examName
        String divider = " - ";
        int index = selectedExamName.indexOf(divider);
        String courseName = null;
        String examName = null;
        if (index != -1) {
            courseName = selectedExamName.substring(0, index);
            examName = selectedExamName.substring(index + divider.length());
        }

        //Find the questions in the exam

        //Get the courseID based on the courseName
        Database<Course> courseDatabase = DatabaseService.getCourseDatabase();
        Course course = courseDatabase.queryByName(courseName, "courseId");
        String courseKey = course.getCourseKey().toString();

        //Get the names of exams to be displayed
        Database<Exam> examsDatabase = DatabaseService.getExamDatabase();
        Exam examToShow = examsDatabase.queryByTwoFields(examName, "examName", courseKey, "courseKey");

        if (examToShow == null) {
            examToShow = examsDatabase.queryByName(examName, "examName");
        }

        //Get the records of questions to be displayed
        Database<Record> recordDatabase = DatabaseService.getRecordDatabase();
        List<Record> recordOfQuestionsToShow = recordDatabase.queryAllOfKey(Long.toString(examToShow.getId()), "examKey");



        //Get the questions
        List<Question> questionsToShow = new ArrayList<>();
        for (Record record : recordOfQuestionsToShow) {
            questionsToShow.add(record.getQuestion());
        }

        if (questionsToShow.isEmpty()) {
            showAlert("Notice", "There are no questions in this exam, hence it is unable to take it at this time.");
            return;
        }

        System.out.println("Questions: " + questionsToShow);

        ExamAndQuestionData.setExam(examToShow);
        ExamAndQuestionData.setQuestions(questionsToShow);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentExamUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Start Exam");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function openGradeStatistics() to open the student grade statistics interface
     */
    @FXML
    public void openGradeStatistic() {
        Database<Grade> gradeDatabase = DatabaseService.getGradeDatabase();
        Long currentStudentId = StudentLoginController.SessionManager.getLoggedInStudentId();
        List<Grade> grades = gradeDatabase.queryAllOfKey(currentStudentId.toString(), "studentId");
        if (grades.size() == 0) {
            showAlert("Warning", "There are no statistics to show.");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentGradeStatisticsUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function exit() to exit interface.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }

    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
