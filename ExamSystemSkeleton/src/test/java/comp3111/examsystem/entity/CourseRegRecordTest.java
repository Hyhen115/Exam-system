package comp3111.examsystem.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseRegRecordTest {

    private CourseRegRecord record;

    /**
     * Set up tests
     * @author Wong Hon Yin
     */
    @BeforeEach
    public void setUp() {
        // Initialize a CourseRegRecord object before each test
        record = new CourseRegRecord(1L, "123123123", "456456456");
    }

    /**
     * Test default constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testDefaultConstructor() {
        CourseRegRecord defaultRecord = new CourseRegRecord();

        assertNull(defaultRecord.getStudentKey());
        assertNull(defaultRecord.getCourseKey());
    }

    /**
     * Test parameter constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testParameterizedConstructorAndGetters() {
        assertEquals("123123123", record.getStudentKey());
        assertEquals("456456456", record.getCourseKey());
    }

    /**
     * Test setter
     * @author Wong Hon Yin
     */
    @Test
    public void testSetters() {
        record.setStudentKey("789789789");
        record.setCourseKey("101010101");

        assertEquals("789789789", record.getStudentKey());
        assertEquals("101010101", record.getCourseKey());
    }
}