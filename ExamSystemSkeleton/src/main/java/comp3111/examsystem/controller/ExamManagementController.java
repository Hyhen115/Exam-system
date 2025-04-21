/**
 * The ExamManagementController class is responsible for handling the logic and UI interaction for managing exams
 * in the Exam System. It allows users (teachers) to add, update, and delete exams, as well as manage questions
 * associated with each exam.
 * This controller interacts with the database to manage exam data, including adding and removing questions from
 * exams, and applying filters to display relevant data.
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */


package comp3111.examsystem.controller;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.service.ExamService;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.Record;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import comp3111.examsystem.entity.Grade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ExamManagementController {
    @FXML
    public TableView<Exam> examTableView;
    @FXML
    public TableColumn<Exam, String> examNameColumn, courseIdColumn, examTimeColumn, publishColumn;
    @FXML
    public TableColumn<Question, String> allQuestionScoreColumn ,allQuestionTypeColumn,allQuestionColumn;
    @FXML
    public TextField examNameField,examTimeField, examNameFilter,questionFilter,scoreFilter;
    @FXML
    public ChoiceBox<String> courseIdComboBox,publishComboBox,courseIdFilter,publishFilter,typeFilter;
    @FXML
    public TableView<Question> allQuestionsTableView;
    @FXML
    public TableView<Record> questionsInExamTableView;
    @FXML
    public TableColumn<Record, String> questionScoreInExamColumn,questionTypeInExamColumn,questionInExamColumn;

    private final ExamService examService = new ExamService();
    private final ObservableList<Exam> examObservableList = FXCollections.observableArrayList();
    private final ObservableList<Record> recordObservableList = FXCollections.observableArrayList();
    private final ObservableList<Question> questionObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller by setting up the ComboBoxes, TableView, and applying default filters.
     * It also adds a listener to handle exam selection and loading associated questions.
     * @author Seokhyeon Hong
     */
    @FXML
    public void initialize(){
        populateCourseComboBoxes();
        if (publishComboBox!=null) publishComboBox.getItems().addAll("yes", "no");
        if (publishFilter!= null) publishFilter.getItems().addAll("yes","no");
        if (typeFilter!= null) typeFilter.getItems().addAll("Single", "Multiple");

        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        examTimeColumn.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        publishColumn.setCellValueFactory(new PropertyValueFactory<>("publish"));

        allQuestionColumn.setCellValueFactory(new PropertyValueFactory<>("Question"));
        allQuestionScoreColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));
        allQuestionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));

        questionInExamColumn.setCellValueFactory(new PropertyValueFactory<>("questionName"));
        questionScoreInExamColumn.setCellValueFactory(new PropertyValueFactory<>("questionScore"));
        questionTypeInExamColumn.setCellValueFactory(new PropertyValueFactory<>("questionType"));

        refresh();

        examTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                loadQuestionsForSelectedExam(newValue);
                examNameField.setText(newValue.getExamName());
                examTimeField.setText(newValue.getExamTime());
                String selectedCourseKey = newValue.getCourseKey();
                Course selectedCourse = DatabaseService.getCourseDatabase().getAll().stream()
                        .filter(course -> String.valueOf(course.getCourseKey()).equals(selectedCourseKey))
                        .findFirst()
                        .orElse(null);

                if (selectedCourse != null) {
                    courseIdComboBox.setValue(selectedCourse.getCourseId());
                }
                publishComboBox.setValue(newValue.getPublish());
            }
            else {
                clearSelection();
            }
        });
    }

    /**
     * Loads the questions associated with the selected exam into the 'questionsInExamTableView'.
     *
     * @param selectedExam The selected exam whose questions need to be loaded.
     * @author Seokhyeon Hong
     */
    private void loadQuestionsForSelectedExam(Exam selectedExam) {
        if (selectedExam == null) return;

        // Clear the current records in questionsInExamTableView
        recordObservableList.clear();

        // Fetch records related to the selected exam
        List<Record> allRecords = examService.getAllRecords();
        List<Record> filteredRecords = new ArrayList<>();

        String selectedExamKey = String.valueOf(selectedExam.getId());
        for (Record record : allRecords) {
            if (selectedExamKey.equals(record.getExamKey())) {
                filteredRecords.add(record);
            }
        }

        // Update the table with the filtered records
        recordObservableList.setAll(filteredRecords);
        questionsInExamTableView.setItems(recordObservableList);
    }

    /**
     * Populates the course ComboBoxes with the list of course IDs from the database.
     * @author Seokhyeon Hong
     */
    private void populateCourseComboBoxes() {
        List<Course> courses = examService.getAllCourses();
        List<String> courseIds = new ArrayList<>();

        for (Course course : courses) {
            courseIds.add(course.getCourseId()); // Assuming getCourseID() returns the course ID as a String
        }

        if (courseIdComboBox != null) {
            courseIdComboBox.getItems().addAll(courseIds);
        }
        if (courseIdFilter != null) {
            courseIdFilter.getItems().addAll(courseIds);
        }
    }

    /**
     * Refreshes the tables to reflect the current state of the database for exams and questions.
     * It also restores the previous selection of an exam if available.
     * @author Seokhyeon Hong
     */
    @FXML
    public void refresh() {
        // Store the currently selected exam's ID
        Exam selectedExam = examTableView.getSelectionModel().getSelectedItem();
        String selectedExamId = (selectedExam != null) ? String.valueOf(selectedExam.getId()) : null;

        // Update the exam and question lists
        List<Exam> allExams = examService.getAllExams();
        List<Question> allQuestions = examService.getAllQuestions();
        examObservableList.setAll(allExams);
        questionObservableList.setAll(allQuestions);

        // Restore the selection if there was a previously selected item
        if (selectedExamId != null) {
            for (Exam exam : examObservableList) {
                if (String.valueOf(exam.getId()).equals(selectedExamId)) {
                    examTableView.getSelectionModel().select(exam);
                    break;
                }
            }
        }
        recordObservableList.clear();
        questionsInExamTableView.setItems(recordObservableList);
        clearSelection();
        applyBothFilter();
    }

    /**
     * Applies both the question filter and the exam filter.
     * @author Seokhyeon Hong
     */
    private void applyBothFilter() {
        onQuestionFilter();
        onExamFilter();
    }

    /**
     * Filters the questions based on user inputs in the UI filters (question text, score, and type).
     * @author Seokhyeon Hong
     */
    @FXML
    public void onQuestionFilter() {
        String selectedQuestion = questionFilter.getText().trim();
        String selectedType = typeFilter.getValue();
        String selectedScore = scoreFilter.getText().trim();

        questionObservableList.clear();

        List<Question> allQuestions = examService.getAllQuestions();
        for(Question question : allQuestions){
            boolean matches = true;

            if(!(selectedQuestion.isEmpty()) && !question.getQuestion().contains(selectedQuestion)){
                matches = false;
            }
            if(selectedType != null && !selectedType.equals(question.getType())){
                matches = false;
            }
            if(!(selectedScore.isEmpty()) && !question.getScore().contains(selectedScore)){
                matches = false;
            }

            if(matches){
                questionObservableList.add(question);
            }
        }
        allQuestionsTableView.setItems(questionObservableList);
    }

    /**
     * Resets the question filters and restores the original list of questions in the table.
     * @author Seokhyeon Hong
     */
    @FXML
    public void onResetQuestionFilter() {
        questionFilter.clear();
        scoreFilter.clear();
        typeFilter.getSelectionModel().clearSelection();
        questionObservableList.clear();

        List<Question> allQuestions = examService.getAllQuestions();
        questionObservableList.addAll(allQuestions);

        allQuestionsTableView.setItems(questionObservableList);

    }

    /**
     * Filters the exams based on user inputs in the UI filters (exam name, course ID, and publish status).
     * @author Seokhyeon Hong
     */
    @FXML
    public void onExamFilter() {
        String selectedExam = examNameFilter.getText().trim();
        String selectedCourseID = courseIdFilter.getValue();
        String selectedPublish = publishFilter.getValue();

        examObservableList.clear();

        List<Exam> allExams = examService.getAllExams();
        for(Exam exam : allExams){
            boolean matches = true;

            if(!(selectedExam.isEmpty()) && !exam.getExamName().contains(selectedExam)){
                matches = false;
            }
            if( selectedCourseID != null && !selectedCourseID.equals(exam.getCourseNum())){
                matches = false;
            }
            if( selectedPublish != null && !selectedPublish.equals(exam.getPublish())){
                matches = false;
            }

            if(matches){
                examObservableList.add(exam);
            }
        }
        examTableView.setItems(examObservableList);
    }

    /**
     * Resets the exam filters and restores the original list of exams in the table.
     * @author Seokhyeon Hong
     */
    @FXML
    public void onResetExamFilter() {
        examNameFilter.clear();
        courseIdFilter.getSelectionModel().clearSelection();
        publishFilter.getSelectionModel().clearSelection();

        examObservableList.clear();

        List<Exam> allExams = examService.getAllExams();
        examObservableList.addAll(allExams);

        examTableView.setItems(examObservableList);
    }

    /**
     * Deletes the selected question from the exam.
     * If there is no question left after execution of delete,
     * it will set publish status to be no.
     * @author Seokhyeon Hong
     */
    @FXML
    public void onDeleteLeft() {
        Record selectedRecord = questionsInExamTableView.getSelectionModel().getSelectedItem();

        if (selectedRecord == null) {
            showAlert("Please select a question in the exam to delete.");
            return;
        }

        // Delete the selected Record from the database
        examService.deleteQuestionFromExam(selectedRecord.getId());
        showAlert("Question removed from the exam.");

        Exam selectedExam = examTableView.getSelectionModel().getSelectedItem();
        if (selectedExam != null) {
            List<Record> remainingRecords = examService.getRecordsForExam(selectedExam.getId());
            if (remainingRecords.isEmpty()) {
                selectedExam.setPublish("no");
                examService.updateExam(selectedExam);
                showAlert("No questions remain in the exam. The publish status has been set to 'no'.");
            }
        }
        refresh(); // Refresh tables to reflect changes
    }

    /**
     * Adds a selected question to the selected exam.
     * An exam must be created for the question to be added.
     * To create an exam, the user must go to Question Bank Management system, create an exam on it.
     * @author Seokhyeon Hong
     */
    @FXML
    public void onAddLeft() {
        Exam selectedExam = examTableView.getSelectionModel().getSelectedItem();
        Question selectedQuestion = allQuestionsTableView.getSelectionModel().getSelectedItem();

        if (selectedExam == null) {
            showAlert("Please select an exam.");
            return;
        }
        if (selectedQuestion == null) {
            showAlert("Please select a question to add.");
            return;
        }

        examService.addQuestionToExam(selectedExam.getId(), selectedQuestion.getId());

        showAlert("Question added to the exam.");
        refresh(); // Refresh tables to reflect changes
    }

    /**
     * Deletes the selected exam and associated records and grades from the database.
     * There must be selected exam for it to be removed.
     * If not, it will display an alert
     * @author Seokhyeon Hong
     */
    @FXML
    public void onDelete() {
        Exam selectedExam = examTableView.getSelectionModel().getSelectedItem();
        if(selectedExam == null){
            showAlert("Please select an exam to delete");
            return;
        }
        examService.deleteRecordsForExam(selectedExam.getId());
        examService.deleteGradesForExam(selectedExam.getId());
        examService.deleteExam(selectedExam.getId());
        showAlert("Exam has been successfully deleted");
        refresh();

    }

    /**
     * Adds a new exam to the system.It will get the information from the user input fields
     * and will add to the exam database.
     * There are some criteria to be met for the new exam to be added to the database
     * 1. All the relevant fields must be filled
     * 2. Exam time must be a positive integer
     * 3. Course must be found in the Course Database
     * 4. Can be only published if there is at least one question in the exam.
     * 5. Before adding question, there must be an exam created
     *
     * @author Seokhyeon Hong
     */
    @FXML
    public void onAdd() {
        String examName = examNameField.getText().trim();
        String examTime = examTimeField.getText().trim();
        String CourseID = courseIdComboBox.getValue();
        String publish = publishComboBox.getValue();

        if(examName.isEmpty() || examTime.isEmpty() || CourseID == null || publish == null){
            showAlert("All fields must be filled in");
        }

        try {
            int examTimeValue = Integer.parseInt(examTime);
            if (examTimeValue <= 0) {
                showAlert("Exam time must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Exam time must be a positive integer.");
            return;
        }
        Course selectedCourse = DatabaseService.getCourseDatabase().getAll().stream()
                .filter(course -> course.getCourseId().equals(CourseID))
                .findFirst()
                .orElse(null);

        if (selectedCourse == null) {
            showAlert("Course not found.");
            return;
        }

        if (publish.equals("yes") && recordObservableList.isEmpty()) {
            showAlert("An exam requires at least one question to be published.");
            return;
        }

        Long courseKey = selectedCourse.getCourseKey();
        boolean examExists = examService.getAllExams().stream()
                .anyMatch(exam -> exam.getExamName().equals(examName) &&
                        exam.getCourseKey().equals(String.valueOf(courseKey)) &&
                        exam.getExamTime().equals(examTime));

        if (examExists) {
            showAlert("An exam with the same name, course ID, and exam time already exists.");
            return;
        }
        Exam exam = new Exam();
        exam.setExamName(examName);
        exam.setExamTime(examTime);
        exam.setCourseId(String.valueOf(courseKey));
        exam.setPublish(publish);

        examService.addExam(exam);
        refresh();
        showAlert("Exam has been successfully added");

    }

    /**
     * Updates the selected exam in the database with new details.
     * This happens when update button is pressed
     * There are some criteria to be met for the new exam to be added to the database
     *      * 1. All the relevant fields must be filled
     *      * 2. Exam time must be a positive integer
     *      * 3. Course must be found in the Course Database
     *      * 4. Can be only published if there is at least one question in the exam.
     *      * 5. Before adding question, there must be an exam created
     * @author Seokhyeon Hong
     */
    @FXML
    public void onUpdate() {
        Exam selectedExam = examTableView.getSelectionModel().getSelectedItem();
        if (selectedExam == null){
            showAlert("Please select an exam to update");
            return;
        }
        String updatedExamName = examNameField.getText().trim();
        String updatedExamTime = examTimeField.getText().trim();
        String updatedCourseID = courseIdComboBox.getValue();
        String updatedPublish = publishComboBox.getValue();

        if (updatedCourseID ==null || updatedPublish == null || updatedExamName.isEmpty() || updatedExamTime.isEmpty()){
            showAlert("All fields must be filled in");
            return;
        }
        try {
            int examTimeValue = Integer.parseInt(updatedExamTime);
            if (examTimeValue <= 0) {
                showAlert("Exam time must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Exam time must be a positive integer.");
            return;
        }
        Course selectedCourse = DatabaseService.getCourseDatabase().getAll().stream()
                .filter(course -> course.getCourseId().equals(updatedCourseID))
                .findFirst()
                .orElse(null);

        if (selectedCourse == null) {
            showAlert("Course not found.");
            return;
        }
        if (updatedPublish.equals("yes") && recordObservableList.isEmpty()) {
            showAlert("An exam requires at least one question to be published.");
            return;
        }
        Long updatedCourseKey = selectedCourse.getCourseKey();
        selectedExam.setExamName(updatedExamName);
        selectedExam.setExamTime(updatedExamTime);
        selectedExam.setPublish(updatedPublish);
        selectedExam.setCourseId(String.valueOf(updatedCourseKey));

        examService.updateExam(selectedExam);

        refresh();
        showAlert("Exam has been successfully updated");
        clearSelection();
    }

    /**
     * Displays an alert message with the provided content text.
     *
     * @param message The message to display in the alert.
     *
     * @author Seokhyeon Hong
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears all the information fields in the examName,examTime, CourseId, Publish information
     * @author Seokhyeon Hong
     */
    private void clearSelection(){
        examNameField.clear();
        examTimeField.clear();
        courseIdComboBox.getSelectionModel().clearSelection();
        publishComboBox.getSelectionModel().clearSelection();
    }
}
