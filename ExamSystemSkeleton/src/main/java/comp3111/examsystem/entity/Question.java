/**
 * Represents a Question in the examination system. This class holds the details of a question
 * such as the question text, possible answer options, the correct answer, the type of question,
 * and its score value. The `Question` class can represent both single-answer and multiple-answer questions.
 * The class includes constructors for creating a `Question` object, along with getter and setter methods
 * for each attribute. It also overrides `toString()` for a formatted string representation and `equals()`
 * to compare `Question` objects based on their ID.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */
package comp3111.examsystem.entity;

import java.util.Objects;

public class Question extends Entity{
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String type; // "Single" or "Multiple"
    private String score;

    /**
     * Default constructor for the `Question` class.
     * This constructor initializes the `Question` object without setting any of its attributes.
     * @author Seokhyeon Hong
     */
    public Question() { super();}

    /**
     * Constructor to initialize a `Question` object with specific values for all attributes.
     *
     * @param question The question text.
     * @param optionA The first option for the question.
     * @param optionB The second option for the question.
     * @param optionC The third option for the question.
     * @param optionD The fourth option for the question.
     * @param answer The correct answer (A, B, C, or D).
     * @param type The type of question: "Single" or "Multiple".
     * @param score The score assigned to this question.
     * @author Seokhyeon Hong
     */
    public Question(String question, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.type = type;
        this.score = score;
    }

    /**
     * Gets the question text.
     *
     * @return The question text.
     * @author Seokhyeon Hong
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text.
     *
     * @param question The question text to set.
     * @author Seokhyeon Hong
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the first option for the question (Option A).
     *
     * @return The first option for the question.
     * @author Seokhyeon Hong
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Sets the first option for the question (Option A).
     *
     * @param optionA The first option to set.
     * @author Seokhyeon Hong
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * Gets the second option for the question (Option B).
     *
     * @return The second option for the question.
     * @author Seokhyeon Hong
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Sets the second option for the question (Option B).
     *
     * @param optionB The second option to set.
     * @author Seokhyeon Hong
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * Gets the third option for the question (Option C).
     *
     * @return The third option for the question.
     * @author Seokhyeon Hong
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * Sets the third option for the question (Option C).
     *
     * @param optionC The third option to set.
     * @author Seokhyeon Hong
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     * Gets the fourth option for the question (Option D).
     *
     * @return The fourth option for the question.
     * @author Seokhyeon Hong
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * Sets the fourth option for the question (Option D).
     *
     * @param optionD The fourth option to set.
     * @author Seokhyeon Hong
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     * Gets the correct answer for the question.
     *
     * @return The correct answer (A, B, C, or D).
     * @author Seokhyeon Hong
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the correct answer for the question.
     *
     * @param answer The correct answer to set (A, B, C, or D).
     * @author Seokhyeon Hong
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets the type of the question (either "Single" or "Multiple").
     *
     * @return The question type.
     * @author Seokhyeon Hong
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the question (either "Single" or "Multiple").
     *
     * @param type The type of the question to set.
     * @author Seokhyeon Hong
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the score assigned to the question.
     *
     * @return The score for the question.
     * @author Seokhyeon Hong
     */
    public String getScore() {
        return score;
    }

    /**
     * Sets the score for the question.
     *
     * @param score The score to set for the question.
     * @author Seokhyeon Hong
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Returns a string representation of the `Question` object.
     *
     * @return A string representation of the `Question` object in the form of:
     *         "Question{question='questionText', optionA='A', optionB='B', optionC='C', optionD='D', answer='A', type='Single', score=5}"
     * @author Seokhyeon Hong
     */
    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", answer='" + answer + '\'' +
                ", type='" + type + '\'' +
                ", score=" + score +
                '}';
    }

    /**
     * Compares this `Question` object to another object for equality.
     * Two `Question` objects are considered equal if they have the same ID.
     *
     * @param obj The object to compare with this `Question`.
     * @return `true` if the `Question` objects are equal, `false` otherwise.
     * @author Seokhyeon Hong
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Question question = (Question) obj;
        return id == question.id;
    }
}
