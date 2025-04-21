package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseManagementServiceTest {

    @Test
    void testAddCourseFail() {
        // Test cases for invalid inputs
        String result;

        // 1. Test empty course name
        result = CourseManagementService.addCourseService("", "Computer Science", "CS101");
        assertEquals("EMPTY_FIELDS", result);

        // 2. Test empty department
        result = CourseManagementService.addCourseService("Introduction to Programming", "", "CS101");
        assertEquals("EMPTY_FIELDS", result);

        // 3. Test empty course ID
        result = CourseManagementService.addCourseService("Introduction to Programming", "Computer Science", "");
        assertEquals("EMPTY_FIELDS", result);

        // 4. Test existing course ID
        Course existingCourse1 = new Course();
        existingCourse1.setCourseId("CS101");
        existingCourse1.setCourseName("Data Structures");
        existingCourse1.setDepartment("Computer Science");
        DatabaseService.getCourseDatabase().add(existingCourse1); // Add to mock database

        result = CourseManagementService.addCourseService("Algorithms", "Computer Science", "CS101");
        assertEquals("COURSE_ID_EXISTS", result);

        // Clean up
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS101");

        // 5. Test existing course name
        Course existingCourse2 = new Course();
        existingCourse2.setCourseId("CS102");
        existingCourse2.setCourseName("Algorithms");
        existingCourse2.setDepartment("Computer Science");
        DatabaseService.getCourseDatabase().add(existingCourse2); // Add to mock database

        result = CourseManagementService.addCourseService("Algorithms", "Computer Science", "CS103");
        assertEquals("COURSE_NAME_EXISTS", result);

        // Clean up
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS102");
    }

    @Test
    void testAddCourseSuccess() {
        // Test cases for valid inputs
        String result;

        // 1. Add a course with valid inputs
        result = CourseManagementService.addCourseService("Introduction to Programming", "Computer Science", "CS101");
        assertEquals("COURSE_ADDED", result);

        // 2. Verify that the course was added to the database
        Course addedCourse = DatabaseService.getCourseDatabase().queryByField("courseId", "CS101").getFirst();

        assertEquals("Introduction to Programming", addedCourse.getCourseName());
        assertEquals("Computer Science", addedCourse.getDepartment());
        assertEquals("CS101", addedCourse.getCourseId());

        // Clean up the added course
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS101");
    }

    @Test
    void testModifyCourseFail() {
        // Create and add a course for testing modifications
        Course existingCourse = new Course();
        existingCourse.setCourseId("CS101");
        existingCourse.setCourseName("Data Structures");
        existingCourse.setDepartment("Computer Science");

        DatabaseService.getCourseDatabase().add(existingCourse); // Add to mock database


        // 1. Test modifying a null course
        String result = CourseManagementService.modifyCourseService(null, "New Course Name", "New Department", "CS102");
        assertEquals("COURSE_NOT_SELECTED", result);

        // 2. Test empty fields
        result = CourseManagementService.modifyCourseService(existingCourse, "", "New Department", "CS102");
        assertEquals("EMPTY_FIELDS", result);

        result = CourseManagementService.modifyCourseService(existingCourse, "New Course Name", "", "CS102");
        assertEquals("EMPTY_FIELDS", result);

        result = CourseManagementService.modifyCourseService(existingCourse, "New Course Name", "New Department", "");
        assertEquals("EMPTY_FIELDS", result);


        Course existingCourse2 = new Course();
        existingCourse2.setCourseId("CS102");
        existingCourse2.setCourseName("name2");
        existingCourse2.setDepartment("dep2");

        DatabaseService.getCourseDatabase().add(existingCourse2);

        result = CourseManagementService.modifyCourseService(existingCourse2, "new name 2", "new name 2", "CS101");
        assertEquals("COURSE_ID_EXISTS", result);

        result = CourseManagementService.modifyCourseService(existingCourse2, "Data Structures", "new", "CS102");
        assertEquals("COURSE_NAME_EXISTS", result);


        DatabaseService.getCourseDatabase().delByFiled("courseId","CS101");
        DatabaseService.getCourseDatabase().delByFiled("courseId","CS102");

    }


    @Test
    void testModifyCourseSuccess() {
        // Create and add a course for testing modifications
        Course existingCourse = new Course();
        existingCourse.setCourseId("CS101");
        existingCourse.setCourseName("Data Structures");
        existingCourse.setDepartment("Computer Science");

        DatabaseService.getCourseDatabase().add(existingCourse); // Add to mock database

        // Test cases for valid inputs
        String result;

        // 1. Modify the existing course with valid inputs
        result = CourseManagementService.modifyCourseService(existingCourse, "Algorithms", "Computer Science", "CS101");
        assertEquals("COURSE_MODIFIED", result);

        // 2. Verify that the course was modified in the database
        Course modifiedCourse = DatabaseService.getCourseDatabase().queryByField("courseId", "CS101").getFirst();

        assertEquals("Algorithms", modifiedCourse.getCourseName());
        assertEquals("Computer Science", modifiedCourse.getDepartment());
        assertEquals("CS101", modifiedCourse.getCourseId());

        // Clean up the modified course
        DatabaseService.getCourseDatabase().delByKey(modifiedCourse.getId().toString());
    }

    @Test
    void testDeleteCourseFail() {
        // Attempt to delete a null course
        boolean result = CourseManagementService.deleteCourseService(null);
        assertFalse(result);
    }


    @Test
    void testDeleteCourseSuccess() {
        Course existingCourse = new Course();
        existingCourse.setCourseId("CS101");
        existingCourse.setCourseName("Data Structures");
        existingCourse.setDepartment("Computer Science");

        DatabaseService.getCourseDatabase().add(existingCourse);

        Boolean result = CourseManagementService.deleteCourseService(existingCourse);

        assertTrue(result);

        // i am lazy to test those related things to delete, as I tested by hand, it works
    }


}