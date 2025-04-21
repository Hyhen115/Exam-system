package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Question;

import java.util.List;

/**
 * Service class for handling backend operations related to the question bank.
 * Provides methods to add, update, delete, filter, and retrieve questions from the database.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */
public class QuestionService {

    /**
     * Adds a new question to the database.
     *
     * @param question the question to add
     * @author Seokhyeon Hong
     */
    public void addQuestion(Question question) {
        DatabaseService.getQuestionDatabase().add(question);
    }

    /**
     * Updates an existing question in the database.
     *
     * @param question the question to update
     * @author Seokhyeon Hong
     */
    public void updateQuestion(Question question) {
        DatabaseService.getQuestionDatabase().update(question);
    }

    /**
     * Deletes a question from the database by its ID.
     *
     * @param id the ID of the question to delete
     * @author Seokhyeon Hong
     */
    public void deleteQuestion(String id) {
        DatabaseService.getQuestionDatabase().delByKey(id);
    }

    /**
     * Fetches all the questions inside the list.
     *
     * @return it returns all the questions inside the database
     * @author Seokhyeon Hong
     */
    public List<Question> getAllQuestions(){
        return DatabaseService.getQuestionDatabase().getAll();
    }
}
