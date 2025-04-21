package comp3111.examsystem.controller;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.controller.StudentMainController;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.service.StudentExamService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import comp3111.examsystem.entity.Question;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;

import comp3111.examsystem.entity.Record;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class StudentExamController implements Initializable {

    @FXML
    private ListView<String> questionsList;

    @FXML
    private Label examName;

    @FXML
    private Label numberOfQuestionsLabel;

    @FXML
    private Label timeLimitLabel;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private TextArea QuestionDetailsTextArea;

    @FXML
    private VBox optionsBox;

    private LocalTime timeRemaining;
    private Timeline timeline;
    private int questionNumber;
    private String[] answers = null;
    private ToggleGroup answerSelection = new ToggleGroup();
    private Map<RadioButton, String> radioButtonLabelMap = new HashMap<>();
    private Map<CheckBox, String> checkBoxLabelMap = new HashMap<>();
    private int submitCounter = 0;
    private Exam exam = StudentMainController.ExamAndQuestionData.getExam();
    private List<Question> questions = StudentMainController.ExamAndQuestionData.getQuestions();
    private String selectedExamName = StudentMainController.ExamAndQuestionData.getSelectedExamName();
    private int examSize = questions.size();

    /**
     * Initializes the exam interface with the provided URL and resource bundle.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     * This method performs the following tasks:
     *  - Initializes the answers array based on the exam size.
     *  - Populates the question list view with questions.
     *  - Sets the exam name label.
     *  - Adds a listener to the question list view to display the selected question.
     *  - Displays the number of questions.
     *  - Sets up the exam time limit and starts the countdown timer.
     *  - Displays the initial question.
     *  - Prevents the exam tab from being closed.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Get the exam information from StudentMainController
        answers = new String[examSize];

        //Set up the question list on the side
        for (Question question : questions) {
            if (question != null) {
                questionsList.getItems().add(question.getQuestion());
            }
        }
        examName.setText(selectedExamName);

        //Add listeners to the question list
        questionsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Display the content of the selected item in the TextArea
            if (newValue != null) {
                //Find the question number of the listview
                questionNumber = questionsList.getSelectionModel().getSelectedIndex();
                //Clear the current answer choices
                optionsBox.getChildren().clear();
                //Load the question
                LoadQuestion(questionNumber);
            }
        });

        //Get number of questions
        String numberOfQuestions = Integer.toString(examSize);
        numberOfQuestionsLabel.setText("Number of Questions: " + numberOfQuestions);

        //Set up the time limit of the exam
        //Get the examTime
        String strExamTime = exam.getExamTime();
        int examTime = Integer.parseInt(strExamTime);
        int hours = examTime / 60;
        int minutes = examTime % 60;

        timeRemaining = LocalTime.of(hours,minutes,0);
        updateLabel();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining = timeRemaining.minusSeconds(1);
            updateLabel();
            if (timeRemaining.equals(LocalTime.of(0, 0, 0))) {
                timeline.stop();
                stopExam();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //Display the initial question
        LoadQuestion(0);

        //Do not allow tab to be closed
        Platform.runLater(this::cannotCloseStage);
    }

    /**
     * Function updateLabel() to update the time label. This is to correctly show the time remaining in the format of
     * hh:mm:ss
     */
    public void updateLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLimitLabel.setText("Time remaining: " + timeRemaining.format(formatter));
    }

    /**
     * Function stopExam() to stop exam when time is up.
     * Alert the user that the time is up, then submit the answers for questions that have been
     * completed, then close the exam UI.
     */
    public void stopExam() {
        showAlert("Notice", "Exam time is up!", () -> {submit(true);});
        closeExamUI();
    }

    /**
     * Function showAlert(title, text, onHidden) to show alert to user.
     * @param title is the title of the alert
     * @param text is the main message of the alert
     * @param onHidden is the function to run when alert is closed. If null, then simply show alert.
     */
    private void showAlert(String title, String text, Runnable onHidden) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.setOnHidden(event -> {
            if (onHidden != null) {
                onHidden.run();
            }
        });
        Platform.runLater(() -> alert.showAndWait());

    }

    /**
     * Function nextQuestion() to handle displaying the next question in the question list.
     */
    public void nextQuestion() {
        SaveAnswer(questionNumber);


        if ((questionNumber+1) < questionsList.getItems().size()) {
            questionNumber++;
            optionsBox.getChildren().clear();
            LoadQuestion(questionNumber);
        }
        else {
            showAlert("Notice", "There are no more questions. Please press submit if you have completed the exam.", null);
        }
    }

    /**
     * Function LoadQuestion(questionNumber) to load a specific question at index questionNumber
     * @param questionNumber is an index in the questionList. It represents the location of the question to be displayed.
     */
    public void LoadQuestion(int questionNumber) {
        questionNumberLabel.setText("Question " + (questionNumber+1));
        QuestionDetailsTextArea.setText(questions.get(questionNumber).getQuestion());
        radioButtonLabelMap.clear();
        checkBoxLabelMap.clear();

        //Set up the answer choices
        String[] options = new String[4];
        options[0] = questions.get(questionNumber).getOptionA();
        options[1] = questions.get(questionNumber).getOptionB();
        options[2] = questions.get(questionNumber).getOptionC();
        options[3] = questions.get(questionNumber).getOptionD();
        String[] choices = new String[4];
        choices[0] = "A";
        choices[1] = "B";
        choices[2] = "C";
        choices[3] = "D";
        String questionType = questions.get(questionNumber).getType();
        if (questionType.equals("Single")) {
            for (int i = 0; i < 4; i++) {
                Label AnswerChoiceLabel = new Label();
                AnswerChoiceLabel.setText(choices[i]);
                RadioButton radioButton = new RadioButton(options[i]);
                radioButton.setToggleGroup(answerSelection);
                radioButtonLabelMap.put(radioButton, choices[i]);

                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.getChildren().addAll(AnswerChoiceLabel, radioButton);
                optionsBox.getChildren().add(hbox);
                if (answers[questionNumber] != null && answers[questionNumber].equals(choices[i])) {
                    radioButton.setSelected(true);
                }
            }

        }
        else {
            for (int i = 0; i < 4; i++) {
                Label AnswerChoiceLabel = new Label();
                AnswerChoiceLabel.setText(choices[i]);
                CheckBox checkBox = new CheckBox(options[i]);
                checkBoxLabelMap.put(checkBox, choices[i]);

                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.getChildren().addAll(AnswerChoiceLabel, checkBox);
                optionsBox.getChildren().add(hbox);

                if (answers[questionNumber] != null) {
                    for (char savedChoice : answers[questionNumber].toCharArray()) {
                        if (String.valueOf(savedChoice).equals(choices[i])) {
                            checkBox.setSelected(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Function SaveAnswer(questionNumber) adds the answer of the student into an internal array
     * @param questionNumber is the index of the current question
     */
    public void SaveAnswer(int questionNumber){
        String choice = null;
        List<String> selectedChoices = new ArrayList<>();
        String questionType = questions.get(questionNumber).getType();
        if (questionType.equals("Single")) {
            RadioButton selectedRadioButton = (RadioButton) answerSelection.getSelectedToggle();
            if (answerSelection.getSelectedToggle() == null) {
                choice = null;
            }
            else {
                choice = radioButtonLabelMap.get(selectedRadioButton);
            }
        }
        else {
            //Handle checkboxes
            boolean noneSelected = true;
            for (CheckBox checkBox : checkBoxLabelMap.keySet()) {
                if (checkBox.isSelected()) {
                    noneSelected = false;
                    selectedChoices.add(checkBoxLabelMap.get(checkBox));
                }
            }
            if (noneSelected) {
                choice = null;
            }
            else {
                Collections.sort(selectedChoices);
                StringBuilder choices = new StringBuilder();
                for (String selectedChoice : selectedChoices) {
                    choices.append(selectedChoice);
                }
                choice = choices.toString();
            }
        }
        answers[questionNumber] = choice;
    }

    /**
     * Function submit(timeUp) is a function to submit the answer array. It gives alerts to the user if they really
     * want to submit the exam, and it also sends the user back to the exam if there are questions left unanswered.
     * @param timeUp checks if the submit function is passed due to time limit reached. If true, then the function
     *               will skip the checks and submit the answer immediately.
     */
    public void submit(boolean timeUp){
        SaveAnswer(questionNumber);
        if (timeUp) {
            //Immediately move on to the next interface
            checkAnswers(answers);
        }
        else {
            boolean missingAnswer = false;
            for (int i = 0; i < questionsList.getItems().size(); i++) {
                if (answers[i] == null) {
                    missingAnswer = true;
                }
            }
            //Check if any questions are unanswered
            if (missingAnswer) {
                showAlert("Notice", "One or more answers are missing. Please check again.", null);
                return;
            }
            //Second chance of submission in case of accident
            if (submitCounter == 0) {
                showAlert("Warning", "Are you sure you want to submit the exam? Press submit again if you would like to submit the exam.", null);
                submitCounter++;
            }
            //Final submit
            else if (submitCounter == 1) {
                showAlert("Notice", "Your answers will now be submitted.", () -> {
                    checkAnswers(answers);
                });
            }
        }
    }

    /**
     * Function handleSubmit() is a function to be called when the submit button is pressed. The function calls
     * submit(timeUp) function.
     */
    public void handleSubmit() {
        submit(false);
    }

    /**
     * Function checkAnswers(studentAnswer) compares the student's answer array with the answers stored in the database.
     * @param studentAnswer is the array of the student's answers for the exam that is to be marked.
     */
    public void checkAnswers(String[] studentAnswer) {
        String[] correctAnswers = new String[examSize];
        int totalScore = 0;
        int score = 0;
        int correctNumber = 0;
        for (int i = 0; i < examSize; i++) {

            correctAnswers[i] = questions.get(i).getAnswer();
            totalScore += Integer.parseInt(questions.get(i).getScore());

            if (studentAnswer[i] == null) {
                continue;
            }

            if (studentAnswer[i].equals(correctAnswers[i])) {
                score = score + Integer.parseInt(questions.get(i).getScore());
                correctNumber++;
            }
        }
        int finalScore = score;
        int finalTotalScore = totalScore;
        int finalCorrect = correctNumber;
        showAlert("Notice", correctNumber + "/" + examSize + " correct. Your score is " + finalScore + "/" + finalTotalScore + ".", ()->{
            StudentExamService studentExamService = new StudentExamService();
            studentExamService.addGrade(exam, timeRemaining, finalScore, finalTotalScore);
            closeExamUI();
        });
    }

    /**
     * Function closeExamUI() to close the exam interface.
     */
    public void closeExamUI() {
        Stage stage = (Stage) questionsList.getScene().getWindow();
        stage.close();
        timeline.stop();
    }

    /**
     * Function cannotCloseStage() to prevent the student from exiting the exam interface by clicking 'X'. This
     * prevents the student from leaving the exam without submission.
     */
    public void cannotCloseStage() {
        Stage stage = (Stage) questionsList.getScene().getWindow();
        stage.setOnCloseRequest(event->{
            event.consume();
            showAlert("Notice", "You are not allowed to close the exam. Please press submit if you have completed.", null);
        });
    }
}
