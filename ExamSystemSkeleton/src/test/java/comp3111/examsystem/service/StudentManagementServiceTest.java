package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagementServiceTest {

    @Test
    void TestAddStudentFail() {
        // Test cases for invalid inputs
        String result;

        // 1. Test empty username
        result = StudentManagementService.addStudentService("", "John Doe", "20", "Male", "Computer Science", "password123");
        assertEquals("EMPTY_FIELDS", result);

        // 2. Test empty name
        result = StudentManagementService.addStudentService("student123", "", "20", "Male", "Computer Science", "password123");
        assertEquals("EMPTY_FIELDS", result);

        // 3. Test empty age
        result = StudentManagementService.addStudentService("student123", "John Doe", "", "Male", "Computer Science", "password123");
        assertEquals("EMPTY_FIELDS", result);

        // 4. Test empty gender
        result = StudentManagementService.addStudentService("student123", "John Doe", "20", "", "Computer Science", "password123");
        assertEquals("EMPTY_FIELDS", result);

        // 5. Test empty department
        result = StudentManagementService.addStudentService("student123", "John Doe", "20", "Male", "", "password123");
        assertEquals("EMPTY_FIELDS", result);

        // 6. Test empty password
        result = StudentManagementService.addStudentService("student123", "John Doe", "20", "Male", "Computer Science", "");
        assertEquals("EMPTY_FIELDS", result);

        // 7. Test username too short
        result = StudentManagementService.addStudentService("stu", "John Doe", "20", "Male", "Computer Science", "password123");
        assertEquals("USERNAME_TOO_SHORT", result);

        // 8. Test existing username
        Student existingStudent = new Student();
        existingStudent.setUsername("student123");
        DatabaseService.getStudentDatabase().add(existingStudent); // Add to mock database

        result = StudentManagementService.addStudentService("student123", "Jane Doe", "20", "Female", "Computer Science", "password123");
        assertEquals("STUDENT_ALREADY_EXISTS", result);
        // Clean up
        DatabaseService.getStudentDatabase().delByKey(existingStudent.getId().toString());

        // 9. Test invalid age input
        result = StudentManagementService.addStudentService("student456", "John Doe", "invalid_age", "Male", "Computer Science", "password123");
        assertEquals("INVALID_AGE", result);

        // 10. Test age out of range
        result = StudentManagementService.addStudentService("student456", "John Doe", "-5", "Male", "Computer Science", "password123");
        assertEquals("INVALID_AGE_RANGE", result);

        result = StudentManagementService.addStudentService("student456", "John Doe", "150", "Male", "Computer Science", "password123");
        assertEquals("INVALID_AGE_RANGE", result);

        // 11. Test password too short
        result = StudentManagementService.addStudentService("student456", "John Doe", "20", "Male", "Computer Science", "short");
        assertEquals("PASSWORD_TOO_SHORT", result);
    }

    @Test
    void TestAddStudentSuccess() {
        // Test cases for valid inputs
        String result;

        // 1. Add a student with valid inputs
        String username = "student123";
        String name = "John Doe";
        String age = "20";
        String gender = "Male";
        String department = "Computer Science";
        String password = "StrongPassword123";

        result = StudentManagementService.addStudentService(username, name, age, gender, department, password);
        assertEquals("STUDENT_ADDED", result);

        // 2. Verify that the student was added to the database
        Student addedStudent = DatabaseService.getStudentDatabase().queryByField("username", username).getFirst();

        // Check that the student attributes match the expected values
        assertEquals(name, addedStudent.getName());
        assertEquals(password, addedStudent.getPassword());
        assertEquals(department, addedStudent.getDepartment());
        assertEquals(username, addedStudent.getUsername());
        assertEquals(gender, addedStudent.getGender());
        assertEquals("20", addedStudent.getAge()); // Assuming getAge() returns an int

        // Clean up the added student
        DatabaseService.getStudentDatabase().delByKey(addedStudent.getId().toString());
    }

    @Test
    void TestUpdateStudentFail() {
        // Create and add a student for testing updates
        Student existingStudent = new Student();
        existingStudent.setUsername("student123");
        existingStudent.setPassword("StrongPassword123");
        existingStudent.setName("John Doe");
        existingStudent.setDepartment("Computer Science");
        existingStudent.setGender("Male");
        existingStudent.setAge("20");

        DatabaseService.getStudentDatabase().add(existingStudent); // Add to mock database


        // 1. Test updating a null student
        String result = StudentManagementService.updateStudentService(null, "NewUsername", "New Name", "25", "Female", "NewDepartment", "NewPassword");
        assertEquals("NO_SELECTED_STUDENT", result);

        // 2. Test empty fields
        result = StudentManagementService.updateStudentService(existingStudent, "", "New Name", "25", "Female", "NewDepartment", "NewPassword");
        assertEquals("EMPTY_FIELDS", result);

        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "", "25", "Female", "NewDepartment", "NewPassword");
        assertEquals("EMPTY_FIELDS", result);

        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "", "Female", "NewDepartment", "NewPassword");
        assertEquals("EMPTY_FIELDS", result);

        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "25", "", "NewDepartment", "NewPassword");
        assertEquals("EMPTY_FIELDS", result);

        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "25", "Female", "", "NewPassword");
        assertEquals("EMPTY_FIELDS", result);

        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "25", "Female", "NewDepartment", "");
        assertEquals("EMPTY_FIELDS", result);

        // 3. Test username too short
        result = StudentManagementService.updateStudentService(existingStudent, "stu", "New Name", "25", "Female", "NewDepartment", "NewPassword");
        assertEquals("USERNAME_TOO_SHORT", result);

        // 4. Test invalid age input
        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "invalid_age", "Female", "NewDepartment", "NewPassword");
        assertEquals("INVALID_AGE", result);

        // 5. Test age out of range
        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "-5", "Female", "NewDepartment", "NewPassword");
        assertEquals("INVALID_AGE_RANGE", result);

        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "150", "Female", "NewDepartment", "NewPassword");
        assertEquals("INVALID_AGE_RANGE", result);

        // 6. Test password too short
        result = StudentManagementService.updateStudentService(existingStudent, "NewUsername", "New Name", "25", "Female", "NewDepartment", "short");
        assertEquals("PASSWORD_TOO_SHORT", result);

        // 7. Test existing username (if trying to update to an already existing username)
        Student anotherStudent = new Student();
        anotherStudent.setUsername("student456");
        DatabaseService.getStudentDatabase().add(anotherStudent); // Add another student to mock database

        result = StudentManagementService.updateStudentService(existingStudent, "student456", "New Name", "25", "Female", "NewDepartment", "NewPassword");
        assertEquals("STUDENT_ALREADY_EXISTS", result);

        // Clean up
        DatabaseService.getStudentDatabase().delByKey(existingStudent.getId().toString());
        DatabaseService.getStudentDatabase().delByKey(anotherStudent.getId().toString());
    }

}