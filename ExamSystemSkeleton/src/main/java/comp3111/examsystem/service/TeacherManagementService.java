package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Teacher;

import java.util.List;

import static comp3111.examsystem.MsgSender.showMsg;

public class TeacherManagementService {

    /**
     * Function to delete teachers
     * @param teacher selected teacher to delete
     * @return true / false is the teacher deleted successful
     */
    public static boolean deleteTeacherService(Teacher teacher) {
        if (teacher != null) {
            // teacher not null delete teacher and return true
            DatabaseService.getTeacherDatabase().delByKey(teacher.getId().toString());
            return true;
        }

        // teacher is null
        return false;
    }


    /**
     * Add teacher service
     * @param name teacher name
     * @param password teacher password
     * @param department teacher department
     * @param username teacher username
     * @param gender teacher gender
     * @param position teacher position
     * @param age teacher age
     * @return String for status of add teacher
     * @author Wong Hon Yin
     */
    public static String addTeacherService(String name, String password, String department, String username, String gender, String position, String age) {
        Integer ageInt;

        // validate input
        if (name.isEmpty() || password.isEmpty() || department.isEmpty() || username.isEmpty() || gender.isEmpty() || position.isEmpty() || age.isEmpty()) {
            return "EMPTY_FIELDS";
        }

        // check username , >4 words
        if(username.length() <= 4)
        {
            return "UNAME_LENGTH";
        }

        // check exist teachers
        // fetch data
        List<Teacher> existingTeacher = DatabaseService.getTeacherDatabase().getAll();

        // check for existing teachers
        for (Teacher teacher : existingTeacher) {
            // if you have existed username -> return
            if (teacher.getUsername().equals(username)) {
                return "TEACHER_ALREADY_EXISTS";
            }
        }

        // validate age
        try {
            ageInt = Integer.parseInt(age); // Convert String to Integer
        } catch (NumberFormatException e) {
            return "STRING_AGE";
        }

        // Check age range
        if (ageInt < 0 || ageInt > 100) {
            return "AGE_RANGE";
        }

        // check password, >= 8 words
        if(password.length() < 8) {
            return "PASSWORD_LENGTH";
        }

        // add teacher and fetch the data to the db
        Teacher teacher = new Teacher();
        teacher.setAgeUsingString(age);
        teacher.setGender(gender);
        teacher.setPosition(position);
        teacher.setName(name);
        teacher.setPassword(password);
        teacher.setDepartment(department);
        teacher.setUsername(username);

        DatabaseService.getTeacherDatabase().add(teacher);
        return "TEACHER_ADDED";

    }

    /**
     * Update teacher service
     * @param selectedTeacher selected teacher in table
     * @param name updated teacher name
     * @param password updated teacher password
     * @param department updated teacher department
     * @param username updated teacher username
     * @param gender updated teacher gender
     * @param position updated teacher position
     * @param age updated teacher age
     * @return status of update
     * @author Wong Hon Yin
     */
    public static String updateTeacherService(Teacher selectedTeacher, String name, String password, String department, String username, String gender, String position, String age) {
        // not selected teacher
        if (selectedTeacher == null) {
            return "NOT_SELECTED";
        }

        Integer ageInt;

        // empty fields
        if (name.isEmpty() || password.isEmpty() || department.isEmpty() || username.isEmpty() || gender.isEmpty() || position.isEmpty() || age.isEmpty()) {
            return "EMPTY_FIELDS";
        }

        // check username , >4 words
        if (username.length() <= 4) {
            return "UNAME_LENGTH";
        }

        // check for exist username other than it self
        List<Teacher> existingTeacher = DatabaseService.getTeacherDatabase().getAll();
        for (Teacher teacher : existingTeacher) {
            if (!teacher.getUsername().equals(selectedTeacher.getUsername()) && teacher.getUsername().equals(username)) {
                return "TEACHER_ALREADY_EXISTS";
            }
        }

        // validate age
        // check for valid age
        try {
            ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return "INVALID_AGE";
        }

        // check age range
        if (ageInt < 0 || ageInt > 100) {
            return "AGE_RANGE";
        }

        // check password, >= 8 words
        if (password.length() < 8) {
            return "PASSWORD_LENGTH";
        }

        // update the selected teacher's details
        selectedTeacher.setName(name);
        selectedTeacher.setPassword(password);
        selectedTeacher.setDepartment(department);
        selectedTeacher.setUsername(username);
        selectedTeacher.setGender(gender);
        selectedTeacher.setPosition(position);
        selectedTeacher.setAgeUsingString(age);

        // Update the database
        DatabaseService.getTeacherDatabase().update(selectedTeacher);
        return "TEACHER_UPDATED";

    }
}
