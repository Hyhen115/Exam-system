package comp3111.examsystem.service;
import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Student;

import java.util.List;

public class StudentRegisterService {

    public static void addStudent(String username, String name, String gender, String ageText, String department,
                                  String password) {

        Student student = new Student();
        student.setUsername(username);
        student.setName(name);
        student.setGender(gender);
        student.setAge(ageText);
        student.setDepartment(department);
        student.setPassword(password);

        DatabaseService.getStudentDatabase().add(student);
    }
}
