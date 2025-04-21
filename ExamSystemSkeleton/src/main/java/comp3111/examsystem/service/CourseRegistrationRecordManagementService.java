package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.CourseRegRecord;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Student;

import java.util.List;

import static comp3111.examsystem.MsgSender.showMsg;

/**
 * Service class for delete, add course reg records for course reg controller
 * @author Wong Hon Yin
 */
public class CourseRegistrationRecordManagementService {
    /**
     * The function for delete course reg records
     * @param selectedRecord record selected in the table
     * @return true / false for is the record deleted
     * @author Wong Hon Yin
     */
    public static Boolean deleteCourseRegistrationRecordService(CourseRegRecord selectedRecord) {
        if (selectedRecord != null) {

            // delete all grades of the student in that course
            List<Grade> gradeList = DatabaseService.getGradeDatabase().getAll();

            for (Grade grade : gradeList) {
                if ( grade.getCourseKey() != null && grade.getCourseKey().equals(selectedRecord.getCourseKey()) && grade.getStudentId().equals(selectedRecord.getStudentKey())) {
                    DatabaseService.getGradeDatabase().delByKey(grade.getId().toString());
                }
            }

            // delete selected course reg record
            DatabaseService.getCourseRegRecordDatabase().delByKey(selectedRecord.getId().toString());

            return true;
        } else {
            return false;
        }
    }

    /**
     * The function to add course reg records
     * @param selectedCourse course reg
     * @param selectedStudent student reg
     * @return Status of adding courses to student
     * @author Wong Hon Yin
     */
    public static String addCourseRegistrationRecordService(String selectedCourse, String selectedStudent) {
        String selectedCourseKey = "";
        String selectedStudentKey = "";

        if (selectedStudent == null) {
            return "STUDENT_IS_NULL";
        }

        if(selectedCourse == null) {
            return "COURSE_IS_NULL";
        }

        // find course key
        Database<Course> courseDatabase = DatabaseService.getCourseDatabase();
        List<Course> tempCourseList = courseDatabase.queryByField("courseId", selectedCourse);
        // has course in it
        if (!tempCourseList.isEmpty()) {
            selectedCourseKey = tempCourseList.getFirst().getId().toString();
        } else {
            return "NO_COURSE_SELECTED";
        }


        //find student key
        Database<Student> studentDatabase = DatabaseService.getStudentDatabase();
        List<Student> tempStudentList = studentDatabase.queryByField("username", selectedStudent);
        // has student in it
        if (!tempStudentList.isEmpty()) {
            selectedStudentKey = tempStudentList.getFirst().getId().toString();
        } else {
            return "NO_STUDENT_SELECTED";
        }

        // find if have same record
        List<CourseRegRecord> courseRegRecordList = DatabaseService.getCourseRegRecordDatabase().getAll();
        for (CourseRegRecord courseRegRecord : courseRegRecordList) {
            if (courseRegRecord.getCourseKey().equals(selectedCourseKey) && courseRegRecord.getStudentKey().equals(selectedStudentKey)) {
                return "RECORD_ALREADY_EXISTS";
            }
        }

        // add record
        CourseRegRecord newRecord = new CourseRegRecord();
        newRecord.setCourseKey(selectedCourseKey);
        newRecord.setStudentKey(selectedStudentKey);

        DatabaseService.getCourseRegRecordDatabase().add(newRecord);
        return "RECORD_ADDED";
    }
}
