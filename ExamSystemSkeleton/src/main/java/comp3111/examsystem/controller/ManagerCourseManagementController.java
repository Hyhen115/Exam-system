package comp3111.examsystem.controller;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.*;
import comp3111.examsystem.entity.Record;
import comp3111.examsystem.service.CourseManagementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.MsgSender.showMsg;

public class ManagerCourseManagementController implements Initializable {

    @FXML
    private TableColumn<Course, String> colCourseID;

    @FXML
    private TableColumn<Course, String> colCourseName;

    @FXML
    private TableColumn<Course, String> colDepartment;

    @FXML
    private TextField courseNameIN;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TextField courseidIN;

    @FXML
    private TextField departmentIN;

    @FXML
    private TextField filterCourseIdIN;

    @FXML
    private TextField filterCourseNameIN;

    @FXML
    private TextField filterDepartmentIN;

    // initialize list for the list
    ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    /**
     * The function for the setup of initializing the stage
     *
     * @param url The location used to resolve relative paths for the root object, or
     *            {@code null} if the location is not known.
     * @param rb  The resources used to localize the root object, or {@code null} if
     *            the root object was not localized.
     * @author Wong Hon Yin
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //fetch data from database
        List<Course> course = DatabaseService.getCourseDatabase().getAll();

        //set the fetched data to list
        courseObservableList = FXCollections.observableArrayList(course);

        //setup columns
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        // set the courseObservableList to the table view
        courseTable.setItems(courseObservableList);

        // listener for table row selection
        courseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                courseidIN.setText(newValue.getCourseId());
                courseNameIN.setText(newValue.getCourseName());
                departmentIN.setText(newValue.getDepartment());
            }
        });
    }


    /**
     * Function for Add button
     *
     * @param event the mouse event of clicking the add button
     * @author Wong Hon Yin
     */
    @FXML
    void AddCourse(MouseEvent event) {
        String courseName = courseNameIN.getText();
        String department = departmentIN.getText();
        String courseID = courseidIN.getText();

        String addCourseStatus = CourseManagementService.addCourseService(courseName, department, courseID);

        // if have empty fields
        if (addCourseStatus.equals("EMPTY_FIELDS")) {
            showMsg("Please fill all the fields");
            return;
        }

        // check for existing courses
        if (addCourseStatus.equals("COURSE_ID_EXISTS")) {
            showMsg("Error! A course with this courseID already exists.");
            return;
        }

        if (addCourseStatus.equals("COURSE_NAME_EXISTS")) {
            showMsg("Error! A course with this courseName already exists.");
            return;
        }

        if (addCourseStatus.equals("COURSE_ADDED")) {
            Refresh(null); // Refresh the table view to show the new course
            showMsg("Course added successfully.");
            return;
        }

        // error
        System.out.println(addCourseStatus);
        System.out.println("Course add error");
    }

    /**
     * The function for delete button
     *
     * @param event the mouse event of clicking the delete button
     * @author Wong Hon Yin
     */
    @FXML
    void DeleteCourse(MouseEvent event) {
        // Fetch the selected course
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

        if (CourseManagementService.deleteCourseService(selectedCourse)) {
            // deleted student
            Refresh(null); // Refresh the table view to reflect the deletion
            showMsg("Course deleted successfully.");
        } else {
            showMsg("Error: No course selected for deletion.");
        }
    }

    /**
     * The function for filter button
     *
     * @param event the mouse event of clicking the filter button
     * @author Wong Hon Yin
     */
    @FXML
    void Filter(MouseEvent event) {
        String filterCourseId = filterCourseIdIN.getText();
        String filterCourseName = filterCourseNameIN.getText();
        String filterDepartment = filterDepartmentIN.getText();

        List<Course> filteredCourses = DatabaseService.getCourseDatabase().getAll();

        if (!filterCourseId.isEmpty()) {
            filteredCourses = DatabaseService.getCourseDatabase().queryFuzzyByField("courseId", filterCourseId);
        }
        if (!filterCourseName.isEmpty()) {
            filteredCourses = DatabaseService.getCourseDatabase().queryFuzzyByField("courseName", filterCourseName);
        }
        if (!filterDepartment.isEmpty()) {
            filteredCourses = DatabaseService.getCourseDatabase().queryFuzzyByField("department", filterDepartment);
        }

        courseObservableList.setAll(filteredCourses);
        courseTable.setItems(courseObservableList);

    }
    
    /**
     * The function for modify button
     *
     * @param event the mouse event of clicking the modify button
     * @author Wong Hon Yin
     */
    @FXML
    void Modify(MouseEvent event) {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

        String newCourseID = courseidIN.getText();
        String newCourseName = courseNameIN.getText();
        String newDepartment = departmentIN.getText();

        String modifyCourseStatus = CourseManagementService.modifyCourseService(selectedCourse, newCourseName, newDepartment, newCourseID);


        if (modifyCourseStatus.equals("COURSE_NOT_SELECTED")) {
            showMsg("Please Select a course for editing.");
            return;
        }

        // check empty
        if (modifyCourseStatus.equals("EMPTY_FIELDS")) {
            showMsg("Error! All fields must be filled in");
            return;
        }

        // check for existing courses (excluding the current one being modified)
        if(modifyCourseStatus.equals("COURSE_ID_EXISTS")) {
            showMsg("Error! A course with this courseID already exists.");
            return;
        }

        if (modifyCourseStatus.equals("COURSE_NAME_EXISTS")) {
            showMsg("Error! A course with this name already exists.");
            return;
        }

        if (modifyCourseStatus.equals("COURSE_MODIFIED")) {
            Refresh(null); // Refresh the table view to show the updated course
            showMsg("Course Modified successfully.");
        }

    }

    /**
     * The function of refresh button
     *
     * @param event the mouse event of clicking the refresh button
     * @author Wong Hon Yin
     */
    @FXML
    void Refresh(MouseEvent event) {
        List<Course> courses = DatabaseService.getCourseDatabase().getAll();
        courseObservableList.setAll(courses);
        courseTable.setItems(courseObservableList);

        Filter(null);
    }

    /**
     * The function for the reset button
     *
     * @param event the mouse event of clicking the Reset button
     * @author Wong Hon Yin
     */
    @FXML
    void ResetFilter(MouseEvent event) {
        filterCourseIdIN.clear();
        filterCourseNameIN.clear();
        filterDepartmentIN.clear();
        Refresh(null);
    }

}



