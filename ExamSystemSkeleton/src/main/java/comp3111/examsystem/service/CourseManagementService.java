package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.*;
import comp3111.examsystem.entity.Record;

import java.util.List;

import static comp3111.examsystem.MsgSender.showMsg;

/**
 * service class for delete, add, modify courses for manage course controller
 * @author Wong Hon Yin
 */
public class CourseManagementService {

    /**
     * function for delete student
     * @param selectedCourse selected course from table
     * @return true / false for delete student
     * @author Wong Hon Yin
     */
    public static boolean deleteCourseService(Course selectedCourse) {

        if (selectedCourse != null) {
            // delete the grade related to that course
            List<Grade> grades = DatabaseService.getGradeDatabase().getAll();
            for (Grade grade : grades) {
                if(grade.getCourseKey() != null && grade.getCourseKey().equals(selectedCourse.getId().toString())) {
                    //delete grade
                    DatabaseService.getGradeDatabase().delByKey(grade.getId().toString());
                }
            }

            // delete exam
            List<Exam> exams = DatabaseService.getExamDatabase().getAll();
            for (Exam exam : exams) {
                if(exam.getCourseKey().equals(selectedCourse.getId().toString())) {
                    // found target exam to delete

                    // delete all records of that exam
                    List<Record> records = DatabaseService.getRecordDatabase().getAll();
                    for (Record record : records) {
                        if(record.getExamKey().equals(exam.getId().toString())) {
                            DatabaseService.getRecordDatabase().delByKey(record.getId().toString());
                        }
                    }

                    DatabaseService.getExamDatabase().delByKey(exam.getId().toString());
                }
            }

            // delete course reg records
            List<CourseRegRecord> courseRegRecords = DatabaseService.getCourseRegRecordDatabase().getAll();
            for (CourseRegRecord courseRegRecord : courseRegRecords) {
                if(courseRegRecord.getCourseKey().equals(selectedCourse.getId().toString())) {
                    DatabaseService.getCourseRegRecordDatabase().delByKey(courseRegRecord.getId().toString());
                }
            }

            // Delete the course
            DatabaseService.getCourseDatabase().delByKey(selectedCourse.getId().toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * the function to return the add course status and add course
     * @param courseName course name
     * @param department course department
     * @param courseID course id
     * @return add course status
     * @author Wong Hon Yin
     */
    public static String addCourseService(String courseName, String department, String courseID) {
        // if have empty fields
        if (courseName.isEmpty() || department.isEmpty() || courseID.isEmpty()) {
            return "EMPTY_FIELDS";
        }

        // check for existing courses
        List<Course> existingCourses = DatabaseService.getCourseDatabase().getAll();
        for (Course course : existingCourses) {
            if (course.getCourseId().equals(courseID)) {
                return "COURSE_ID_EXISTS";
            } else if (course.getCourseName().equals(courseName)) {
                return "COURSE_NAME_EXISTS";
            }
        }

        Course newCourse = new Course();
        newCourse.setCourseId(courseID);
        newCourse.setCourseName(courseName);
        newCourse.setDepartment(department);

        DatabaseService.getCourseDatabase().add(newCourse);
        return "COURSE_ADDED";

    }

    /**
     * function for modify the course status
     * @param selectedCourse selected course on table
     * @param courseName course name
     * @param department course department
     * @param courseID course id
     * @return modify course status
     * @author Wong Hon Yin
     */
    public static String modifyCourseService(Course selectedCourse,String courseName, String department, String courseID) {

        // check null
        if (selectedCourse == null) {
            return "COURSE_NOT_SELECTED";
        }

        //check empty
        if (courseName.isEmpty() || department.isEmpty() || courseID.isEmpty()) {
            return "EMPTY_FIELDS";
        }

        // check for existing course
        List<Course> existingCourses = DatabaseService.getCourseDatabase().getAll();
        for (Course course : existingCourses) {
            // check duplicate courseID
            // dbcourse != selectedcourse and dbcourse == input
            if (!course.getCourseId().equals(selectedCourse.getCourseId()) && course.getCourseId().equals(courseID)) {
                return "COURSE_ID_EXISTS";
            }

            // Check for duplicate course name
            if (!course.getCourseName().equals(selectedCourse.getCourseName()) && course.getCourseName().equals(courseName)) {
                return "COURSE_NAME_EXISTS";
            }
        }

        selectedCourse.setCourseName(courseName);
        selectedCourse.setCourseId(courseID);
        selectedCourse.setDepartment(department);

        DatabaseService.getCourseDatabase().update(selectedCourse);
        return "COURSE_MODIFIED";

    }

}
