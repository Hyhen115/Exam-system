/**
 * This QuestionBankManagementController implements questionbankmanagement system for ExamSystem
 * Allows adding/updating/filtering/removing questions to the database
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */


package comp3111.examsystem.controller;

import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for managing the question bank in the exam system.
 * Provides functionality for adding, updating, deleting, filtering, and displaying questions.
 * @author Seokhyeon Hong
 */
public class QuestionBankManagementController {
    @FXML
    public Button resetBtn;
    @FXML
    public Button filterBtn;
    @FXML
    public TextField questionNameFilterTxt;
    @FXML
    public TableColumn questionColumn, optionAColumn, optionBColumn, optionCColumn, optionDColumn, answerColumn, typeColumn, scoreColumn;
    @FXML
    public ChoiceBox<String> typeFilterComboX;
    @FXML
    private TableView<Question> questionTable;
    @FXML
    private TextField questionTxt, optionATxt, optionBTxt, optionCTxt, optionDTxt, answerTxt, scoreTxt, questionFilterTxt, scoreFilterTxt;
    @FXML
    private ChoiceBox<String> typeCombo;

    private final QuestionService questionService = new QuestionService();
    private final ObservableList<Question> questionObservableList = FXCollections.observableArrayList();

    /**
     * initialize function initializes the controller. Sets up Choicebox options, TableView Columns, and refreshes the question table.
     * @author Seokhyeon Hong
     */
    @FXML
    public void initialize(){
        if(typeCombo!= null) typeCombo.getItems().addAll("Single", "Multiple");
        if(typeFilterComboX != null) typeFilterComboX.getItems().addAll("Single", "Multiple");

        questionColumn.setCellValueFactory(new PropertyValueFactory<>("Question"));
        optionAColumn.setCellValueFactory(new PropertyValueFactory<>("OptionA"));
        optionBColumn.setCellValueFactory(new PropertyValueFactory<>("OptionB"));
        optionCColumn.setCellValueFactory(new PropertyValueFactory<>("OptionC"));
        optionDColumn.setCellValueFactory(new PropertyValueFactory<>("OptionD"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("Answer"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));

        refresh();

        questionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                questionTxt.setText(newValue.getQuestion());
                optionATxt.setText(newValue.getOptionA());
                optionBTxt.setText(newValue.getOptionB());
                optionCTxt.setText(newValue.getOptionC());
                optionDTxt.setText(newValue.getOptionD());
                answerTxt.setText(newValue.getAnswer());
                scoreTxt.setText(newValue.getScore());
                typeCombo.setValue(newValue.getType());
            }
        });
    }

    /**
     * addQuestion function gets initiated when the user clicks on add button. It adds a new question to the database using the data entered in the text field.
     * Validates the input and shows an alert if any field is empty.
     * If any input is invalid, it will show the respective alert
     * @author Seokhyeon Hong
     */
    @FXML
    private void addQuestion() {
        // Logic to add a question
        String questionText = questionTxt.getText().trim();
        String optionA = optionATxt.getText().trim();
        String optionB = optionBTxt.getText().trim();
        String optionC = optionCTxt.getText().trim();
        String optionD = optionDTxt.getText().trim();
        String answer = answerTxt.getText().trim();
        String score = scoreTxt.getText().trim();
        String type = typeCombo.getValue();

        if(questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() ||
        optionD.isEmpty() || answer.isEmpty() || score.isEmpty() || type == null) {
            showAlert("All fields must be filled in");
        }

        if (!validateAnswerFormat(answer, type)) {
            return; // Do not proceed if answer format is invalid
        }
        if (!validateScore(score)){
            return;
        }
        List<Question> allQuestions = questionService.getAllQuestions();
        for (Question existingQuestion : allQuestions) {
            if (existingQuestion.getQuestion().equalsIgnoreCase(questionText) &&
                    existingQuestion.getOptionA().equalsIgnoreCase(optionA) &&
                    existingQuestion.getOptionB().equalsIgnoreCase(optionB) &&
                    existingQuestion.getOptionC().equalsIgnoreCase(optionC) &&
                    existingQuestion.getOptionD().equalsIgnoreCase(optionD) &&
                    existingQuestion.getAnswer().equalsIgnoreCase(answer) &&
                    existingQuestion.getScore().equals(score) &&
                    existingQuestion.getType().equalsIgnoreCase(type)) {
                showAlert("A question with the same details already exists in the database.");
                return;
            }
        }
        Question question = new Question();
        question.setQuestion(questionText);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setAnswer(answer);
        question.setScore(score);
        question.setType(type);

        questionService.addQuestion(question);
        refresh();
        showAlert("Question has been successfully added");

    }



    /**
     * Updates the selected question with new data entered in the text fields and from choice boxes.
     * If no question is selected, an alert is shown.
     * If any input is invalid, it will show respective alert
     * @author Seokhyeon Hong
     */
    @FXML
    private void updateQuestion() {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion == null) {
            showAlert("Please select a question to update.");
            return;
        }

        // Get the updated data from the text fields
        String updatedQuestion = questionTxt.getText().trim();
        String updatedOptionA = optionATxt.getText().trim();
        String updatedOptionB = optionBTxt.getText().trim();
        String updatedOptionC = optionCTxt.getText().trim();
        String updatedOptionD = optionDTxt.getText().trim();
        String updatedAnswer = answerTxt.getText().trim();
        String updatedScore = scoreTxt.getText().trim();
        String updatedType = typeCombo.getValue();

        // Validate the fields
        if (updatedQuestion.isEmpty() || updatedOptionA.isEmpty() || updatedOptionB.isEmpty() || updatedOptionC.isEmpty() ||
                updatedOptionD.isEmpty() || updatedAnswer.isEmpty() || updatedScore.isEmpty() || updatedType == null) {
            showAlert("All fields must be filled in");
            return;
        }
        if (!validateAnswerFormat(updatedAnswer, updatedType)) {
            return; // Do not proceed if answer format is invalid
        }

        if(!validateScore(updatedScore)){
            return;
        }
        // Update the selected question with new data
        selectedQuestion.setQuestion(updatedQuestion);
        selectedQuestion.setOptionA(updatedOptionA);
        selectedQuestion.setOptionB(updatedOptionB);
        selectedQuestion.setOptionC(updatedOptionC);
        selectedQuestion.setOptionD(updatedOptionD);
        selectedQuestion.setAnswer(updatedAnswer);
        selectedQuestion.setScore(updatedScore);
        selectedQuestion.setType(updatedType);

        // Update the question in the database
        questionService.updateQuestion(selectedQuestion);

        // Refresh the table to reflect the changes
        refresh();

        // Show a success message
        showAlert("Question has been successfully updated.");
    }

    /**
     * Deletes the selected question from the database.
     * If no question is selected, an alert is shown.
     * @author Seokhyeon Hong
     */
    @FXML
    private void deleteQuestion() {
        // Logic to delete selected question
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion == null) {
            showAlert("Please select a question to delete.");
            return;
        }

        questionService.deleteQuestion(String.valueOf(selectedQuestion.getId()));
        showAlert("Question has been successfully deleted.");
        refresh();
    }

    /**
     * Applies filters to the question list based on user input in the filter fields.
     * Updates the table with the filtered questions.
     * @author Seokhyeon Hong
     */
    @FXML
    public void applyFilter() {
        String selectedQuestion = questionNameFilterTxt.getText().trim();
        String selectedType = typeFilterComboX.getValue();
        String selectedScore = scoreFilterTxt.getText().trim();

        questionObservableList.clear();

        List<Question> allQuestions = questionService.getAllQuestions();
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
        clearSelection();
        questionTable.setItems(questionObservableList);
    }

    /**
     * Refreshes the question table with the latest data from the database.
     * Applies any active filters to the list.
     * @author Seokhyeon Hong
     */
    @FXML
    public void refresh() {
        questionObservableList.clear();

        List<Question> allQuestions = questionService.getAllQuestions();
        questionObservableList.addAll(allQuestions);
        questionTable.setItems(questionObservableList);
        clearSelection();
        applyFilter();
    }


    /**
     * Resets the filter fields and refreshes the question table with all available questions.
     * @author Seokhyeon Hong
     */
    @FXML
    public void resetFilter() {

        typeFilterComboX.getSelectionModel().clearSelection();
        questionNameFilterTxt.clear();
        scoreFilterTxt.clear();

        questionObservableList.clear();

        List<Question> allQuestions = questionService.getAllQuestions();
        questionObservableList.addAll(allQuestions);

        clearSelection();
        questionTable.setItems(questionObservableList);
    }

    /**
     * Clears all the input field for question.
     * @author Seokhyeon Hong
     */
    public void clearSelection(){
        questionTxt.clear();
        optionATxt.clear();
        optionBTxt.clear();
        optionCTxt.clear();
        optionDTxt.clear();
        answerTxt.clear();
        scoreTxt.clear();
        typeCombo.getSelectionModel().clearSelection();
    }

    /**
     * Validates the score format.
     * Valid score must be a positive integer.
     * @param score the score text to validate
     * @return true if the score format is valid, false otherwise
     * @author Seokhyeon Hong
     */
    private boolean validateScore(String score){
        try {
            int parsedScore = Integer.parseInt(score); // Try parsing the score
            if (parsedScore < 0) {
                showAlert("Score must be a non-negative integer.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showAlert("Invalid score format. Please enter a valid integer.");
            return false;
        }
    }
    /**
     * Validates the answer format based on the question type.
     * Shows an alert if the format is incorrect and returns false.
     *
     * @param answer the answer text to validate
     * @param type the type of question ("Single" or "Multiple")
     * @return true if the answer format is valid, false otherwise
     * @author Seokhyeon Hong
     */
    private boolean validateAnswerFormat(String answer, String type) {
        if (type.equals("Single")) {
            // Allow only a single letter A, B, C, or D for single-choice answers
            if (!answer.matches("^[A-D]$")) {
                showAlert("Invalid answer format for single-choice question. Please use only A, B, C, or D.");
                return false;
            }
        } else if (type.equals("Multiple")) {
            // Allow combinations of letters A, B, C, and D in alphabetical order for multiple-choice answers
            if (!answer.matches("^[A-D]{2,4}$") || !answer.equals(sortAnswer(answer))) {
                showAlert("Invalid answer format for multiple-choice question. Use combinations like AB, AC, or BCD in alphabetical order.");
                return false;
            }
        }
        return true;
    }

    /**
     * Sorts the answer string in alphabetical order.
     *
     * @param answer the answer text
     * @return the sorted answer text
     * @author Seokhyeon Hong
     */
    private String sortAnswer(String answer) {
        char[] chars = answer.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Displays an alert message using popup.
     * @param message the message to be displayed in the alert.
     * @author Seokhyeon Hong
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
