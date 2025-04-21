package comp3111.examsystem.controller;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import comp3111.examsystem.entity.Grade;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StudentGradeStatisticsController implements Initializable {

    @FXML
    private ComboBox<String> statisticsCombox;

    @FXML
    private TableView<gradeStatistics> studentGradeTable;

    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis categoryAxisBar;
    @FXML
    NumberAxis numberAxisBar;

    @FXML
    private TableColumn<Grade, String> courseColumn;
    @FXML
    private TableColumn<Grade, String> examColumn;
    @FXML
    private TableColumn<Grade, String> scoreColumn;
    @FXML
    private TableColumn<Grade, String> fullScoreColumn;
    @FXML
    private TableColumn<Grade, String> timeSpendColumn;

    private URL location;
    private ResourceBundle resources;

    private String studentId = StudentLoginController.SessionManager.getLoggedInStudentId().toString();
    private Database<Grade> gradeDatabase = DatabaseService.getGradeDatabase();
    private List<Grade> gradesReceived = gradeDatabase.queryAllOfKey(studentId, "studentId");
    private Database<Exam> examDatabase = DatabaseService.getExamDatabase();

    /**
     * Initializes the controller class with the provided location and resources.
     *
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources the resources used to localize the root object, or null if the root object was not localized.
     * This method performs the following tasks:
     * - Displays the initial screen by setting up the table, making columns equal width, and setting the statistics combo box.
     * - Configures the bar chart by hiding the legend and setting labels for the category and number axes.
     * - Initializes the bar chart with default settings.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Display initial screen
        setUpTable();
        makeColumnsEqualWidth();
        setStatisticsCombox();

        //Set up bar chart
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Exam");
        numberAxisBar.setLabel("Percentage Score");

        setBarChart("none",true);

    }

    /**
     * Class gradeStatistics to store data to be displayed on the bar chart
     */
    public static class gradeStatistics {
        private String courseId;
        private String examName;
        private String score;
        private String fullScore;
        private String timeSpent;

        public gradeStatistics(String courseId, String examName, String score, String fullScore, String timeSpent) {
            this.courseId = courseId;
            this.examName = examName;
            this.score = score;
            this.fullScore = fullScore;
            this.timeSpent = timeSpent;
        }
        public String getCourseId() { return courseId; }
        public String getExamName() { return examName; }
        public String getScore() { return score; }
        public String getFullScore() { return fullScore; }
        public String getTimeSpent() { return timeSpent; }
        public void setCourseId(String courseId) { this.courseId = courseId; }
        public void setExamName(String examId) { this.examName = examName; }
        public void setScore(String score) { this.score = score; }
        public void setFullScore(String fullScore) { this.fullScore = fullScore; }
        public void setTimeSpent(String timeSpent) { this.timeSpent = timeSpent; }
    }

    /**
     * Function setUpTable() sets up the table view of the list of exams taken by student
     */
    public void setUpTable() {

        //clear table
        studentGradeTable.getItems().clear();

        //Initialize the columns for the tableview
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));

        //Fill in the values into the table
        ObservableList<gradeStatistics> gradeStatisticsList = FXCollections.observableArrayList();

        //For each grade
        for (Grade grade : gradesReceived) {
            //Find the examId
            String examId = grade.getExamId();
            //Use it to find the courseId
            Exam exam = examDatabase.queryByKey(examId);
            String courseId = exam.getCourseNum();

            //Get the exam name
            String examName = exam.getExamName();

            String score = grade.getScore();
            String fullScore = grade.getFullScore();
            String timeSpend = grade.getTimeSpend();

            // Add data to the table
            gradeStatisticsList.add(new gradeStatistics(courseId, examName, score, fullScore, timeSpend));
        }

        studentGradeTable.setItems(gradeStatisticsList);
    }

    /**
     * Function makeColumnsEqualWidth() is to make the columns in tableview to be equalwidth
     */
    private void makeColumnsEqualWidth() {
        studentGradeTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double tableWidth = newWidth.doubleValue();
            double columnWidth = tableWidth / studentGradeTable.getColumns().size();
            for (TableColumn<?, ?> column : studentGradeTable.getColumns()) {
                column.setPrefWidth(columnWidth);
            }
        });
    }

    /**
     * Function setStatisticsCombox sets up the combobox for the filter
     */
    public void setStatisticsCombox() {
        if (statisticsCombox == null) {
            return;
        }
        //Reset combobox
        statisticsCombox.getSelectionModel().clearSelection();
        //Set up the course list
        int gradesSize = gradesReceived.size();
        for (int i = 0; i < gradesSize; i++) {
            //Find the examId
            String examId = gradesReceived.get(i).getExamId();
            //Use it to find the courseId
            Exam exam = examDatabase.queryByKey(examId);
            if (!statisticsCombox.getItems().contains(exam.getCourseNum())) {
                statisticsCombox.getItems().addAll(exam.getCourseNum());
            }
        }
    }

    /**
     * Function setBarChart(courseName, firstTime) sets up the bar chart based on the item selected in combobox
     * @param courseName is the name of course selected in combobox
     * @param firstTime is a boolean to check if this is the first time this function is called
     */
    public void setBarChart(String courseName, boolean firstTime) {

        // Clear existing data from the charts
        barChart.getData().clear();

        if (courseName == "none") {
            return;
        }

        // Bar Chart -> scores for each exam

        //Get input from comboBox
        String statsToShow = courseName;

        //For each grade in the database, check if it is from the selected course
        if (statsToShow == null && firstTime == false) {
            showAlert("Notice", "Please select an exam to display.");
            return;
        }
        else if (firstTime == true) {
            return;
        }

        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();

        for (Grade grade : gradesReceived) {
            String course = grade.getCourseNum();
            if (statsToShow.equals(course)) {
                //Then, get its exam names, scores, total scores
                String examName = grade.getExamName();
                double score = Double.parseDouble(grade.getScore());
                double totalScore = Double.parseDouble(grade.getFullScore());
                String scoreString = grade.getScore();
                Double percentageScore = (100 * score) / totalScore;

                seriesBar.getData().add(new XYChart.Data<>(examName, percentageScore));

            }
        }

        barChart.getData().add(seriesBar);

        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelRotation(0); // Ensure labels are not rotated
        xAxis.setTickLabelGap(10); // Adjust gap if needed
        xAxis.setStyle("-fx-alignment: center;"); // Center align labels

        // Force layout pass to center the labels
        Platform.runLater(() -> {
            barChart.applyCss();
            barChart.layout();
        });
    }

    /**
     * Function showAlert(title, text, onHidden) to show alert to user.
     * @param title is the title of the alert
     * @param text is the main message of the alert
     */
    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    /**
     * Function closeStatisticsUI() closes the interface
     */
    public void closeStatisticsUI() {
        Stage stage = (Stage) statisticsCombox.getScene().getWindow();
        stage.close();
    }

    /**
     * Function reset() resets the combobox and the bar chart accordingly.
     */
    public void reset() {
        setStatisticsCombox();
        setBarChart("none",true);
    }

    /**
     * Function filter() filters the exams that belong to the course selected in the combobox, then displays
     * the data on the bar chart.
     */
    public void filter() {
        String courseName = statisticsCombox.getSelectionModel().getSelectedItem();
        setBarChart(courseName,false);
    }

    /**
     * Function refresh() resets the entire page by calling the initializer again.
     */
    public void refresh(){
        closeStatisticsUI();
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
}
