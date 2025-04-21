package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherManagementServiceTest {

    @Test
    void testDeleteTeacherFalse() {
        Teacher teacher = null;
        Boolean deleteStatus = TeacherManagementService.deleteTeacherService(teacher);
        // no teacher selected
        assertFalse(deleteStatus);
    }

    @Test
    void testDeleteTeacherTrue() {
        // database dont have teacher but still delete
        Teacher teacher = new Teacher();
        Boolean deleteStatus = TeacherManagementService.deleteTeacherService(teacher);
        assertTrue(deleteStatus);

        // database have teacher delete


        // add teacher
        DatabaseService.getTeacherDatabase().add(teacher);

        //db size
        int dbSizeAdd = DatabaseService.getTeacherDatabase().getAll().size();

        deleteStatus = TeacherManagementService.deleteTeacherService(teacher);

        //db size
        int dbSizeDelete = DatabaseService.getTeacherDatabase().getAll().size();

        // deleted teacher
        assertTrue(deleteStatus);

        // check size of db
        assertEquals(dbSizeAdd - 1, dbSizeDelete);
    }

    @Test void testAddTeacherFalse() {
        // Test cases for invalid inputs
        String result;

        // 1. Test empty name
        result = TeacherManagementService.addTeacherService("", "password", "department", "username", "Male", "position", "30");
        assertEquals("EMPTY_FIELDS", result);

        // 2. Test empty password
        result = TeacherManagementService.addTeacherService("name", "", "department", "username", "Male", "position", "30");
        assertEquals("EMPTY_FIELDS", result);

        // 3. Test empty department
        result = TeacherManagementService.addTeacherService("name", "password", "", "username", "Male", "position", "30");
        assertEquals("EMPTY_FIELDS", result);

        // 4. Test empty username
        result = TeacherManagementService.addTeacherService("name", "password", "department", "", "Male", "position", "30");
        assertEquals("EMPTY_FIELDS", result);

        // 5. Test empty gender
        result = TeacherManagementService.addTeacherService("name", "password", "department", "username", "", "position", "30");
        assertEquals("EMPTY_FIELDS", result);

        // 6. Test empty position
        result = TeacherManagementService.addTeacherService("name", "password", "department", "username", "Male", "", "30");
        assertEquals("EMPTY_FIELDS", result);

        // 7. Test empty age
        result = TeacherManagementService.addTeacherService("name", "password", "department", "username", "Male", "position", "");
        assertEquals("EMPTY_FIELDS", result);

        // 8. Test username too short
        result = TeacherManagementService.addTeacherService("name", "password", "department", "user", "Male", "position", "30");
        assertEquals("UNAME_LENGTH", result);

        // 9. Test existing username
        Teacher existingTeacher = new Teacher();
        existingTeacher.setUsername("johndoe");
        DatabaseService.getTeacherDatabase().add(existingTeacher); // Add to mock database

        result = TeacherManagementService.addTeacherService("Jane Doe", "StrongPassword123", "Math", "johndoe", "Female", "Professor", "30");
        assertEquals("TEACHER_ALREADY_EXISTS", result);
        // Clean up
        DatabaseService.getTeacherDatabase().delByKey(existingTeacher.getId().toString());

        // 10. Test invalid age input
        result = TeacherManagementService.addTeacherService("John Doe", "StrongPassword123", "Math", "johndoe123", "Male", "Professor", "invalid_age");
        assertEquals("STRING_AGE", result);

        // 11. Test age out of range
        result = TeacherManagementService.addTeacherService("John Doe", "StrongPassword123", "Math", "johndoe123", "Male", "Professor", "-5");
        assertEquals("AGE_RANGE", result);

        result = TeacherManagementService.addTeacherService("John Doe", "StrongPassword123", "Math", "johndoe123", "Male", "Professor", "150");
        assertEquals("AGE_RANGE", result);

        // 12. Test password too short
        result = TeacherManagementService.addTeacherService("John Doe", "short", "Math", "johndoe123", "Male", "Professor", "30");
        assertEquals("PASSWORD_LENGTH", result);
    }

    @Test
    void testAddTeacherTrue() {

        // Test cases for valid inputs
        String result;

        Teacher teacher = new Teacher();
        teacher.setName("John Doe");
        teacher.setUsername("johndoe123");
        teacher.setPassword("strongPassword123");
        teacher.setDepartment("Math");
        teacher.setPosition("Professor");
        teacher.setAgeUsingString("30");
        teacher.setGender("Male");

        // 1. Add a teacher with valid inputs
        result = TeacherManagementService.addTeacherService(teacher.getName(), teacher.getPassword(), teacher.getDepartment(), teacher.getUsername(), teacher.getGender(), teacher.getPosition(), teacher.getAge());
        assertEquals("TEACHER_ADDED", result);

        // 2. Verify that the teacher was added to the database
        Teacher addedTeacher = DatabaseService.getTeacherDatabase().queryByField("username","johndoe123").getFirst();

        assertEquals(teacher.getName(), addedTeacher.getName());
        assertEquals(teacher.getPassword(), addedTeacher.getPassword());
        assertEquals(teacher.getDepartment(), addedTeacher.getDepartment());
        assertEquals(teacher.getUsername(), addedTeacher.getUsername());
        assertEquals(teacher.getGender(), addedTeacher.getGender());
        assertEquals(teacher.getPosition(), addedTeacher.getPosition());
        assertEquals(teacher.getAge(), addedTeacher.getAge());

        // clear up teachers
        DatabaseService.getTeacherDatabase().delByKey(addedTeacher.getId().toString());

    }

    @Test
    void testUpdateTeacherFail() {

        // Create and add a teacher for testing updates
        Teacher existingTeacher = new Teacher();
        existingTeacher.setUsername("johndoe123");
        existingTeacher.setPassword("StrongPassword123");
        existingTeacher.setName("John Doe");
        existingTeacher.setDepartment("Math");
        existingTeacher.setGender("Male");
        existingTeacher.setPosition("Professor");
        existingTeacher.setAgeUsingString("30");

        DatabaseService.getTeacherDatabase().add(existingTeacher);

        // 1. Test updating a null teacher
        String result = TeacherManagementService.updateTeacherService(null, "New Name", "NewPassword", "NewDepartment", "NewUsername", "Female", "NewPosition", "25");
        assertEquals("NOT_SELECTED", result);

        // 2. Test empty fields
        result = TeacherManagementService.updateTeacherService(existingTeacher, "", "NewPassword", "NewDepartment", "NewUsername", "Female", "NewPosition", "25");
        assertEquals("EMPTY_FIELDS", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "", "NewDepartment", "NewUsername", "Female", "NewPosition", "25");
        assertEquals("EMPTY_FIELDS", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "", "NewUsername", "Female", "NewPosition", "25");
        assertEquals("EMPTY_FIELDS", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "", "Female", "NewPosition", "25");
        assertEquals("EMPTY_FIELDS", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "NewUsername", "", "NewPosition", "25");
        assertEquals("EMPTY_FIELDS", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "NewUsername", "Female", "", "25");
        assertEquals("EMPTY_FIELDS", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "NewUsername", "Female", "NewPosition", "");
        assertEquals("EMPTY_FIELDS", result);

        // 3. Test username too short
        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "user", "Female", "NewPosition", "25");
        assertEquals("UNAME_LENGTH", result);

        // 4. Test invalid age input
        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "NewUsername", "Female", "NewPosition", "invalid_age");
        assertEquals("INVALID_AGE", result);

        // 5. Test age out of range
        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "NewUsername", "Female", "NewPosition", "-5");
        assertEquals("AGE_RANGE", result);

        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "NewPassword", "NewDepartment", "NewUsername", "Female", "NewPosition", "150");
        assertEquals("AGE_RANGE", result);

        // 6. Test password too short
        result = TeacherManagementService.updateTeacherService(existingTeacher, "New Name", "short", "NewDepartment", "NewUsername", "Female", "NewPosition", "25");
        assertEquals("PASSWORD_LENGTH", result);

        //clear exist teacher
        DatabaseService.getTeacherDatabase().delByKey(existingTeacher.getId().toString());
    }

    @Test
    void testUpdateTeacherTrue() {
        // Test cases for valid inputs
        String result;

        // Create and add a teacher for testing updates
        Teacher existingTeacher = new Teacher();
        existingTeacher.setUsername("johndoe123");
        existingTeacher.setPassword("StrongPassword123");
        existingTeacher.setName("John Doe");
        existingTeacher.setDepartment("Math");
        existingTeacher.setGender("Male");
        existingTeacher.setPosition("Professor");
        existingTeacher.setAgeUsingString("30");

        DatabaseService.getTeacherDatabase().add(existingTeacher);

        // 1. Update the existing teacher with valid inputs
        String newName = "John Smith";
        String newPassword = "NewStrongPassword123";
        String newDepartment = "Science";
        String newUsername = "johnsmith123";
        String newGender = "Male";
        String newPosition = "Lecturer";
        String newAge = "35";

        result = TeacherManagementService.updateTeacherService(existingTeacher, newName, newPassword, newDepartment, newUsername, newGender, newPosition, newAge);
        assertEquals("TEACHER_UPDATED", result);

        // 2. Verify that the teacher was updated in the database
        Teacher updatedTeacher = DatabaseService.getTeacherDatabase().queryByField("username", newUsername).getFirst();

        assertEquals(newName, updatedTeacher.getName());
        assertEquals(newPassword, updatedTeacher.getPassword());
        assertEquals(newDepartment, updatedTeacher.getDepartment());
        assertEquals(newUsername, updatedTeacher.getUsername());
        assertEquals(newGender, updatedTeacher.getGender());
        assertEquals(newPosition, updatedTeacher.getPosition());
        assertEquals("35", updatedTeacher.getAge()); // Assuming getAge() returns an int

        // Clean up the updated teacher
        DatabaseService.getTeacherDatabase().delByKey(updatedTeacher.getId().toString());


    }


}