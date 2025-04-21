/**
 * Service class for handling backend operations related to the teacher registration.
 * Provides methods to add teacher accounts to the database.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */

package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Teacher;

import java.util.List;

public class TeacherRegisterService {

    /**
     * checks whether the username already exists in the database
     * @param username the username to check
     * @return true if it doesn't exist, false if the same username exists
     *
     * @author Seokhyeon Hong
     */
    public boolean checkUsername(String username) {
        List<Teacher> existingTeacher = DatabaseService.getTeacherDatabase().getAll();

        // check for existing teachers
        for (Teacher teacher : existingTeacher) {
            if (teacher.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * adds teacher to the database
     * @param teacher is the new account to be added to the database
     *
     * @author Seokhyeon Hong
     */
    public void addTeacher(Teacher teacher){
        DatabaseService.getTeacherDatabase().add(teacher);

    }
}
