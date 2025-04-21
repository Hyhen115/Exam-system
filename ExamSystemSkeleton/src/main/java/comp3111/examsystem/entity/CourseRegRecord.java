package comp3111.examsystem.entity;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;

/**
 * @author Wong Hon Yin
 * A class for the records of student registered into which course
 * studentKey -> the student Key (123123123)
 * courseJey -> the course student reg into (123123123)
 */
public class CourseRegRecord extends Entity {
    private String studentKey;
    private String courseKey;

    public CourseRegRecord() {
        super();
    }

    public CourseRegRecord(Long id,String studentKey, String courseKey) {
        super(id);
        this.studentKey = studentKey;
        this.courseKey = courseKey;
    }

    public String getStudentKey() {
        return studentKey;
    }

    public void setStudentKey(String studentKey) {
        this.studentKey = studentKey;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    /**
     * @author Wong Hon Yin
     * @return the student name reference to the student key
     */
    public String getStudentName(){
        Database<Student> studentDB = DatabaseService.getStudentDatabase();
        Student student = studentDB.queryByKey(studentKey);

        return student.getUsername();
    }

    /**
     * @author Wong Hon Yin
     * @return th course name reference to the course name
     */
    public String getCourseName(){
        Database<Course> courseDB = DatabaseService.getCourseDatabase();
        Course course = courseDB.queryByKey(courseKey);

        return course.getCourseId();
    }
}
