package comp3111.examsystem.controller;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.CourseRegRecord;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentManagementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.MsgSender.showMsg;

public class ManagerStudentManagementController implements Initializable {

    @FXML
    private ChoiceBox<String> GenderChoice;

    @FXML
    private TextField AgeIN;

    @FXML
    private TableColumn<Student, String> colAge;

    @FXML
    private TableColumn<Student, String> colDepartment;

    @FXML
    private TableColumn<Student, String> colGender;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colPassword;

    @FXML
    private TableColumn<Student, String> colUsername;

    @FXML
    private TextField departmentIN;

    @FXML
    private TextField filterDepartmentIN;

    @FXML
    private TextField filterNameIN;

    @FXML
    private TextField filterUsernameIN;

    @FXML
    private TextField nameIN;

    @FXML
    private TextField passwordIN;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TextField usernameIN;

    // initialize list for the list
    ObservableList<Student> studentsObservableList = FXCollections.observableArrayList();

    private String selectedGender = "";

    /**
     * The function for the setup of the page and initialize the needed data used in the page
     * @author Wong Hon Yin
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up the choice box with gender options
        GenderChoice.setItems(FXCollections.observableArrayList("Male", "Female", "Non-Binary"));
        GenderChoice.getSelectionModel().selectFirst(); // Select first option by default
        selectedGender = GenderChoice.getValue(); // Initialize selectedGender

        // Fetch data from the database
        List<Student> students = DatabaseService.getStudentDatabase().getAll();

        // Set up the table view
        studentTable.setItems(FXCollections.observableArrayList(students));

        // Set up columns
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        studentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                usernameIN.setText(newValue.getUsername());
                nameIN.setText(newValue.getName());
                AgeIN.setText(newValue.getAge());
                departmentIN.setText(newValue.getDepartment());
                passwordIN.setText(newValue.getPassword());

                selectedGender = newValue.getGender();

                // Set the ChoiceBox selections
                GenderChoice.setValue(selectedGender);
            }
        });

        // Set listener for ChoiceBox to update selectedGender
        GenderChoice.setOnAction(event -> {
            selectedGender = GenderChoice.getValue(); // Update selectedGender when a new gender is selected
        });


    }

    /**
     * The function of add button
     * @author Wong Hon Yin
     * @param event the mouse event clicking add button
     */
    @FXML
    void AddStudent(MouseEvent event) {
        String username = usernameIN.getText();
        String name = nameIN.getText();
        String age = AgeIN.getText();
        String gender = selectedGender;
        String department = departmentIN.getText();
        String password = passwordIN.getText();

        String addStudentStatus = StudentManagementService.addStudentService(username, name, age, gender, department, password);

        // is empty
        if(addStudentStatus.equals("EMPTY_FIELDS")) {
            showMsg("Please fill all the fields");
            return;
        }

        // check username , >4 words
        if(addStudentStatus.equals("USERNAME_TOO_SHORT"))
        {
            showMsg("Please enter a valid username with at least 5 characters");
            return;
        }

        // check for exist username
        if(addStudentStatus.equals("STUDENT_ALREADY_EXISTS")){
            showMsg("Student already exists");
            return;
        }

        // validate age
        if(addStudentStatus.equals("INVALID_AGE")) {
            showMsg("Please enter a valid age");
            return;
        }

        // invalid age range
        if(addStudentStatus.equals("INVALID_AGE_RANGE")){
            showMsg("Please enter a valid age between 0 and 100");
            return;
        }

        // check password, >= 8 words
        if(addStudentStatus.equals("PASSWORD_TOO_SHORT")) {
            showMsg("Please enter a valid password at least 8 characters");
            return;
        }

        if(addStudentStatus.equals("STUDENT_ADDED")) {
            Refresh(null); // Refresh the table view
            showMsg("Student added Successfully");
            return;
        }

        // error
        System.out.println(addStudentStatus);
        System.out.println("add student error");

    }

    /**
     * The function for delete button
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the delete button
     */
    @FXML
    void DeleteStudent(MouseEvent event) {
        // fetch all student
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (StudentManagementService.deleteStudentService(selectedStudent)) {
            Refresh(null); // Refresh the table view
            showMsg("Student deleted successfully");
        } else {
            showMsg("Error: No student selected for deletion.");
        }
    }

    /**
     * The function for filter button
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the filter button
     */
    @FXML
    void Filter(MouseEvent event) {
        String filterName = filterNameIN.getText();
        String filterDepartment = filterDepartmentIN.getText();
        String filterUsername = filterUsernameIN.getText();

        List<Student> filteredStudents = DatabaseService.getStudentDatabase().getAll();

        //Apply filters
        if (!filterName.isEmpty()) {
            filteredStudents.removeIf(student -> !student.getName().toLowerCase().contains(filterName.toLowerCase()));
        }
        if (!filterUsername.isEmpty()) {
            filteredStudents.removeIf(student -> !student.getUsername().toLowerCase().contains(filterUsername.toLowerCase()));
        }
        if (!filterDepartment.isEmpty()) {
            filteredStudents.removeIf(student -> !student.getDepartment().toLowerCase().contains(filterDepartment.toLowerCase()));
        }

        studentsObservableList.setAll(filteredStudents);
        studentTable.setItems(studentsObservableList);


    }

    /**
     * The function for refresh button
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the refresh button
     */
    @FXML
    void Refresh(MouseEvent event) {
        List<Student> students = DatabaseService.getStudentDatabase().getAll();
        studentsObservableList.setAll(students);
        studentTable.setItems(studentsObservableList);

        Filter(null);
    }

    /**
     * The function for reset button
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the reset button
     */
        @FXML
    void ResetFilter(MouseEvent event) {
        filterNameIN.clear();
        filterUsernameIN.clear();
        filterDepartmentIN.clear();
        Refresh(null); // Refresh the table to show all students
    }

    /**
     * The function of update button
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the update button
     */
    @FXML
    void Update(MouseEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        String username = usernameIN.getText();
        String name = nameIN.getText();
        String age = AgeIN.getText();
        String gender = selectedGender;
        String department = departmentIN.getText();
        String password = passwordIN.getText();
        
        String updateStudentStatus = StudentManagementService.updateStudentService(selectedStudent, username, name, age, gender, department, password);

        //student null
        if (updateStudentStatus.equals("NO_SELECTED_STUDENT")) {
            showMsg("Error: No student selected for update.");
            return;
        }

        //empty fields
        if(updateStudentStatus.equals("EMPTY_FIELDS")) {
            showMsg("Please fill all the fields");
            return;
        }

        // check username , >4 words
        if(updateStudentStatus.equals("USERNAME_TOO_SHORT"))
        {
            showMsg("Please enter a valid username with at least 5 characters");
            return;
        }

        // exist username
        if(updateStudentStatus.equals("STUDENT_ALREADY_EXISTS")){
            showMsg("username already exists");
            return;
        }

        // non valid age
        if(updateStudentStatus.equals("INVALID_AGE")) {
            showMsg("Please enter a valid age");
            return;
        }


        // non valid age range
        if(updateStudentStatus.equals("INVALID_AGE_RANGE")){
            showMsg("Please enter a valid age between 0 and 100");
            return;
        }

        // check password, >= 8 words
        if(updateStudentStatus.equals("PASSWORD_TOO_SHORT")) {
            showMsg("Please enter a valid password at least 8 characters");
            return;
        }


        // update database
        if(updateStudentStatus.equals("STUDENT_UPDATED")) {
            Refresh(null);
            showMsg("Student updated successfully");
            return;
        }

        // error
        System.out.println(updateStudentStatus);
        System.out.println("update student error");

    }

}
