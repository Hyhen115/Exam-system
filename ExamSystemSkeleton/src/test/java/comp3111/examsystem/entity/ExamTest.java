package comp3111.examsystem.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExamTest {

    private Exam exam;

    /**
     * set up exam
     * @author Wong Hon Yin
     */
    @BeforeEach
    public void setUp() {
        // Initialize an Exam object before each test
        exam = new Exam(1L, "Final Exam", "1730804311725", "60", "true");
    }

    /**
     * test default constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testDefaultConstructor() {
        Exam defaultExam = new Exam();

        assertNull(defaultExam.getExamName());
        assertNull(defaultExam.getCourseKey());
        assertNull(defaultExam.getExamTime());
        assertNull(defaultExam.getPublish());
    }

    /**
     * test getter
     * @author Wong Hon Yin
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("Final Exam", exam.getExamName());
        assertEquals("1730804311725", exam.getCourseKey());
        assertEquals("60", exam.getExamTime());
        assertEquals("true", exam.getPublish());
    }

    /**
     * test setter
     * @author Wong Hon Yin
     */
    @Test
    public void testSetters() {
        exam.setExamName("Midterm Exam");
        exam.setCourseId("1730804311726");
        exam.setExamTime("45");
        exam.setPublish("false");

        assertEquals("Midterm Exam", exam.getExamName());
        assertEquals("1730804311726", exam.getCourseKey());
        assertEquals("45", exam.getExamTime());
        assertEquals("false", exam.getPublish());
    }
}