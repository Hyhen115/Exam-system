package comp3111.examsystem;

import comp3111.examsystem.entity.*;
import comp3111.examsystem.entity.Record;
import javafx.scene.chart.XYChart;

public class DatabaseService {
    private static Database<Student> studentDatabase = new Database<>(Student.class);
    private static Database<Teacher> teacherDatabase = new Database<>(Teacher.class);
    private static final Database<Course> courseDatabase = new Database<>(Course.class);
    private static Database<Exam> examDatabase = new Database<>(Exam.class);
    private static Database<Grade> gradeDatabase = new Database<>(Grade.class);
    private static Database<Question> questionDatabase = new Database<>(Question.class);
    private static Database<Record> recordDatabase = new Database<>(Record.class);
    private static final Database<CourseRegRecord> courseRegRecordDatabase = new Database<>(CourseRegRecord.class);

    public DatabaseService() {}

    /**
     * @return the student database binding to the student.txt file for data storage
     */
    //getters
    public static Database<Student> getStudentDatabase() {
        return studentDatabase;
    }

    /**
     * @return the teacher database binding to the teacher.txt file for data storage
     */
    public static Database<Teacher> getTeacherDatabase() {
        return teacherDatabase;
    }

    /**
     * @return the course database binding to the course.txt file for data storage
     */
    public static Database<Course> getCourseDatabase() {
        return courseDatabase;
    }

    /**
     * @return the exam database binding to the exam.txt file for data storage
     */
    public static Database<Exam> getExamDatabase() {
        return examDatabase;
    }

    /**
     * @return the grade database binding to the grade.txt file for data storage
     */
    public static Database<Grade> getGradeDatabase() {
        return gradeDatabase;
    }

    /**
     * @return the question database binding to the question.txt file for data storage
     */
    public static Database<Question> getQuestionDatabase() {return questionDatabase;}

    /**
     * @return the exam-question record links database binding to the record.txt file for data storage
     */
    public static Database<Record> getRecordDatabase() {return recordDatabase;}

    /**
     * @return the course registration records database binding to the courseregrecord.txt file for data storage
     */
    public static Database<CourseRegRecord> getCourseRegRecordDatabase() {return courseRegRecordDatabase;}

    /**
     * sets teacherdatabase to be the one given in parameter
     * used for creating mock database for unit Test
     * @param database the database to be set
     * @author Seokhyeon Hong
     */
    public static void setTeacherDatabase(Database<Teacher> database) {
        teacherDatabase = database;
    }

    /**
     * sets questiondatabase to be the one given in parameter
     * used for creating mock database for unit Test
     * @param database the database to be set
     * @author Seokhyeon Hong
     */
    public static void setQuestionDatabase(Database<Question> database){
        questionDatabase = database;
    }

    /**
     * sets ExamDatabase to be the one given in parameter
     * used for creating mock database for unit Test
     * @param database the database to be set
     * @author Seokhyeon Hong
     */
    public static void setExamDatabase(Database<Exam> database){
        examDatabase = database;
    }

    /**
     * sets RecordDatabase to be the one given in parameter
     * used for creating mock database for unit Test
     * @param database the database to be set
     * @author Seokhyeon Hong
     */
    public static void setRecordDatabase(Database<Record> database){
        recordDatabase = database;
    }

    /**
     * sets GradeDatabase to be the one given in parameter
     * used for creating mock database for unit Test
     * @param database the database to be set
     * @author Seokhyeon Hong
     */
    public static void setGradeDatabase(Database<Grade> database){
        gradeDatabase = database;
    }

    /**
     * sets StudentDatabase to be the one given in parameter
     * used for creating mock database for unit Test
     * @param database the database to be set
     * @author Lee Hyunjin
     */
    public static void setStudentDatabase(Database<Student> database){
        studentDatabase = database;
    }

}


