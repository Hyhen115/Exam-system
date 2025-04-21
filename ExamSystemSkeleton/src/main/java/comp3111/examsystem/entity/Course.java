package comp3111.examsystem.entity;
/**
 * @author Wong Hon Yin
 * id -> the key of the course (1730804311725)
 * courseId -> Name of the course in short form (CS201)
 * courseName -> Name of the course (Data Science)
 * department -> Name of the department (Computer Science)
 */
public class Course extends Entity {
    private String courseId;
    private String courseName;
    private String department;


    /**
     * @author Wong Hon Yin
     * Default Constructor
     */
    public Course() {
        super();
    }

    /**
     * @author Wong Hon Yin
     * @param id key will generate itself
     * @param courseId course short form name
     * @param courseName course Name
     * @param department department Name
     */
    public Course(Long id, String courseId, String courseName, String department) {
        super(id);
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
    }

    //getters and setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getCourseKey() {return id;}

}
