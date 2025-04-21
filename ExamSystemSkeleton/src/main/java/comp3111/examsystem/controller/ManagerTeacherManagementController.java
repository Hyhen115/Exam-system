package comp3111.examsystem.controller;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ChoiceBox;
import comp3111.examsystem.service.TeacherManagementService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.MsgSender.showMsg;

public class ManagerTeacherManagementController implements Initializable {


    @FXML
    private ChoiceBox<String> PositionChoice;

    @FXML
    private ChoiceBox<String> genderChoice;

    @FXML
    private TextField ageIN;

    @FXML
    private TableColumn<Teacher, String> colAge;

    @FXML
    private TableColumn<Teacher, String> colDepartment;

    @FXML
    private TableColumn<Teacher, String> colGender;

    @FXML
    private TableColumn<Teacher, String> colName;

    @FXML
    private TableColumn<Teacher, String> colPassword;

    @FXML
    private TableColumn<Teacher, String> colPosition;

    @FXML
    private TableColumn<Teacher, String> colUsername;

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
    private TableView<Teacher> teacherTable;

    @FXML
    private TextField usernameIN;

    private String selectedGender = "";

    private String selectedPosition = "";

    // initialize list for the list
    ObservableList<Teacher> teacherObservableList = FXCollections.observableArrayList();

    // constructor for tests
    public ManagerTeacherManagementController() {
        this.usernameIN = new TextField();
        this.nameIN = new TextField();
        this.passwordIN = new TextField();
        this.departmentIN = new TextField();
        this.selectedPosition = "";
        this.selectedGender = "";
    }

    // setter for tests
    public ManagerTeacherManagementController(Teacher teacher) {
        this.usernameIN.setText(teacher.getUsername());
        this.nameIN.setText(teacher.getName());
        this.passwordIN.setText(teacher.getPassword());
        this.departmentIN.setText(teacher.getDepartment());
        this.selectedPosition = teacher.getPosition();
        this.selectedGender = teacher.getGender();
    }

    /**
     * the Function for the setup of the page initializing the needed data for the page
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
        List<Teacher> teacher = DatabaseService.getTeacherDatabase().getAll();

        //set the fetched data to list
        teacherObservableList = FXCollections.observableArrayList(teacher);

        //setup columns
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));


        // set the courseObservableList to the table view
        teacherTable.setItems(teacherObservableList);

        // Initialize the ChoiceBoxes
        genderChoice.setItems(FXCollections.observableArrayList("Male", "Female", "Non-Binary"));
        PositionChoice.setItems(FXCollections.observableArrayList("Teaching Assistant", "Professor", "Assistant Professor", "Lecturer"));

        // Set default selections
        genderChoice.getSelectionModel().selectFirst(); // Select first option by default
        selectedGender = genderChoice.getValue(); // Initialize selectedGender

        PositionChoice.getSelectionModel().selectFirst(); // Select first option by default
        selectedPosition = PositionChoice.getValue(); // Initialize selectedPosition

        // listener for table row selection
        teacherTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                usernameIN.setText(newValue.getUsername());
                nameIN.setText(newValue.getName());
                ageIN.setText(newValue.getAge());
                departmentIN.setText(newValue.getDepartment());
                passwordIN.setText(newValue.getPassword());

                selectedGender = newValue.getGender();
                selectedPosition = newValue.getPosition();

                // Set the ChoiceBox selections
                genderChoice.setValue(selectedGender);
                PositionChoice.setValue(selectedPosition);
            }
        });

        // Set up listener for gender ChoiceBox
        genderChoice.setOnAction(event -> {
            selectedGender = genderChoice.getValue(); // Update selectedGender when a new gender is selected
        });

        // Set up listener for position ChoiceBox
        PositionChoice.setOnAction(event -> {
            selectedPosition = PositionChoice.getValue(); // Update selectedPosition when a new position is selected
        });

    }

    /**
     * The function for add button
     *
     * @param event the event of mouse clicking the add button
     * @author Wong Hon Yin
     */
    @FXML
    void AddTeacher(MouseEvent event) {
        String name = nameIN.getText();
        String password = passwordIN.getText();
        String department = departmentIN.getText();
        String username = usernameIN.getText();
        String gender = selectedGender;
        String position = selectedPosition;
        String age = ageIN.getText();

        String addTeacherStatus = TeacherManagementService.addTeacherService(name, password, department, username, gender, position, age);

        // check any empty
        if (addTeacherStatus.equals("EMPTY_FIELDS")) {
            showMsg("Please fill all the fields");
            return;
        }

        // check invalid age string
        if (addTeacherStatus.equals("STRING_AGE")) {
            showMsg("Please enter a valid age");
            return;
        }

        // invalid age
        if (addTeacherStatus.equals("AGE_RANGE")) {
            showMsg("Please enter a valid age between 0 and 100");
            return;
        }

        // check username > 4 words?
        if (addTeacherStatus.equals("UNAME_LENGTH")) {
            showMsg("Please enter a valid username with at least 5 characters");
            return;
        }
        
        // check unique username
        if(addTeacherStatus.equals("TEACHER_ALREADY_EXISTS")) {
            showMsg("This username is already taken");
            return;
        }

        // check password, >8 words
        if (addTeacherStatus.equals("PASSWORD_LENGTH")) {
            showMsg("Please enter a valid password at least 8 characters");
            return;
        }

        // sucessfull add
        if(addTeacherStatus.equals("TEACHER_ADDED")){
            Refresh(null);
            showMsg("Teacher added Successfully");
            return;
        }

        // error or strange
        System.out.println(addTeacherStatus);
        System.out.println("Error in addTeacher");
    }

