package comp3111.examsystem.entity;

import static org.junit.jupiter.api.Assertions.*;

import comp3111.examsystem.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeacherTest {

    private Teacher teacher;

    /**
     * set up a teacher
     * @author Wong Hon Yin
     */
    @BeforeEach
    public void setUp() {
        // Initialize a Teacher object before each test
        teacher = new Teacher(1L, "jane_doe", "Jane Doe", "Female", "30", "Professor", "Computer Science", "securePassword");
    }

    /**
     * test default constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testDefaultConstructor() {
        Teacher defaultTeacher = new Teacher();

        assertNull(defaultTeacher.getUsername());
        assertNull(defaultTeacher.getName());
        assertNull(defaultTeacher.getGender());
        assertNull(defaultTeacher.getAge());
        assertNull(defaultTeacher.getPosition());
        assertNull(defaultTeacher.getDepartment());
        assertNull(defaultTeacher.getPassword());
    }

    /**
     * test constructor and getter
     * @author Wong Hon Yin
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("jane_doe", teacher.getUsername());
        assertEquals("Jane Doe", teacher.getName());
        assertEquals("Female", teacher.getGender());
        assertEquals("30", teacher.getAge());
        assertEquals("Professor", teacher.getPosition());
        assertEquals("Computer Science", teacher.getDepartment());
        assertEquals("securePassword", teacher.getPassword());
    }

    /**
     * test setter
     * @author Wong Hon Yin
     */
    @Test
    public void testSetters() {
        teacher.setUsername("john_doe");
        teacher.setName("John Doe");
        teacher.setGender("Male");
        teacher.setAgeUsingString("35");
        teacher.setPosition("Lecturer");
        teacher.setDepartment("Mathematics");
        teacher.setPassword("newPassword123");

        assertEquals("john_doe", teacher.getUsername());
        assertEquals("John Doe", teacher.getName());
        assertEquals("Male", teacher.getGender());
        assertEquals("35", teacher.getAge());
        assertEquals("Lecturer", teacher.getPosition());
        assertEquals("Mathematics", teacher.getDepartment());
        assertEquals("newPassword123", teacher.getPassword());
    }

    /**
     * test get age (int version)
     * @author Wong Hon Yin
     */
    @Test
    public void testGetIntegerAge() {
        assertEquals(30, teacher.getIntegerAge());

        teacher.setAgeUsingString("invalid_age");
        assertNull(teacher.getIntegerAge());

        teacher.setAgeUsingString(null);
        assertNull(teacher.getIntegerAge());
    }
}