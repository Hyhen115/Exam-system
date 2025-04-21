package comp3111.examsystem.entity;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.Database;
/**
 * @author Wong Hon Yin
 * studentId -> student KEY (1730897297061)
 * examId -> exam KEY (1730804311726)
 * score -> student score (90)
 * fullScore -> full score of that exam (100)
 * timeSpend -> time spend of the student on that exam (65)
 */
public class Grade extends Entity {
    private String studentId; // Store student ID as a String
    private String examId; // Store exam ID as a String
    private String score; // Store score as a String
    private String fullScore; // Store full score as a String
    private String timeSpend; // Store time spent as a String

    public Grade() {
        super();
    }

    public Grade(Long id, String studentId, String examId, String score, String fullScore, String timeSpend) {
        super(id);
        this.studentId = studentId;
        this.examId = examId;
        this.score = score;
        this.fullScore = fullScore;
        this.timeSpend = timeSpend;
    }

    // getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public String getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpent(String timeSpend) {
        this.timeSpend = timeSpend;
    }


    /**
     * @author Wong Hon Yin
     * @return the student Username of that grade (AmyUsername)
     */
    public String getStudentName() {
        Database<Student> studentDatabase = DatabaseService.getStudentDatabase();
        Student student = studentDatabase.queryByKey(studentId);

        if (student == null) {
            return null;
        }
        else {
            return student.getUsername();
        }
    }

    /**
     * @author Wong Hon Yin
     * @return the exam name of the grade (Midterm 1)
     */
    public String getExamName() {
        Database<Exam> examDatabase = DatabaseService.getExamDatabase();

        Exam exam = examDatabase.queryByKey(examId);
        if (exam == null) {
            return null;
        } else {
            return exam.getExamName();
        }
    }

    /**
     * @author Wong Hon Yin
     * @return the courseId of that course (COMP3111)
     */
    public String getCourseNum() {
        Database<Course> courseDatabase = DatabaseService.getCourseDatabase();
        Database<Exam> examDatabase = DatabaseService.getExamDatabase();

        Exam exam = examDatabase.queryByKey(examId);
        if (exam == null) {
            return null;
        } else {
            // get the course id
            Course course = courseDatabase.queryByKey(exam.getCourseKey());
            return course.getCourseId();
        }
    }

    /**
     * @author Wong Hon Yin
     * @return course key
     */
    public String getCourseKey() {
        Database<Course> courseDatabase = DatabaseService.getCourseDatabase();
        Database<Exam> examDatabase = DatabaseService.getExamDatabase();

        Exam exam = examDatabase.queryByKey(examId);
        if (exam == null) {
            return null;
        }
        else {
            Course course = courseDatabase.queryByKey(exam.getCourseKey());
            return course.getId().toString();
        }
    }

}
