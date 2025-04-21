package comp3111.examsystem.entity;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.Database;
/**
 * Represents a record in the examination system that associates a specific question with an exam.
 * This class stores the keys for both the question and the exam, and provides methods to retrieve
 * additional information about the associated exam and question such as their names, types, and scores.
 * The `Record` class allows for querying the exam and question details based on their respective keys
 * stored within the record.
 *
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-10-20
 */
public class Record extends Entity{

    private String questionKey;
    private String examKey;

    /**
     * Default constructor for the `Record` class.
     * This constructor initializes a `Record` object without setting the question and exam keys.
     * @author Seokhyeon Hong
     */
    public Record() { super();}

    /**
     * Constructor to initialize a `Record` object with a specific ID, question key, and exam key.
     *
     * @param id The unique identifier for the record.
     * @param questionKey The key representing the associated question.
     * @param examKey The key representing the associated exam.
     * @author Seokhyeon Hong
     */
    public Record(Long id, String questionKey, String examKey){
        super(id);
        this.examKey = examKey;
        this.questionKey = questionKey;
    }

    /**
     * Retrieves the name of the exam associated with this record.
     * The method queries the `Exam` database using the `examKey` stored in the record and returns
     * the name of the corresponding exam. If the exam is not found, it returns `null`.
     *
     * @return The name of the exam associated with the record, or `null` if the exam cannot be found.
     * @author Seokhyeon Hong
     */
    public String getExamName(){
        Database<Exam> examDatabase = DatabaseService.getExamDatabase();

        Exam exam = examDatabase.queryByKey(examKey);
        if(exam == null){
            return null;
        } else {
            return exam.getExamName();
        }
    }

    /**
     * Retrieves the type of the question associated with this record.
     * The method queries the `Question` database using the `questionKey` stored in the record and
     * returns the type of the corresponding question (e.g., "Single" or "Multiple"). If the question
     * is not found, it returns `null`.
     *
     * @return The type of the question associated with the record, or `null` if the question cannot be found.
     * @author Seokhyeon Hong
     */
    public String getQuestionType(){
        Database<Question> questionDatabase = DatabaseService.getQuestionDatabase();

        Question question = questionDatabase.queryByKey(questionKey);
        if(question == null){
            return null;
        } else {
            return question.getType();
        }

    }

    /**
     * Retrieves the score assigned to the question associated with this record.
     * The method queries the `Question` database using the `questionKey` stored in the record and
     * returns the score of the corresponding question. If the question is not found, it returns `null`.
     *
     * @return The score of the question associated with the record, or `null` if the question cannot be found.
     * @author Seokhyeon Hong
     */
    public String getQuestionScore(){
        Database<Question> questionDatabase = DatabaseService.getQuestionDatabase();

        Question question = questionDatabase.queryByKey(questionKey);
        if(question == null){
            return null;
        } else {
            return question.getScore();
        }
    }

    /**
     * Retrieves the name of the question associated with this record.
     * The method queries the `Question` database using the `questionKey` stored in the record and
     * returns the text of the corresponding question. If the question is not found, it returns `null`.
     *
     * @return The name (text) of the question associated with the record, or `null` if the question cannot be found.
     * @author Seokhyeon Hong
     */
    public String getQuestionName(){
        Database<Question> questionDatabase = DatabaseService.getQuestionDatabase();

        Question question = questionDatabase.queryByKey(questionKey);
        if(question == null){
            return null;
        } else {
            return question.getQuestion();
        }
    }


    /**
     * Retrieves the question instance associated with this record.
     * The method queries the `Question` database using the `questionKey` stored in the record and
     * returns the text of the corresponding question. If the question is not found, it returns `null`.
     *
     * @return The question instance associated with the record, or `null` if the question cannot be found.
     * @author Seokhyeon Hong
     */
    public Question getQuestion(){
        Database<Question> questionDatabase = DatabaseService.getQuestionDatabase();
        Question question = questionDatabase.queryByKey(questionKey);
        if(question == null){
            return null;
        } else {
            return question;
        }
    }

    /**
     * Sets the key for the question associated with this record.
     *
     * @param questionKey The key representing the associated question.
     * @author Seokhyeon Hong
     */
    public void setQuestionKey(String questionKey){
        this.questionKey = questionKey;
    }

    /**
     * Sets the key for the exam associated with this record.
     *
     * @param examKey The key representing the associated exam.
     * @author Seokhyeon Hong
     */
    public void setExamKey(String examKey){
        this.examKey = examKey;
    }

    /**
     * Retrieves the key for the exam associated with this record.
     *
     * @return The key for the exam associated with this record.
     * @author Seokhyeon Hong
     */
    public String getExamKey() {return examKey;}

    /**
     * Retrieves the key for the question associated with this record.
     *
     * @return The key for the question associated with this record
     * @author Seokhyeon Hong
     */
    public String getQuestionKey(){return questionKey;}
}
