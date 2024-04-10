package com.goorm.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.task.repository.StudentRepository;
import com.goorm.task.service.StudentService;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class StudentControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentControllerTest(StudentService studentService, StudentRepository studentRepository,
                                 MockMvc mockMvc, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @After
    public void tearDown() throws Exception {
        studentRepository.deleteAll();
    }

    @Test
    public void createStudentTest() throws Exception {

    }


}
