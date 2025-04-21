/**
 * Unit tests for the {@link Record} class.
 * Branch coverage includes checks for null comparisons, object types, and various values for attributes.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */

package comp3111.examsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecordTest {

    private Record record;

    /**
     *Setup initial test data for Record.
     *For this example, using hardcoded examKey and questionKey, assuming that these keys exist in the test database.
     *@author Seokhyeon Hong
     */
    @BeforeEach
    public void setUp() {
        record = new Record(1L, "1731153648609", "1730804311732");
    }

    /**
     * test constructor
     * @author Seokhyeon Hong
     */
    @Test
    public void testConstructor(){
        assertNotNull(record);
        assertEquals("1731153648609", record.getQuestionKey());
        assertEquals("1730804311732", record.getExamKey());
    }

    /**
     * Test default constructor
     * @author Seokhyeon Hong
     */
    @Test
    public void testDefaultConstructor(){
        Record defaultRecord = new Record();
        assertNotNull(defaultRecord);
        assertNull(defaultRecord.getQuestionKey());
        assertNull(defaultRecord.getQuestionScore());
        assertNull(defaultRecord.getQuestionName());
        assertNull(defaultRecord.getExamKey());
        assertNull(defaultRecord.getExamName());
        assertNull(defaultRecord.getQuestion());
        assertNull(defaultRecord.getQuestionType());
    }
    /**
     * Test getExamName
     * @author Seokhyeon Hong
     */
    @Test
    public void testGetExamName() {
        String examName = record.getExamName();
        assertNotNull(examName);
        assertEquals("Final Exam", examName);
    }

    /**
     * Test getQuestionType
     * @author Seokhyeon Hong
     */
    @Test
    public void testGetQuestionType() {
        String questionType = record.getQuestionType();
        assertNotNull(questionType);
        assertEquals("Single", questionType);
    }

    /**
     * Test getQuestionScore
     * @author Seokhyeon Hong
     */
    @Test
    public void testGetQuestionScore() {
        String score = record.getQuestionScore();
        assertNotNull(score);
        assertEquals("10", score);
    }

    /**
     * Test getQuestionName
     * @author Seokhyeon Hong
     */
    @Test
    public void testGetQuestionName() {
        // Assuming that "Q001" corresponds to a question with name "What is 2+2?"
        String questionName = record.getQuestionName();
        assertNotNull(questionName);
        assertEquals("What is the rank of a matrix?", questionName);
    }

    /**
     * Test getQuestion
     * @author Seokhyeon Hong
     */
    @Test
    public void testGetQuestion() {
        // Assuming that "Q001" corresponds to an existing question object
        Question question = record.getQuestion();
        assertNotNull(question);
        assertEquals("What is the rank of a matrix?", question.getQuestion());
    }

    /**
     *  Simulate a valid examId that exists in the database
     *  @author Seokhyeon Hong
     */
    @Test
    public void testGetExamNameWithValidExamId() {
        String examName = record.getExamName();
        assertNotNull(examName);
        assertEquals("Final Exam", examName);
    }
}
