package com.goorm.task.advice;

import com.goorm.task.dto.response.ApiResponse;
import com.goorm.task.dto.response.Status;
import com.goorm.task.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestControllerAdvice(basePackages = "com.goorm.task.controller")
public class StudentControllerAdvice {

    @ExceptionHandler(value = {CustomException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<?> customExceptionHandler(final CustomException e) {
        ApiResponse<CustomException> response = new ApiResponse<>();

        Status status = new Status();
        status.setMessage(e.getMessage());
        status.setCode(e.getErrorCode());

        Map<String, String> cause = new HashMap<>();
        if (e.getData() != null && e.getData().containsKey("invalidGrade")) {
            cause.put("invalidGrade", e.getData().get("invalidGrade"));
        }

        if (e.getData() != null && e.getData().containsKey("invalidName")) {
            cause.put("invalidName", e.getData().get("invalidName"));
        }

        if (e.getData() != null && e.getData().containsKey("invalidCommand")) {
            cause.put("noSuchElement", e.getData().get("invalidCommand"));
        }

        Map<String, Map<String, String>> data = new HashMap<>();
        data.put("inputRestriction", cause);

        response.setstatus(status);
        response.setData(data);

        return response;
    }
}
