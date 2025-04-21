package comp3111.examsystem.controller;

import comp3111.examsystem.Database;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.CourseRegRecord;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.CourseRegistrationRecordManagementService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import comp3111.examsystem.DatabaseService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.MsgSender.showMsg;

public class ManagerCourseRegManagementController implements Initializable {

    @FXML
    private ChoiceBox<String> CourseChoice;

    @FXML
    private TableView<CourseRegRecord> CourseRegTable;

    @FXML
    private ChoiceBox<String> StudentChoice;

    @FXML
    private TableColumn<CourseRegRecord, String> colCourseRegistered;

    @FXML
    private TableColumn<CourseRegRecord, String> colUsername;

    @FXML
    private TextField filterCourse;

    @FXML
    private TextField filterUsernameIN;

    private ObservableList<CourseRegRecord> courseRegRecords;

    /**
     * The function for initializing the page for course register management
     * @author Wong Hon Yin
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the TableView and load data
        courseRegRecords = FXCollections.observableArrayList(DatabaseService.getCourseRegRecordDatabase().getAll());
        CourseRegTable.setItems(courseRegRecords);

        // Set up the columns
        colCourseRegistered.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("StudentName"));

        // Load choices for CourseChoice and StudentChoice
        loadChoices();
    }
    
    /**
     * The function to load the choices for the choice boxes
     * @author Wong Hon Yin
     */
    private void loadChoices() {
        // load all choice for the add course column

        // clear every thing in it
        StudentChoice.getItems().clear();
        CourseChoice.getItems().clear();

        // load item to it
        List<Student> students = DatabaseService.getStudentDatabase().getAll();
        for (Student student : students) {
            StudentChoice.getItems().add(student.getUsername());
        }

        List<Course> courses = DatabaseService.getCourseDatabase().getAll();
        for (Course course : courses) {
            CourseChoice.getItems().add(course.getCourseId());
        }
    }

    /**
     * This function will add the data inputted in choice box to the reg course table/database
     * @author Wong Hon Yin
     * @param event mouse click the add button
     */
    @FXML
    void AddCourseRegRecord(MouseEvent event) {
        String selectedCourse = CourseChoice.getValue();
        String selectedStudent = StudentChoice.getValue();

        String addCourseRegRecordStatus = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService(selectedCourse, selectedStudent);

        // null student
        if(addCourseRegRecordStatus.equals("STUDENT_IS_NULL")){
            showMsg("Please select a student first");
            return;
        }

        // null course
        if(addCourseRegRecordStatus.equals("COURSE_IS_NULL")){
            showMsg("Please select a course first");
            return;
        }

        // course key not found
        if(addCourseRegRecordStatus.equals("NO_COURSE_SELECTED")){
            showMsg("Please select a course first");
            return;
        }


        // student key not found
        if(addCourseRegRecordStatus.equals("NO_STUDENT_SELECTED")){
            showMsg("Please select a student first");
            return;
        }


        //record already exists
        if(addCourseRegRecordStatus.equals("RECORD_ALREADY_EXISTS")){
            showMsg("This student has registered this course already");
            return;
        }


        // success
        if(addCourseRegRecordStatus.equals("RECORD_ADDED")){
            showMsg("Course " + selectedCourse + " has been added to " + selectedStudent);
            Refresh(null);
            return;
        }


        // error
        System.out.println(addCourseRegRecordStatus);
        System.out.println("add Course reg record error");
    }

    /**
     * The function to handle delete Course Registration record
     * @author Wong Hon Yin
     * @param event mouse event of clicking the Delete button
     */
    @FXML
    void DeleteCourseRegRecord(MouseEvent event) {
        CourseRegRecord selectedRecord = CourseRegTable.getSelectionModel().getSelectedItem();

        if (CourseRegistrationRecordManagementService.deleteCourseRegistrationRecordService(selectedRecord)) {
            showMsg("Course " + selectedRecord.getCourseName() + " has been dropped for " + selectedRecord.getStudentName());
        } else {
            showMsg("Please select a course first");
        }

        Refresh(null);
    }

    /**
     * The function for Filtering the table of the course registration records
     * @author Wong Hon Yin
     * @param event the mouse action of clicking the Filter button
     */
    @FXML
    void Filter(MouseEvent event) {
        String courseFilter = filterCourse.getText();
        String usernameFilter = filterUsernameIN.getText();

        List<CourseRegRecord> filteredCourseRegRecords = DatabaseService.getCourseRegRecordDatabase().getAll();

        // Apply filters
        if (!courseFilter.isEmpty()) {
            filteredCourseRegRecords.removeIf(record -> !record.getCourseName().toLowerCase().contains(courseFilter.toLowerCase()));
        }

        if (!usernameFilter.isEmpty()) {
            filteredCourseRegRecords.removeIf(record -> !record.getStudentName().toLowerCase().contains(usernameFilter.toLowerCase()));
        }

        // Update the TableView with the filtered list
        CourseRegTable.setItems(FXCollections.observableArrayList(filteredCourseRegRecords));
    }

    /**
     * The function for refreshing the table
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the Refresh button
     */
    @FXML
    void Refresh(MouseEvent event) {
        courseRegRecords.clear();
        courseRegRecords.addAll(DatabaseService.getCourseRegRecordDatabase().getAll()); // Reload records from the database
        CourseRegTable.setItems(courseRegRecords); // Reset table view items

        Filter(null);
    }

    /**
     * The function for resetting the filter for the table
     * @author Wong Hon Yin
     * @param event the mouse event of clicking the Reset button
     */
    @FXML
    void ResetFilter(MouseEvent event) {
        filterCourse.clear();
        filterUsernameIN.clear();
        CourseRegTable.setItems(courseRegRecords); // Reset to original list

    }

}

