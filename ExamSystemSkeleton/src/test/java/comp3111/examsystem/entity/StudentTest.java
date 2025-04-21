package comp3111.examsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student student;

    /**
     * set up a student
     * @author Wong Hon Yin
     */
    @BeforeEach
    public void setUp() {
        // Initialize a Student object before each test
        student = new Student(1L, "john_doe", "John Doe", "20", "Male", "Computer Science", "password123");
    }

    /**
     * test constructor and getter
     * @author Wong Hon Yin
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("john_doe", student.getUsername());
        assertEquals("John Doe", student.getName());
        assertEquals("20", student.getAge());
        assertEquals("Male", student.getGender());
        assertEquals("Computer Science", student.getDepartment());
        assertEquals("password123", student.getPassword());
    }

    /**
     * test setter
     * @author Wong Hon Yin
     */
    @Test
    public void testSetters() {
        student.setUsername("jane_doe");
        student.setName("Jane Doe");
        student.setAge("22");
        student.setGender("Female");
        student.setDepartment("Mathematics");
        student.setPassword("newpassword456");

        assertEquals("jane_doe", student.getUsername());
        assertEquals("Jane Doe", student.getName());
        assertEquals("22", student.getAge());
        assertEquals("Female", student.getGender());
        assertEquals("Mathematics", student.getDepartment());
        assertEquals("newpassword456", student.getPassword());
    }

    /**
     * test get age (int version)
     * @author Wong Hon Yin
     */
    @Test
    public void testGetAgeAsInteger() {
        assertEquals(20, student.getAgeAsInteger());

        student.setAge("invalid_age");
        assertNull(student.getAgeAsInteger());

        student.setAge(null);
        assertNull(student.getAgeAsInteger());
    }

    /**
     * test set age (int version)
     * @author Wong Hon Yin
     */
    @Test
    public void testSetAgeUsingInteger() {
        student.setAgeUsingInteger(25);
        assertEquals("25", student.getAge());

        student.setAgeUsingInteger(null);
        assertNull(student.getAge());
    }

    /**
     * test default constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testDefaultConstructor() {
        Student defaultStudent = new Student();

        assertNull(defaultStudent.getUsername());
        assertNull(defaultStudent.getName());
        assertNull(defaultStudent.getAge());
        assertNull(defaultStudent.getGender());
        assertNull(defaultStudent.getDepartment());
        assertNull(defaultStudent.getPassword());
    }



}