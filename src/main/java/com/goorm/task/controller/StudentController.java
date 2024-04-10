package com.goorm.task.controller;

import com.goorm.task.domain.Student;
import com.goorm.task.dto.request.GradeRequest;
import com.goorm.task.dto.request.StudentRequest;
import com.goorm.task.dto.response.ApiResponse;
import com.goorm.task.dto.response.Metadata;
import com.goorm.task.dto.response.Status;
import com.goorm.task.exception.CustomException;
import com.goorm.task.exception.ErrorCode;
import com.goorm.task.service.StudentService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class StudentController {
    private final StudentService studentService;
    private final Metadata metadata;
    private final Status status;

    private Map<String, String> causeData;

    @Autowired
    public StudentController(StudentService studentService, Metadata metadata, Status status) {
        this.studentService = studentService;
        this.metadata = metadata;
        this.status = status;
    }

    @PostMapping("/create")
    @ResponseBody
    public ApiResponse<Student> createStudent(@RequestBody @Validated StudentRequest studentRequest,
                                              BindingResult bindingResult) {
        log.info(studentRequest.getGrade());
        log.info(studentRequest.getName());

        if (bindingResult.hasFieldErrors("name")) {
            causeData = Map.of("invalidName", "Name Not Found");
            throw new CustomException(ErrorCode.NAME_NOT_FOUND.getCode(),
            ErrorCode.NAME_NOT_FOUND.getErrorMessage(), causeData);
        }

        if (bindingResult.hasFieldErrors("grade")) {
            log.error("grade is {}", bindingResult.getFieldValue("grade"));

            if (StringUtils.isBlank(studentRequest.getGrade())) {
                causeData = Map.of("invalidGrade", "Grade Not Found");
                throw new CustomException(ErrorCode.GRADE_NOT_FOUND.getCode(),
                        ErrorCode.GRADE_NOT_FOUND.getErrorMessage(), causeData);
            }

            causeData = Map.of("invalidGrade", studentRequest.getGrade());
            throw new CustomException(ErrorCode.INVALID_GRADE.getCode(),
                    ErrorCode.INVALID_GRADE.getErrorMessage(), causeData);
        }

        Student createdStudent = studentService.create(studentRequest);
        return makeResponse(createdStudent);
    }

    @GetMapping("/check")
    public ApiResponse<Student> searchAllStudent() {
        List<Student> result = studentService.getAll();
        if (result.isEmpty()) {
            causeData = Map.of("invalidCommand", "Student Not Found");
            throw new CustomException(ErrorCode.EMPTY_STUDENT.getCode(),
                    ErrorCode.EMPTY_STUDENT.getErrorMessage(), causeData);
        }
        return makeResponse(result);
    }


    @PostMapping("/grade")
    @ResponseBody
    public ApiResponse<Student> searchTarderGradeStudent(@RequestBody @Validated GradeRequest gradeRequest,
                                                         BindingResult bindingResult) {
        log.info(String.valueOf(gradeRequest.getGrade()));

        if (bindingResult.hasFieldErrors("grade")) {
            log.error("grade is {}", bindingResult.getFieldValue("grade"));

            if(StringUtils.isBlank(String.valueOf(gradeRequest.getGrade()))) {
                causeData = Map.of("invalidGrade", "Grade Not Found");
                throw new CustomException(ErrorCode.GRADE_NOT_FOUND.getCode(),
                        ErrorCode.GRADE_NOT_FOUND.getErrorMessage(), causeData);
            }

            causeData = Map.of("invalidGrade", String.valueOf(gradeRequest.getGrade()));
            throw new CustomException(ErrorCode.INVALID_GRADE.getCode(),
                    ErrorCode.INVALID_GRADE.getErrorMessage(), causeData);
        }

        List<Student> result = studentService.getStudentByGrade(gradeRequest);
        if (result.isEmpty()) {
            causeData = Map.of("invalidCommand", "Student Not Found");
            throw new CustomException(ErrorCode.EMPTY_STUDENT.getCode(),
                    ErrorCode.EMPTY_STUDENT.getErrorMessage(), causeData);
        }
        return makeResponse(result);
    }

    private <T> ApiResponse<T> makeResponse(T result) {
        ApiResponse<T> response = new ApiResponse<>();
        List<T> results = new ArrayList<>();
        results.add(result);

        status.setCode(2001);
        status.setMessage("Created");
        response.setstatus(status);

        metadata.setResultCount(results.size());
        response.setMetadata(metadata);

        response.setResults(results);
        return response;
    }
}
