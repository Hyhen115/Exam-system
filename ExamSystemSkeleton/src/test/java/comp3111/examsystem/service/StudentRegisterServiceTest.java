package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentRegisterServiceTest {

    private Database<Student> studentDatabase;

    /**
     * Sets up the test environment before each test.
     * Initializes the in-memory database and sets it in the DatabaseService.
     */
    @BeforeEach
    public void setUp() {
        // Initialize the in-memory list to simulate the database
        studentDatabase = new Database<Student>(Student.class);
        DatabaseService.setStudentDatabase(studentDatabase);

    }

    /**
     * Tests the addStudent method.
     * Verifies that a new student is added to the database and checks the details of the added student.
     */
    @Test
    public void testAddStudent() {

        List<Student> studentListBeforeAdd = studentDatabase.getAll();
        // Add a new student
        StudentRegisterService.addStudent("newUser", "John Doe", "Male", "20", "CS", "password123");

        // Verify that the student was added to the database
        List<Student> studentList = studentDatabase.getAll();

        assertEquals((studentListBeforeAdd.size() + 1), studentList.size());
        Student addedStudent = studentList.get(studentList.size()-1);
        assertEquals("newUser", addedStudent.getUsername());
        assertEquals("John Doe", addedStudent.getName());
        assertEquals("Male", addedStudent.getGender());
        assertEquals("20", addedStudent.getAge());
        assertEquals("CS", addedStudent.getDepartment());
        assertEquals("password123", addedStudent.getPassword());
        studentDatabase.delByFiled("username", addedStudent.getUsername());
    }
}