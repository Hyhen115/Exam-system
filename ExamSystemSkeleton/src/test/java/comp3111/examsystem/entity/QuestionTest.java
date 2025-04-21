package comp3111.examsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Question} class.
 * These tests ensure that all methods in the `Question` class work as expected.
 * Includes tests for constructors, getter and setter methods, `toString()`, and `equals()`.
 * Achieves 100% branch coverage by testing various scenarios such as null checks and field variations.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */
class QuestionTest {

    private Question question;
    /**
     * Initializes a {@link Question} object before each test.
     * Sets up the base data for the tests.
     *
     * @author Seokhyeon Hong
     */
    @BeforeEach
    void setUp() {
        question = new Question(
                "What is the capital of France?",
                "Berlin",
                "Madrid",
                "Paris",
                "Rome",
                "C",
                "Single",
                "5"
        );
        question.setId(1L); // Ensures `id` is set for `equals` testing
    }
    /**
     * Tests the parameterized constructor and getter methods of the {@link Question} class.
     * Ensures that all fields are properly initialized and returned by their respective getters.
     *
     * @author Seokhyeon Hong
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("What is the capital of France?", question.getQuestion());
        assertEquals("Berlin", question.getOptionA());
        assertEquals("Madrid", question.getOptionB());
        assertEquals("Paris", question.getOptionC());
        assertEquals("Rome", question.getOptionD());
        assertEquals("C", question.getAnswer());
        assertEquals("Single", question.getType());
        assertEquals("5", question.getScore());
    }

    /**
     * Tests the setter methods of the {@link Question} class.
     * Ensures that all fields can be modified and their updated values are returned correctly.
     *
     * @author Seokhyeon Hong
     */
    @Test
    void testSetters() {
        question.setQuestion("What is the largest ocean?");
        question.setOptionA("Atlantic");
        question.setOptionB("Indian");
        question.setOptionC("Arctic");
        question.setOptionD("Pacific");
        question.setAnswer("D");
        question.setType("Multiple");
        question.setScore("10");

        assertEquals("What is the largest ocean?", question.getQuestion());
        assertEquals("Atlantic", question.getOptionA());
        assertEquals("Indian", question.getOptionB());
        assertEquals("Arctic", question.getOptionC());
        assertEquals("Pacific", question.getOptionD());
        assertEquals("D", question.getAnswer());
        assertEquals("Multiple", question.getType());
        assertEquals("10", question.getScore());
    }

    /**
     * Tests the default constructor of the {@link Question} class.
     * Ensures that all fields are initialized to null or their default values.
     *
     * @author Seokhyeon Hong
     */
    @Test
    void testDefaultConstructor() {
        Question defaultQuestion = new Question();
        assertNotNull(defaultQuestion);
        assertNull(defaultQuestion.getQuestion());
        assertNull(defaultQuestion.getOptionA());
        assertNull(defaultQuestion.getOptionB());
        assertNull(defaultQuestion.getOptionC());
        assertNull(defaultQuestion.getOptionD());
        assertNull(defaultQuestion.getAnswer());
        assertNull(defaultQuestion.getType());
        assertNull(defaultQuestion.getScore());
    }
    /**
     * Tests the {@code toString()} method of the {@link Question} class.
     * Verifies that the string representation matches the expected format.
     *
     * @author Seokhyeon Hong
     */
    @Test
    void testToString() {
        String expected = "Question{question='What is the capital of France?', optionA='Berlin', optionB='Madrid', optionC='Paris', optionD='Rome', answer='C', type='Single', score=5}";
        assertEquals(expected, question.toString());

        // Null fields
        question.setQuestion(null);
        question.setOptionA(null);
        question.setOptionB(null);
        question.setOptionC(null);
        question.setOptionD(null);
        question.setAnswer(null);
        question.setType(null);
        question.setScore(null);

        String nullExpected = "Question{question='null', optionA='null', optionB='null', optionC='null', optionD='null', answer='null', type='null', score=null}";
        assertEquals(nullExpected, question.toString());
    }

    /**
     * Tests the {@code equals()} method of the {@link Question} class.
     * Ensures that the method correctly compares objects for equality based on their ID.
     *
     * @author Seokhyeon Hong
     */
    @Test
    void testEquals() {
        // Same object reference
        assertEquals(question, question);

        // Null comparison
        assertNotEquals(question, null);

        // Different class
        assertNotEquals(question, "Not a Question");

        // Same ID and content
        Question sameQuestion = new Question(
                "What is the capital of France?",
                "Berlin",
                "Madrid",
                "Paris",
                "Rome",
                "C",
                "Single",
                "5"
        );
        sameQuestion.setId(1L); // Same ID as `question`
        assertEquals(question, sameQuestion);

        // Different ID
        Question differentIdQuestion = new Question(
                "What is the capital of France?",
                "Berlin",
                "Madrid",
                "Paris",
                "Rome",
                "C",
                "Single",
                "5"
        );
        differentIdQuestion.setId(2L); // Different ID
        assertNotEquals(question, differentIdQuestion);

        // Completely different content
        Question differentContentQuestion = new Question(
                "What is the largest ocean?",
                "Atlantic",
                "Indian",
                "Arctic",
                "Pacific",
                "D",
                "Multiple",
                "10"
        );
        differentContentQuestion.setId(3L);
        assertNotEquals(question, differentContentQuestion);
    }

    /**
     * Tests variations of the `type` field (e.g., "Single" vs "Multiple").
     * Ensures that the type field accepts and returns valid values.
     *
     * @author Seokhyeon Hong
     */
    @Test
    void testTypeVariations() {
        question.setType("Single");
        assertEquals("Single", question.getType());

        question.setType("Multiple");
        assertEquals("Multiple", question.getType());

        // Null type
        question.setType(null);
        assertNull(question.getType());
    }
}
