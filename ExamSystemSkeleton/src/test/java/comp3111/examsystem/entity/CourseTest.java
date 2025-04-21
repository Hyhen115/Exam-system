package comp3111.examsystem.entity;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {

    private Course course;

    /**
     * Set up test
     * @author Wong Hon Yin
     */
    @BeforeEach
    public void setUp() {
        course = new Course(1730804311725L, "CS201", "Data Science", "Computer Science");
    }

    /**
     * Test get course id
     * @author Wong Hon Yin
     */
    @Test
    public void testGetCourseId() {
        assertEquals("CS201", course.getCourseId());
    }

    /**
     * test get course name
     * @author Wong Hon Yin
     */
    @Test
    public void testGetCourseName() {
        assertEquals("Data Science", course.getCourseName());
    }

    /**
     * test get dep
     * @author Wong Hon Yin
     */
    @Test
    public void testGetDepartment() {
        assertEquals("Computer Science", course.getDepartment());
    }

    /**
     * test set course id
     * @author Wong Hon Yin
     */
    @Test
    public void testSetCourseId() {
        course.setCourseId("CS202");
        assertEquals("CS202", course.getCourseId());
    }

    /**
     * test course name
     * @author Wong Hon Yin
     */
    @Test
    public void testSetCourseName() {
        course.setCourseName("Machine Learning");
        assertEquals("Machine Learning", course.getCourseName());
    }

    /**
     * test set dep
     * @author Wong Hon Yin
     */
    @Test
    public void testSetDepartment() {
        course.setDepartment("Information Technology");
        assertEquals("Information Technology", course.getDepartment());
    }
}