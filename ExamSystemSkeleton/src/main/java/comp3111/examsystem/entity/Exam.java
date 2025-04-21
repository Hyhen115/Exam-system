package comp3111.examsystem.entity;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;

import java.util.List;
/**
 * @author Wong Hon Yin
 * id -> the KEY of the exam (1730804311726)
 * examName -> Name of the exam (Final Exam)
 * courseKey -> the KEY of the linked course (1730804311725)
 * examTime -> overall time of the exam (60)
 * publish -> is the exam published (true/false)
 */
public class Exam extends Entity {
    private String examName;
    private String courseKey;
    private String examTime;
    private String publish;

    public Exam() {
        super();
    }
    public Exam(Long id, String examName, String courseKey, String examTime, String publish) {
        this.examName = examName;
        this.courseKey = courseKey;
        this.examTime = examTime;
        this.publish = publish;
    }
    // setters and getters
    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public String getCourseKey() {
        return courseKey;
    }
    public void setCourseId(String courseKey) {
        this.courseKey = courseKey;
    }
    public String getExamTime() {
        return examTime;
    }
    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }


    /**
     * @author Wong Hon Yin
     * @return the linked course id (COMP3111)
     */
    public String getCourseNum (){
        Database<Course> courseDatabase = DatabaseService.getCourseDatabase();
        Course course = courseDatabase.queryByKey(courseKey);
        return course.getCourseId();

    }
}