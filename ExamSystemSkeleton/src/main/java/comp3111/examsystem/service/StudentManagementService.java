package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.CourseRegRecord;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Student;

import java.util.List;

import static comp3111.examsystem.MsgSender.showMsg;

public class StudentManagementService {
    /**
     * The service for deleting students
     * @param selectedStudent student selected in the table
     * @return true / false that student is deleted
     */
    public static boolean deleteStudentService(Student selectedStudent) {
        if (selectedStudent != null) {

            // get student key
            String selectedStudentKey = selectedStudent.getId().toString();

            // Fetch all grades associated with the selected student
            List<Grade> studentGrades = DatabaseService.getGradeDatabase().getAll();

            // delete all grades of that student
            for(Grade grade : studentGrades){
                if(selectedStudentKey.equals(grade.getStudentId())){
                    DatabaseService.getGradeDatabase().delByKey(grade.getId().toString());
                }
            }

            // delete all related course reg records related to the student
            List<CourseRegRecord> regCourseList = DatabaseService.getCourseRegRecordDatabase().getAll();

            // delete all course reg records related to the student
            for(CourseRegRecord courseRegRecord : regCourseList){
                if(selectedStudentKey.equals(courseRegRecord.getStudentKey())) {
                    DatabaseService.getCourseRegRecordDatabase().delByKey(courseRegRecord.getId().toString());
                }
            }

            // delete student
            DatabaseService.getStudentDatabase().delByKey(selectedStudent.getId().toString()); // Assuming getId() is available

            return true;
        } else {
            return false;
        }
    }

    /**
     * The function to add student for controller
     * @param username student username
     * @param name student name
     * @param age student age
     * @param gender student gender
     * @param department student department
     * @param password student password
     * @return status of student added or not
     * @author Wong Hon Yin
     */
    public static String addStudentService(String username, String name, String age, String gender, String department, String password) {

        Integer ageInt;

        // Validate input
        if (username.isEmpty() || name.isEmpty() || age.isEmpty() || gender.isEmpty() || department.isEmpty() || password.isEmpty()) {
            return "EMPTY_FIELDS";
        }

        // check username , >4 words
        if(username.length() <= 4)
        {
            return "USERNAME_TOO_SHORT";
        }

        // check for exist username
        List<Student> existingStudent = DatabaseService.getStudentDatabase().getAll();
        for(Student student : existingStudent){
            if(student.getUsername().equals(username)){
                return "STUDENT_ALREADY_EXISTS";
            }
        }

        // validate age
        try {
            ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return "INVALID_AGE";
        }

        // invalid age
        if(ageInt < 0 || ageInt > 100){
            return "INVALID_AGE_RANGE";
        }

        // check password, >= 8 words
        if(password.length() < 8) {
            return "PASSWORD_TOO_SHORT";
        }

        // Create new student and add to the database
        Student newStudent = new Student();
        newStudent.setUsername(username);
        newStudent.setName(name);
        newStudent.setAge(age);
        newStudent.setGender(gender);
        newStudent.setDepartment(department);
        newStudent.setPassword(password);

        DatabaseService.getStudentDatabase().add(newStudent);

        return "STUDENT_ADDED";
    }

    /**
     * The function for update students
     * @param selectedStudent student selected in table
     * @param username updated student username
     * @param name updated student name
     * @param age updated student age
     * @param gender updated student gender
     * @param department updated student department
     * @param password updated student password
     * @return status of update student
     * @author Wong Hon Yin
     */
    public static String updateStudentService(Student selectedStudent, String username, String name, String age, String gender, String department, String password) {
        // no student selected
        if (selectedStudent == null) {
            return "NO_SELECTED_STUDENT";
        }

        //empty fields
        if (username.isEmpty() || name.isEmpty() || age.isEmpty() || gender.isEmpty() || department.isEmpty() || password.isEmpty()) {
            return "EMPTY_FIELDS";
        }

        // Validate username length > 4 character
        if (username.length() <= 4) {
            return "USERNAME_TOO_SHORT";
        }

        // Check for existing usernames
        List<Student> existingStudents = DatabaseService.getStudentDatabase().getAll();
        for (Student student : existingStudents) {
            if (!student.getUsername().equals(selectedStudent.getUsername()) && student.getUsername().equals(username)) {
                return "STUDENT_ALREADY_EXISTS";
            }
        }

        Integer ageInt;

        // Validate age
        try {
            ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return "INVALID_AGE";
        }

        if (ageInt < 0 || ageInt > 100) {
            return "INVALID_AGE_RANGE";
        }

        // Validate password length
        if (password.length() < 8) {
            return "PASSWORD_TOO_SHORT";
        }

        //update selected student's details
        selectedStudent.setUsername(username);
        selectedStudent.setName(name);
        selectedStudent.setAge(age);
        selectedStudent.setGender(gender);
        selectedStudent.setDepartment(department);
        selectedStudent.setPassword(password);

        // update database
        DatabaseService.getStudentDatabase().update(selectedStudent);
        return "STUDENT_UPDATED";
    }
}
