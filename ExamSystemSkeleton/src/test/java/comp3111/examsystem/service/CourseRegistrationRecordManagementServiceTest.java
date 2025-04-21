package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.CourseRegRecord;
import comp3111.examsystem.entity.Record;
import comp3111.examsystem.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseRegistrationRecordManagementServiceTest {

    @Test
    void TestAddCourseRegistrationRecordFail() {
        // 1. Test adding a record with a null student
        String result = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService("CS101", null);
        assertEquals("STUDENT_IS_NULL", result);

        // 2. Test adding a record with a null course
        result = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService(null, "john_doe");
        assertEquals("COURSE_IS_NULL", result);

        // 3. Test adding a record with a non-existent course
        result = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService("CS999", "john_doe");
        assertEquals("NO_COURSE_SELECTED", result);

        // 4. Test adding a record with a non-existent student
        // add the course
        Course existCourse = new Course();
        existCourse.setCourseId("CS101");
        DatabaseService.getCourseDatabase().add(existCourse);

        result = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService("CS101", "non_existent_student");
        assertEquals("NO_STUDENT_SELECTED", result);

        //add student
        Student existStudent = new Student();
        existStudent.setUsername("existent_student");
        DatabaseService.getStudentDatabase().add(existStudent);

        // add record
        CourseRegRecord regRecord = new CourseRegRecord();
        regRecord.setCourseKey(existCourse.getId().toString());
        regRecord.setStudentKey(existStudent.getId().toString());
        DatabaseService.getCourseRegRecordDatabase().add(regRecord);

        // 5. test add exist reg course record
        result = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService("CS101", "existent_student");


        // clear up
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS101");
        DatabaseService.getStudentDatabase().delByFiled("username","existent_student");
        DatabaseService.getCourseRegRecordDatabase().delByFiled("studentKey",existStudent.getId().toString());
    }

    @Test
    void TestAddCourseRegistrationRecordSuccess() {
        //add student
        Student existStudent = new Student();
        existStudent.setUsername("existent_student");
        DatabaseService.getStudentDatabase().add(existStudent);

        // add the course
        Course existCourse = new Course();
        existCourse.setCourseId("CS101");
        DatabaseService.getCourseDatabase().add(existCourse);

        String result = CourseRegistrationRecordManagementService.addCourseRegistrationRecordService("CS101", "existent_student");

        assertEquals("RECORD_ADDED", result);

        //clear up
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS101");
        DatabaseService.getStudentDatabase().delByFiled("username","existent_student");
        DatabaseService.getCourseRegRecordDatabase().delByFiled("studentKey",existStudent.getId().toString());
    }

    @Test
    void TestDeleteCourseRegistrationRecordFail() {
        Boolean result = CourseRegistrationRecordManagementService.deleteCourseRegistrationRecordService(null);
        assertFalse(result);
    }

    @Test
    void TestDeleteCourseRegistrationRecordSuccess() {
        //add student
        Student existStudent = new Student();
        existStudent.setUsername("existent_student");
        DatabaseService.getStudentDatabase().add(existStudent);

        // add the course
        Course existCourse = new Course();
        existCourse.setCourseId("CS101");
        DatabaseService.getCourseDatabase().add(existCourse);


        // add record
        CourseRegRecord regRecord = new CourseRegRecord();
        regRecord.setCourseKey(existCourse.getId().toString());
        regRecord.setStudentKey(existStudent.getId().toString());
        DatabaseService.getCourseRegRecordDatabase().add(regRecord);

        Boolean result = CourseRegistrationRecordManagementService.deleteCourseRegistrationRecordService(regRecord);
        assertTrue(result);

        // clear up
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS101");
        DatabaseService.getStudentDatabase().delByFiled("username","existent_student");

        // check is the related stuff also deleted (grade), i use hand to check it before, no problem
    }


}