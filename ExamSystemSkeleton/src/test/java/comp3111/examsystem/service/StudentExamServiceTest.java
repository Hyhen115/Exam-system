package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.controller.StudentLoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentExamServiceTest {

    private Database<Grade> gradeDatabase;
    private Exam exam;
    private LocalTime timeRemaining;

    @BeforeEach
    public void setUp() {
        // Initialize the in-memory database
        gradeDatabase = new Database<>(Grade.class);
        DatabaseService.setGradeDatabase(gradeDatabase);

        // Initialize the exam and timeRemaining
        exam = new Exam();
        exam.setId(1L);
        exam.setExamTime("120"); // 120 minutes

        timeRemaining = LocalTime.of(1, 30); // 1 hour 30 minutes remaining

        // Mock the session manager to return a specific student ID
        StudentLoginController.SessionManager.setLoggedInStudentId(1L);
    }

    @Test
    public void testAddGrade() {
        List<Grade> gradesBeforeAdd = gradeDatabase.getAll();
        // Create an instance of the service
        StudentExamService studentExamService = new StudentExamService();

        // Call the addGrade method
        studentExamService.addGrade(exam, timeRemaining, 85, 100);

        // Verify that the grade was added to the database
        List<Grade> grades = gradeDatabase.getAll();
        assertEquals(gradesBeforeAdd.size() + 1, grades.size());

        Grade addedGrade = grades.get(grades.size() - 1);
        assertEquals("1", addedGrade.getExamId());
        assertEquals("1", addedGrade.getStudentId());
        assertEquals("85", addedGrade.getScore());
        assertEquals("100", addedGrade.getFullScore());
        assertEquals("30", addedGrade.getTimeSpend()); // 120 minutes - 90 minutes = 30 minutes


        gradeDatabase.delBy2Field("studentId", addedGrade.getStudentId(),
                "examId", addedGrade.getExamId());

    }
}