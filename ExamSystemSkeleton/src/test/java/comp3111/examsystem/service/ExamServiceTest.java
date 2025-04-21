package comp3111.examsystem.service;

import comp3111.examsystem.Database;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.Record;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamServiceTest {

    private ExamService examService;
    private static List<Exam> mockExamDatabase;
    private static List<Record> mockRecordDatabase;
    private static List<Grade> mockGradeDatabase;
    private static List<Question> mockQuestionDatabase;

    @BeforeEach
    public void setUp() {
        examService = new ExamService();


        mockExamDatabase = new ArrayList<>();
        mockRecordDatabase = new ArrayList<>();
        mockGradeDatabase = new ArrayList<>();
        mockQuestionDatabase = new ArrayList<>();

        Exam exam1 = new Exam();
        exam1.setId(Long.valueOf("11"));
        exam1.setExamTime("150");
        exam1.setExamName("Final");
        exam1.setPublish("Yes");
        exam1.setCourseId("22");
        mockExamDatabase.add(exam1);
        // Add a sample Record to the mockRecordDatabase
        Record record1 = new Record();
        record1.setId(1L);
        record1.setExamKey("11");
        record1.setQuestionKey("22");
        mockRecordDatabase.add(record1);

        // Add a sample Grade to the mockGradeDatabase
        Grade grade1 = new Grade();
        grade1.setId(1L);
        grade1.setStudentId("12345");
        grade1.setExamId("11");
        grade1.setScore("90");
        grade1.setFullScore("100");
        grade1.setTimeSpent("65");
        mockGradeDatabase.add(grade1);
        Question question1 = new Question();
        question1.setId(Long.valueOf("22"));
        question1.setQuestion("What's 1+1?");
        question1.setType("Single");
        question1.setOptionA("1");
        question1.setOptionB("2");
        question1.setOptionC("3");
        question1.setOptionD("4");
        question1.setScore("10");
        question1.setAnswer("B");
        mockQuestionDatabase.add(question1);

        Database<Exam> mockDatabase1 = new Database<>(Exam.class){
            @Override
            public List<Exam> getAll(){
                return new ArrayList<>(mockExamDatabase);
            }
            @Override
            public void add(Exam entity){
                mockExamDatabase.add(entity);
            }
            @Override
            public void update(Exam entity){
                for (int i = 0; i < mockExamDatabase.size(); i++) {
                    if (mockExamDatabase.get(i).getId().equals(entity.getId())) {
                        mockExamDatabase.set(i, entity);
                        return;
                    }
                }
            }
            @Override
            public void delByKey(String id){
                Long longId = Long.valueOf(id);
                mockExamDatabase.removeIf(exam -> exam.getId().equals(longId));
            }
        };
        Database<Question> mockDatabase2 = new Database<>(Question.class) {
            @Override
            public List<Question> getAll() {
                return new ArrayList<>(mockQuestionDatabase);
            }

            @Override
            public void add(Question entity) {
                mockQuestionDatabase.add(entity);
            }

            @Override
            public void update(Question entity) {
                for (int i = 0; i < mockQuestionDatabase.size(); i++) {
                    if (mockQuestionDatabase.get(i).getId().equals(entity.getId())) {
                        mockQuestionDatabase.set(i, entity);
                        return;
                    }
                }
            }

            @Override
            public void delByKey(String id) {
                Long longId = Long.valueOf(id); // Convert String ID to Long
                mockQuestionDatabase.removeIf(question -> question.getId().equals(longId));
            }

        };
        Database<Record> mockDatabase3 = new Database<>(Record.class) {
            @Override
            public List<Record> getAll() {
                return new ArrayList<>(mockRecordDatabase);
            }

            @Override
            public void add(Record entity) {
                mockRecordDatabase.add(entity);
            }

            @Override
            public void update(Record entity) {
                for (int i = 0; i < mockRecordDatabase.size(); i++) {
                    if (mockRecordDatabase.get(i).getId().equals(entity.getId())) {
                        mockRecordDatabase.set(i, entity);
                        return;
                    }
                }
            }

            @Override
            public void delByKey(String id) {
                Long longId = Long.valueOf(id);
                mockRecordDatabase.removeIf(record -> record.getId().equals(longId));
            }
        };

        Database<Grade> mockDatabase4 = new Database<>(Grade.class) {
            @Override
            public List<Grade> getAll() {
                return new ArrayList<>(mockGradeDatabase);
            }

            @Override
            public void add(Grade entity) {
                mockGradeDatabase.add(entity);
            }

            @Override
            public void update(Grade entity) {
                for (int i = 0; i < mockGradeDatabase.size(); i++) {
                    if (mockGradeDatabase.get(i).getId().equals(entity.getId())) {
                        mockGradeDatabase.set(i, entity);
                        return;
                    }
                }
            }

            @Override
            public void delByKey(String id) {
                Long longId = Long.valueOf(id);
                mockGradeDatabase.removeIf(grade -> grade.getId().equals(longId));
            }
        };
        // Set up DatabaseService mock behaviors
        DatabaseService.setExamDatabase(mockDatabase1);
        DatabaseService.setRecordDatabase(mockDatabase3);
        DatabaseService.setGradeDatabase(mockDatabase4);
        DatabaseService.setQuestionDatabase(mockDatabase2);
    }

    @Test
    void testGetAllExams() {
        List<Exam> exams = examService.getAllExams();
        assertEquals(1, exams.size());
        assertEquals("Final", exams.get(0).getExamName());
    }

    @Test
    void testAddExam() {
        Exam newExam = new Exam();
        newExam.setId(3L);
        newExam.setExamName("History Exam");

        examService.addExam(newExam);

        assertEquals(2, mockExamDatabase.size());
        assertEquals("History Exam", mockExamDatabase.get(1).getExamName());
    }

    @Test
    void testDeleteExam() {
        examService.deleteExam(1L);

        assertEquals(1, mockExamDatabase.size());
        assertEquals("Final", mockExamDatabase.get(0).getExamName());
    }

    @Test
    void testGetRecordsForExam() {
        Record record1 = new Record();
        record1.setId(1L);
        record1.setExamKey("1");

        Record record2 = new Record();
        record2.setId(2L);
        record2.setExamKey("2");

        mockRecordDatabase.add(record1);
        mockRecordDatabase.add(record2);

        List<Record> records = examService.getRecordsForExam(1L);
        assertEquals(1, records.size());
        assertEquals(1L, records.get(0).getId());
    }

    @Test
    void testDeleteRecordsForExam() {
        Record record1 = new Record();
        record1.setId(1L);
        record1.setExamKey("1");

        Record record2 = new Record();
        record2.setId(2L);
        record2.setExamKey("1");

        mockRecordDatabase.add(record1);
        mockRecordDatabase.add(record2);

        examService.deleteRecordsForExam(1L);

        assertEquals(0, mockRecordDatabase.size());
    }

    @Test
    void testDeleteGradesForExam() {
        Grade grade1 = new Grade();
        grade1.setId(1L);
        grade1.setExamId("1");

        Grade grade2 = new Grade();
        grade2.setId(2L);
        grade2.setExamId("2");

        mockGradeDatabase.add(grade1);
        mockGradeDatabase.add(grade2);

        examService.deleteGradesForExam(1L);

        assertEquals(1, mockGradeDatabase.size());
        assertEquals(2L, mockGradeDatabase.get(0).getId());
    }

    @Test
    void testAddQuestionToExam() {
        examService.addQuestionToExam(1L, 100L);

        assertEquals(2, mockRecordDatabase.size());
        assertEquals("11", mockRecordDatabase.get(0).getExamKey());
        assertEquals("22", mockRecordDatabase.get(0).getQuestionKey());
    }

    @Test
    void testDeleteQuestionFromExam() {
        Record record = new Record();
        record.setId(1L);
        record.setExamKey("1");
        record.setQuestionKey("100");

        mockRecordDatabase.add(record);

        examService.deleteQuestionFromExam(1L);

        assertEquals(0, mockRecordDatabase.size());
    }
}
