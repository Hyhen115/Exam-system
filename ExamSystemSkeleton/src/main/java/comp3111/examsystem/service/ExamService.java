package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseService;
import comp3111.examsystem.entity.*;
import comp3111.examsystem.entity.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * The ExamService class encapsulates backend logic for managing exams, including CRUD operations,
 * question management, and filtering.
 * @author Seokhyeon Hong
 * @version 1.0
 * @since 2024-11-21
 */
public class ExamService {

    /**
     * Retrieves all exams from the database.
     * @return A list of all exams.
     * @author Seokhyeon Hong
     */
    public List<Exam> getAllExams() {
        return DatabaseService.getExamDatabase().getAll();
    }

    /**
     * Retrieves all questions from the database.
     * @return A list of all questions.
     * @author Seokhyeon Hong
     */
    public List<Question> getAllQuestions() {
        return DatabaseService.getQuestionDatabase().getAll();
    }

    public List<Course> getAllCourses(){
        return DatabaseService.getCourseDatabase().getAll();
    }

    /**
     *
     */
    public List<Record> getAllRecords(){
        return DatabaseService.getRecordDatabase().getAll();
    }
    /**
     * Retrieves all records for a given exam ID.
     * @param examId The ID of the exam.
     * @return A list of records associated with the exam.
     * @author Seokhyeon Hong
     */
    public List<Record> getRecordsForExam(Long examId) {
        List<Record> allRecords = DatabaseService.getRecordDatabase().getAll();
        List<Record> filteredRecords = new ArrayList<>();

        for (Record record : allRecords) {
            if (String.valueOf(examId).equals(record.getExamKey())) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }

    /**
     * Deletes all records associated with a given exam ID.
     * @param examId The ID of the exam.
     * @author Seokhyeon Hong
     */
    public void deleteRecordsForExam(Long examId) {
        List<Record> records = getRecordsForExam(examId);
        for (Record record : records) {
            DatabaseService.getRecordDatabase().delByKey(String.valueOf(record.getId()));
        }
    }

    /**
     * Deletes all grades associated with a given exam ID.
     * @param examId The ID of the exam.
     * @author Seokhyeon Hong
     */
    public void deleteGradesForExam(Long examId) {
        List<Grade> allGrades = DatabaseService.getGradeDatabase().getAll();
        for (Grade grade : allGrades) {
            if (String.valueOf(examId).equals(grade.getExamId())) {
                DatabaseService.getGradeDatabase().delByKey(String.valueOf(grade.getId()));
            }
        }
    }

    /**
     * Deletes an exam and its associated records and grades.
     * @param examId The ID of the exam.
     * @author Seokhyeon Hong
     */
    public void deleteExam(Long examId) {
        deleteRecordsForExam(examId);
        deleteGradesForExam(examId);
        DatabaseService.getExamDatabase().delByKey(String.valueOf(examId));
    }

    /**
     * Adds a new exam to the database.
     * @param exam The exam to be added.
     * @author Seokhyeon Hong
     */
    public void addExam(Exam exam) {
        DatabaseService.getExamDatabase().add(exam);
    }

    /**
     * Updates an existing exam in the database.
     * @param exam The exam to be updated.
     * @author Seokhyeon Hong
     */
    public void updateExam(Exam exam) {
        DatabaseService.getExamDatabase().update(exam);
    }

    /**
     * Adds a question to an exam by creating a new record.
     * @param examId The ID of the exam.
     * @param questionId The ID of the question.
     * @author Seokhyeon Hong
     */
    public void addQuestionToExam(Long examId, Long questionId) {
        Record newRecord = new Record();
        newRecord.setExamKey(String.valueOf(examId));
        newRecord.setQuestionKey(String.valueOf(questionId));
        DatabaseService.getRecordDatabase().add(newRecord);
    }

    /**
     * Deletes a question from an exam by removing the associated record.
     * @param recordId The ID of the record to be deleted.
     * @author Seokhyeon Hong
     */
    public void deleteQuestionFromExam(Long recordId) {
        DatabaseService.getRecordDatabase().delByKey(String.valueOf(recordId));
    }


}
