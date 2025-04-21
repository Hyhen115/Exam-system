package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherRegisterServiceTest {

    private TeacherRegisterService teacherRegisterService;
    private static List<Teacher> mockTeacherDatabase;

    @BeforeEach
    void setUp() {
        teacherRegisterService = new TeacherRegisterService();

        // Mocking the teacher database
        mockTeacherDatabase = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setUsername("teacher1");
        Teacher teacher2 = new Teacher();
        teacher2.setUsername("teacher2");
        mockTeacherDatabase.add(teacher1);
        mockTeacherDatabase.add(teacher2);

        // Override the DatabaseService to return our mock data
        Database<Teacher> mockDatabase = new Database<>(Teacher.class) {
            @Override
            public List<Teacher> getAll() {
                return mockTeacherDatabase;
            }

            @Override
            public void add(Teacher entity) {
                mockTeacherDatabase.add(entity);
            }
        };

        // Replace the singleton instance for testing
        DatabaseService.setTeacherDatabase(mockDatabase);
    }

    @Test
    void testCheckUsernameExists() {
        assertFalse(teacherRegisterService.checkUsername("teacher1"),
                "Username 'teacher1' should already exist.");
        assertFalse(teacherRegisterService.checkUsername("teacher2"),
                "Username 'teacher2' should already exist.");
    }

    @Test
    void testCheckUsernameDoesNotExist() {
        assertTrue(teacherRegisterService.checkUsername("teacher3"),
                "Username 'teacher3' should not exist.");
    }

    @Test
    void testAddTeacher() {
        Teacher newTeacher = new Teacher();
        newTeacher.setUsername("teacher3");
        teacherRegisterService.addTeacher(newTeacher);

        assertFalse(teacherRegisterService.checkUsername("teacher3"),
                "Username 'teacher3' should now exist after adding.");
    }
}
