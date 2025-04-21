package comp3111.examsystem.controller;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

import comp3111.examsystem.entity.Grade;

public class TeacherGradeStatisticController implements Initializable {
    /*
    public static class GradeExampleClass {
        public String getStudentName() {
            return "student";
        }
        public String getCourseNum() {
            return "comp3111";
        }
        public String getExamName() {
            return "final";
        }
        public String getScore() {
            return "100";
        }
        public String getFullScore() {
            return "100";
        }
        public String getTimeSpend() {
            return "60";
        }
    }
     */

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private ChoiceBox<String> examCombox;
    @FXML
    private ChoiceBox<String> studentCombox;
    @FXML
    private TableView<Grade> gradeTable;
    @FXML
    private TableColumn<Grade, String> studentColumn;
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
    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis categoryAxisBar;
    @FXML
    NumberAxis numberAxisBar;
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    CategoryAxis categoryAxisLine;
    @FXML
    NumberAxis numberAxisLine;
    @FXML
    PieChart pieChart;

    private final ObservableList<Grade> gradeList = FXCollections.observableArrayList();

    /**
     * The function to set up the page and initialize needed data used in the page
     * @author Wong Hon Yin
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set up pie chart
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Student Scores");
        lineChart.setLegendVisible(false);
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Avg. Score");

        // set up the gradeTable
        //gradeList.add(new Grade());
        //gradeTable.setItems(gradeList);
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("CourseNum"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpend"));

        // populate ComboBoxed
        // show all courses
        populateComboBoxes();

        refresh();
        loadChart();
    }

    /**
     * The function for the refresh button
     * update comboBox
     * update grade list
     * @author Wong Hon Yin
     */
    @FXML
    public void refresh() {
        // Clear the current grade list
        gradeList.clear();

        // Optionally, you can repopulate the TableView with all grades or based on some default criteria
        List<Grade> allGrades = DatabaseService.getGradeDatabase().getAll();
        gradeList.addAll(allGrades); // Add all grades to the list

        gradeTable.setItems(gradeList);

        //new add
        populateComboBoxes();
        loadChart();

        // will not clear the comboBox, only update data
        query();
    }

    /**
     * Helper function for updating the comboBoxes
     * @author Wong Hon Yin
     */
    private void populateComboBoxes() {
        // Clear existing items from the ComboBoxes
        courseCombox.getItems().clear();
        examCombox.getItems().clear();
        studentCombox.getItems().clear();

        // Use Sets to avoid duplicates
        Set<String> uniqueCourses = new HashSet<>();
        Set<String> uniqueExams = new HashSet<>();
        Set<String> uniqueStudents = new HashSet<>();

        // Populate the sets with unique values from grades
        List<Grade> allGrades = DatabaseService.getGradeDatabase().getAll();
        for (Grade grade : allGrades) {
            uniqueCourses.add(grade.getCourseNum());
            uniqueExams.add(grade.getExamName());
            uniqueStudents.add(grade.getStudentName());
        }

        // Add unique items to the ComboBoxes
        courseCombox.getItems().addAll(uniqueCourses);
        examCombox.getItems().addAll(uniqueExams);
        studentCombox.getItems().addAll(uniqueStudents);
    }

    /**
     * Function to load the charts
     * @author Wong Hon Yin
     */
    private void loadChart() {

        // Clear existing data from the charts
        barChart.getData().clear();
        pieChart.getData().clear();
        lineChart.getData().clear();

        if (gradeList.isEmpty()) {
            System.out.println("Grade list is empty. Cannot load charts.");
            return; // Exit the method if there are no grades to process
        }


        // Bar Chart -> avg scores per course
        // Bar Chart: Average scores per course
        Map<String, Double> courseScores = new HashMap<>();
        Map<String, Integer> courseCounts = new HashMap<>();

        for (Grade grade : gradeList) {
            String course = grade.getCourseNum();
            double score = Double.parseDouble(grade.getScore());
            String scoreString = grade.getScore();

            if (course == null || scoreString == null)
            {
                System.out.println("Course or score is null. Cannot load charts.");
                continue;
            }

            // Update total score and count for each course
            courseScores.put(course, courseScores.getOrDefault(course, 0.0) + score);
            courseCounts.put(course, courseCounts.getOrDefault(course, 0) + 1);
        }

        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        for (String course : courseScores.keySet()) {
            double avgScore = courseScores.get(course) / courseCounts.get(course);
            seriesBar.getData().add(new XYChart.Data<>(course, avgScore));
        }
        barChart.getData().add(seriesBar);

        // Pie Chart: Distribution of scores among students
        Map<String, Double> studentScores = new HashMap<>();
        for (Grade grade : gradeList) {
            String student = grade.getStudentName();
            double score = Double.parseDouble(grade.getScore());

            studentScores.put(student, studentScores.getOrDefault(student, 0.0) + score);
        }

        for (String student : studentScores.keySet()) {
            pieChart.getData().add(new PieChart.Data(student, studentScores.get(student)));
        }

        // Line Chart: Average scores per exam
        Map<String, Double> examScores = new HashMap<>();
        Map<String, Integer> examCounts = new HashMap<>();

        for (Grade grade : gradeList) {
            String exam = grade.getExamName();
            double score = Double.parseDouble(grade.getScore());

            examScores.put(exam, examScores.getOrDefault(exam, 0.0) + score);
            examCounts.put(exam, examCounts.getOrDefault(exam, 0) + 1);
        }

        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        for (String exam : examScores.keySet()) {
            double avgScore = examScores.get(exam) / examCounts.get(exam);
            seriesLine.getData().add(new XYChart.Data<>(exam, avgScore));
        }
        lineChart.getData().add(seriesLine);

    }

    /**
     * The function to reset the filter
     * @author Wong Hon Yin
     */
    @FXML
    public void reset() {
        // Clear selections from ComboBoxes without altering the items
        courseCombox.getSelectionModel().clearSelection();
        examCombox.getSelectionModel().clearSelection();
        studentCombox.getSelectionModel().clearSelection();

        // Clear the current grade list
        gradeList.clear();

        // Fetch all grades from the database and display them
        List<Grade> allGrades = DatabaseService.getGradeDatabase().getAll();
        gradeList.addAll(allGrades); // Add all grades to the list

        // Set the items for the TableView
        gradeTable.setItems(gradeList);

        // Load charts with the current data
        loadChart();

    }

    /**
     * filter function
     * @author Wong Hon Yin
     */
    @FXML
    public void query() {
        String selectedCourse = courseCombox.getValue();
        String selectedExam = examCombox.getValue();
        String selectedStudent = studentCombox.getValue();

        gradeList.clear(); // Clear the existing list

        // Query all grades and filter based on selected criteria
        List<Grade> allGrades = DatabaseService.getGradeDatabase().getAll();
        for (Grade grade : allGrades) {
            boolean matches = true;
            
            // Check course
            if (selectedCourse != null && !selectedCourse.equals(grade.getCourseNum())) {
                matches = false;
            }

            // Check exam
            if (selectedExam != null && !selectedExam.equals(grade.getExamName())) {
                matches = false;
            }

            // Check student
            if (selectedStudent != null && !selectedStudent.equals(grade.getStudentName())) {
                matches = false;
            }

            // If all criteria match, add the grade to the list
            if (matches) {
                gradeList.add(grade);
            }
        }

        // Refresh the TableView with the filtered grades
        gradeTable.setItems(gradeList);

        // load chart
        loadChart();

    }
}
