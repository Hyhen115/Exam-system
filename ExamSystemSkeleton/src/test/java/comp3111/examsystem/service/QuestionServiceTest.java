package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionServiceTest {

    private QuestionService questionService;
    private static List<Question> mockQuestionDatabase;

    @BeforeEach
    public void setUp() {
        questionService = new QuestionService();
        mockQuestionDatabase = new ArrayList<>();

        // Initialize mock database with one question
        Question question1 = new Question();
        question1.setId(Long.valueOf("11"));
        question1.setQuestion("What's 1+1?");
        question1.setType("Single");
        question1.setOptionA("1");
        question1.setOptionB("2");
        question1.setOptionC("3");
        question1.setOptionD("4");
        question1.setScore("10");
        question1.setAnswer("B");
        mockQuestionDatabase.add(question1);

        // Mock Database implementation
        Database<Question> mockDatabase = new Database<>(Question.class) {
            @Override
            public List<Question> getAll() {
                return new ArrayList<>(mockQuestionDatabase);
            }

            @Override
            public void add(Question entity) {
                mockQuestionDatabase.add(entity);
            }

            @Override
            public void update(Question entity) {
                for (int i = 0; i < mockQuestionDatabase.size(); i++) {
                    if (mockQuestionDatabase.get(i).getId().equals(entity.getId())) {
                        mockQuestionDatabase.set(i, entity);
                        return;
                    }
                }
            }

            @Override
            public void delByKey(String id) {
                Long longId = Long.valueOf(id); // Convert String ID to Long
                mockQuestionDatabase.removeIf(question -> question.getId().equals(longId));
            }

        };

        // Set the mocked database in DatabaseService
        DatabaseService.setQuestionDatabase(mockDatabase);
    }

    @Test
    public void testAddQuestion() {
        Question newQuestion = new Question();
        newQuestion.setId(Long.valueOf("22"));
        newQuestion.setQuestion("What's 2+2?");
        newQuestion.setType("Single");
        newQuestion.setOptionA("3");
        newQuestion.setOptionB("4");
        newQuestion.setOptionC("5");
        newQuestion.setOptionD("6");
        newQuestion.setScore("5");
        newQuestion.setAnswer("B");

        questionService.addQuestion(newQuestion);

        // Verify the question was added
        List<Question> questions = questionService.getAllQuestions();
        assertEquals(2, questions.size());
        assertEquals("What's 2+2?", questions.get(1).getQuestion());
    }

    @Test
    public void testUpdateQuestion() {
        Question updatedQuestion = new Question();
        updatedQuestion.setId(Long.valueOf("11"));
        updatedQuestion.setQuestion("Updated Question?");
        updatedQuestion.setType("Single");
        updatedQuestion.setOptionA("Yes");
        updatedQuestion.setOptionB("No");
        updatedQuestion.setOptionC("Maybe");
        updatedQuestion.setOptionD("None");
        updatedQuestion.setScore("15");
        updatedQuestion.setAnswer("A");

        questionService.updateQuestion(updatedQuestion);

        // Verify the question was updated
        Question retrieved = questionService.getAllQuestions().get(0);
        assertEquals("Updated Question?", retrieved.getQuestion());
        assertEquals("Yes", retrieved.getOptionA());
        assertEquals("15", retrieved.getScore());
    }

    @Test
    public void testDeleteQuestion() {
        // Ensure initial state
        List<Question> questionsBefore = questionService.getAllQuestions();
        assertEquals(1, questionsBefore.size());
        assertEquals(Long.valueOf("11"), questionsBefore.get(0).getId());

        // Delete the question with ID "11"
        questionService.deleteQuestion("11");
        questionService.deleteQuestion("22");

        // Verify the question was deleted
        List<Question> questionsAfter = questionService.getAllQuestions();
        assertTrue(questionsAfter.isEmpty(), "Question was not deleted successfully.");
    }


    @Test
    public void testGetAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();

        // Verify initial questions in the mock database
        assertEquals(1, questions.size());
        assertEquals("What's 1+1?", questions.get(0).getQuestion());
    }
}
