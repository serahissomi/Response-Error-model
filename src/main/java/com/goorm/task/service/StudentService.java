package com.goorm.task.service;

import com.goorm.task.domain.Student;
import com.goorm.task.repository.StudentRepository;
import com.goorm.task.dto.request.GradeRequest;
import com.goorm.task.dto.request.StudentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll() {
        List<Student> students = repository.findAll(Sort.by(Sort.Direction.ASC, "grade"));
        log.info("Found {} students", students.size());
        return students;
    }

    public Student create(StudentRequest newStudent) {
        Student student = Student.builder()
                .name(newStudent.getName())
                .grade(Integer.valueOf(newStudent.getGrade()))
                .build();
        log.info("Create Student: " + student);
        return repository.save(student);
    }

    public List<Student> getStudentByGrade(GradeRequest targetGrade) {
        GradeRequest target = GradeRequest.builder()
                .grade(targetGrade.getGrade())
                .build();

        List<Student> byGrade = repository.findByGrade(target.getGrade());
        log.info("target -> {} Found {} students", targetGrade, byGrade.size());
        return byGrade;
    }
}
