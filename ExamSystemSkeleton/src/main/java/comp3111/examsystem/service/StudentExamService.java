package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.controller.StudentLoginController;
import java.time.LocalTime;

public class StudentExamService {

    /**
     * Adds a grade to the grade database.
     *
     * @param exam the exam object.
     * @param timeRemaining the remaining time for the exam.
     * @param score the score obtained by the student.
     * @param fullScore the total score possible.
     */
    public void addGrade(Exam exam, LocalTime timeRemaining, int score, int fullScore) {
        Grade grade = new Grade();
        grade.setExamId(exam.getId().toString());
        grade.setStudentId(StudentLoginController.SessionManager.getLoggedInStudentId().toString());
        grade.setScore(Integer.toString(score));
        grade.setFullScore(Integer.toString(fullScore));

        String strExamTime = exam.getExamTime();
        // Calculate total number of seconds of exam
        int examTime = Integer.parseInt(strExamTime) * 60;
        // Compare with total number of seconds remaining
        int secondsSpend = examTime - timeRemaining.toSecondOfDay();

        grade.setTimeSpent(Integer.toString(secondsSpend / 60));
        DatabaseService.getGradeDatabase().add(grade);
    }
}