    /**
     * The function for delete button
     *
     * @param event the event of mouse clicking the delete button
     * @author Wong Hon Yin
     */
    @FXML
    void DeleteTeacher(MouseEvent event) {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();

        if (TeacherManagementService.deleteTeacherService(selectedTeacher)) {
            Refresh(null);
            showMsg("Teacher deleted successfully");
        } else {
            showMsg("Error No teacher selected for deletion.");
        }
    }

    /**
     * The function for filter button
     *
     * @param event the event of mouse clicking the filter button
     * @author Wong Hon Yin
     */
    @FXML
    void Filter(MouseEvent event) {
        // Implement filtering logic based on filter fields
        String filterName = filterNameIN.getText();
        String filterUsername = filterUsernameIN.getText();
        String filterDepartment = filterDepartmentIN.getText();

        List<Teacher> filteredTeachers = DatabaseService.getTeacherDatabase().getAll();

        //Apply filters
        if (!filterName.isEmpty()) {
            filteredTeachers.removeIf(teacher -> !teacher.getName().toLowerCase().contains(filterName.toLowerCase()));
        }
        if (!filterUsername.isEmpty()) {
            filteredTeachers.removeIf(teacher -> !teacher.getUsername().toLowerCase().contains(filterUsername.toLowerCase()));
        }
        if (!filterDepartment.isEmpty()) {
            filteredTeachers.removeIf(teacher -> !teacher.getDepartment().toLowerCase().contains(filterDepartment.toLowerCase()));
        }

        teacherObservableList.setAll(filteredTeachers);
        teacherTable.setItems(teacherObservableList);
    }

    /**
     * The function for  the refresh button
     *
     * @param event the event of mouse clicking the refresh button
     * @author Wong Hon Yin
     */
    @FXML
    void Refresh(MouseEvent event) {
        List<Teacher> teachers = DatabaseService.getTeacherDatabase().getAll();
        teacherObservableList.setAll(teachers);
        teacherTable.setItems(teacherObservableList);

        Filter(null);
    }

    /**
     * The function for the reset button
     *
     * @param event the event of the mouse clicking the reset button
     * @author Wong Hon Yin
     */
    @FXML
    void ResetFilter(MouseEvent event) {
        filterNameIN.clear();
        filterUsernameIN.clear();
        filterDepartmentIN.clear();
        Refresh(null); // Refresh the table to show all teachers
    }

    /**
     * The function for the update button
     *
     * @param event the event of the mouse clicking the update button
     * @author Wong Hon Yin
     */
    @FXML
    void Update(MouseEvent event) {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();

        // get input
        String name = nameIN.getText();
        String password = passwordIN.getText();
        String department = departmentIN.getText();
        String username = usernameIN.getText();
        String gender = selectedGender;
        String position = selectedPosition;
        String age = ageIN.getText();
        Integer ageInt = null;

        String updateTeacherStatus = TeacherManagementService.updateTeacherService(selectedTeacher, name, password, department, username, gender, position, age);

        //if did not select teacher
        if (updateTeacherStatus.equals("NOT_SELECTED")) {
            showMsg("Please select a teacher for update");
            return;
        }

        // check if empty
        if (updateTeacherStatus.equals("EMPTY_FIELDS")) {
            showMsg("Please fill all the fields");
            return;
        }

        // check username > 4
        if (updateTeacherStatus.equals("UNAME_LENGTH")) {
            showMsg("Please enter a valid username with at least 5 characters");
            return;
        }

        // check exist username
        if (updateTeacherStatus.equals("TEACHER_ALREADY_EXISTS")) {
            showMsg("This username is already taken");
            return;
        }

        // check for valid age
        if (updateTeacherStatus.equals("INVALID_AGE")) {
            showMsg("Please enter a valid age");
            return;
        }

        // check age range
        if (updateTeacherStatus.equals("AGE_RANGE")) {
            showMsg("Please enter a valid age between 0 and 100");
            return;
        }


        // check password, >8 words
        if (updateTeacherStatus.equals("PASSWORD_LENGTH")) {
            showMsg("Please enter a valid password at least 8 characters");
            return;
        }

        // updated
        if(updateTeacherStatus.equals("TEACHER_UPDATED")){
            Refresh(null);
            showMsg("Teacher updated successfully");
            return;
        }

        // if error
        System.out.println(updateTeacherStatus);
        System.out.println("Error in updateTeacher");
    }

    // Add this setter method for testing purposes
    public void setTeacherTable(TableView<Teacher> teacherTable) {
        this.teacherTable = teacherTable;
    }
}



