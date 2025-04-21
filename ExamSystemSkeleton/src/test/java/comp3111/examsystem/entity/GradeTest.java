package comp3111.examsystem.entity;

import static org.junit.jupiter.api.Assertions.*;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GradeTest {
    private Grade grade;

    /**
     * set up for grade
     */
    @BeforeEach
    public void setUp() {
        // Initialize a Grade object before each test
        grade = new Grade(1L, "1730897297061", "1730804311726", "90", "100", "65");
    }

    /**
     * test constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testConstructor() {
        assertNotNull(grade);
        assertEquals("1730897297061", grade.getStudentId());
        assertEquals("1730804311726", grade.getExamId());
        assertEquals("90", grade.getScore());
        assertEquals("100", grade.getFullScore());
        assertEquals("65", grade.getTimeSpend());
    }

    /**
     * test getter and setter
     * @author Wong Hon Yin
     */
    @Test
    public void testGettersAndSetters() {
        // Test setters and ensure they update the values correctly
        grade.setStudentId("1234567890");
        assertEquals("1234567890", grade.getStudentId());

        grade.setExamId("0987654321");
        assertEquals("0987654321", grade.getExamId());

        grade.setScore("85");
        assertEquals("85", grade.getScore());

        grade.setFullScore("95");
        assertEquals("95", grade.getFullScore());

        grade.setTimeSpent("70");
        assertEquals("70", grade.getTimeSpend());
    }

    /**
     * test constructor default
     * @author Wong Hon Yin
     */
    @Test
    public void testDefaultConstructor() {
        Grade defaultGrade = new Grade();
        assertNotNull(defaultGrade);
        assertNull(defaultGrade.getStudentId());
        assertNull(defaultGrade.getExamId());
        assertNull(defaultGrade.getScore());
        assertNull(defaultGrade.getFullScore());
        assertNull(defaultGrade.getTimeSpend());
    }

    /**
     * test set, get time spent
     * @author Wong Hon Yin
     */
    @Test
    public void testSetAndGetTimeSpent() {
        grade.setTimeSpent("80");
        assertEquals("80", grade.getTimeSpend());
    }

    /**
     * test get student name using grade
     * @author Wong Hon Yin
     */
    @Test
    public void testGetStudentName() {
        // set student
        Student student = new Student();
        student.setName("testStudentName");
        student.setUsername("testStudentUsername");
        student.setPassword("testStudentPassword");
        student.setAge("20");
        student.setDepartment("testDepartmentName");

        // add student to database
        DatabaseService.getStudentDatabase().add(student);

        // find the student
        Student targetStudent = DatabaseService.getStudentDatabase().queryByEntity(student).getFirst();

        // create grade
        Grade defualtGrade = new Grade();
        defualtGrade.setStudentId(targetStudent.getId().toString());

        assertEquals("testStudentUsername", defualtGrade.getStudentName());

        // delete targetStudent
        DatabaseService.getStudentDatabase().delByKey(targetStudent.getId().toString());

        // TODO: find a un find student (returns Null)
        // there should be no sid 0
        Grade grade1 = new Grade();
        grade1.setStudentId("0L");

        assertNull(grade1.getStudentName());

    }

    /**
     * test get exam name using grade
     * @author Wong Hon Yin
     */
    @Test
    public void testGetExamName() {
        Grade grade = new Grade();
        Exam exam = new Exam();
        exam.setExamName("TestExamName");

        // add exam to db
        DatabaseService.getExamDatabase().add(exam);

        // get exam id fetch from db
        Exam exam1 = DatabaseService.getExamDatabase().queryByEntity(exam).getFirst();
        String exam1Key = exam1.getId().toString();

        grade.setExamId(exam1Key);

        assertEquals("TestExamName", grade.getExamName());

        //delete exam
        DatabaseService.getExamDatabase().delByKey(exam1Key);

        // null
        assertNull(grade.getExamName());

    }

    /**
     * test get course num using grade
     * @author Wong Hon Yin
     */
    @Test
    public void testGetCourseNum() {
        // make course
        Course course = new Course();
        course.setCourseName("TestCourseName");
        course.setCourseId("TESTGETCOURSENAME");
        course.setDepartment("testDepartmentName");

        // add course to db
        DatabaseService.getCourseDatabase().add(course);

        //get course Key
        String courseKey = DatabaseService.getCourseDatabase().queryByEntity(course).getFirst().getId().toString();

        // make exam
        Exam exam = new Exam();
        exam.setExamName("TestExamName");
        exam.setCourseId(courseKey);

        //add exam
        DatabaseService.getExamDatabase().add(exam);

        String examKey = DatabaseService.getExamDatabase().queryByEntity(exam).getFirst().getId().toString();

        // make grade
        Grade grade = new Grade();
        grade.setExamId(examKey);

        assertEquals("TESTGETCOURSENAME", grade.getCourseNum());

        // delete exam, course
        DatabaseService.getExamDatabase().delByKey(examKey);
        DatabaseService.getCourseDatabase().delByKey(courseKey);

        assertNull(grade.getCourseNum());
    }

    /**
     * test get course key using grade
     * @author Wong Hon Yin
     */
    @Test
    public void getCourseKey() {
        // make course
        Course course = new Course();
        course.setCourseName("TestCourseName");
        course.setCourseId("TESTGETCOURSENAME");
        course.setDepartment("testDepartmentName");

        // add course to db
        DatabaseService.getCourseDatabase().add(course);

        //get course Key
        String courseKey = DatabaseService.getCourseDatabase().queryByEntity(course).getFirst().getId().toString();

        // make exam
        Exam exam = new Exam();
        exam.setExamName("TestExamName");
        exam.setCourseId(courseKey);

        //add exam
        DatabaseService.getExamDatabase().add(exam);

        String examKey = DatabaseService.getExamDatabase().queryByEntity(exam).getFirst().getId().toString();

        // make grade
        Grade grade = new Grade();
        grade.setExamId(examKey);

        assertEquals(courseKey, grade.getCourseKey());

        // delete exam, course
        DatabaseService.getExamDatabase().delByKey(examKey);
        DatabaseService.getCourseDatabase().delByKey(courseKey);

        assertNull(grade.getCourseKey());
    }
}