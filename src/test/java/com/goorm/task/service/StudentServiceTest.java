package com.goorm.task.service;

import com.goorm.task.domain.Student;
import com.goorm.task.dto.request.GradeRequest;
import com.goorm.task.dto.request.StudentRequest;
import com.goorm.task.repository.StudentRepository;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class StudentServiceTest {

    @Mock
    private final StudentRepository studentRepository;
    @InjectMocks
    private final StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        studentRepository.deleteAll();
    }

    @Autowired
    public StudentServiceTest (StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @Test
    @DisplayName("전부 가지고 오기")
    void getAll() {
        String name = "serah";
        String grade = "1";
        StudentRequest studentRequest = StudentRequest.builder()
                .name(name)
                .grade(grade)
                .build();
        studentService.create(studentRequest);

        String name2 = "lee";
        String grade2 = "3";
        StudentRequest studentRequest2 = StudentRequest.builder()
                .name(name2)
                .grade(grade2)
                .build();
        studentService.create(studentRequest2);

        List<Student> result = studentService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(name);
        assertThat(result.get(1).getName()).isEqualTo(name2);
    }

    @Test
    @DisplayName("학생 생성")
    void create() {
        String name = "serah";
        String grade = "1";
        StudentRequest studentRequest = StudentRequest.builder()
                .name(name)
                .grade(grade)
                .build();

        Student test = studentService.create(studentRequest);

        assertThat(test.getName()).isEqualTo(name);
        assertThat(test.getGrade()).isEqualTo(Integer.valueOf(grade));
    }

    @Test
    @DisplayName("타켓 성적 가져오기")
    void getByGrade() {
        String name = "serah";
        String grade = "1";
        StudentRequest studentRequest = StudentRequest.builder()
                .name(name)
                .grade(grade)
                .build();
        studentService.create(studentRequest);

        String name2 = "lee";
        String grade2 = "3";
        StudentRequest studentRequest2 = StudentRequest.builder()
                .name(name2)
                .grade(grade2)
                .build();
        studentService.create(studentRequest2);

        String name3 = "john";
        String grade3 = "4";
        StudentRequest studentRequest3 = StudentRequest.builder()
                .name(name3)
                .grade(grade3)
                .build();
        studentService.create(studentRequest3);

        GradeRequest gradeRequest = GradeRequest.builder()
                .grade(4)
                .build();

        List<Student> result = studentService.getStudentByGrade(gradeRequest);

        assertThat(result).hasSize(2);
    }
}
