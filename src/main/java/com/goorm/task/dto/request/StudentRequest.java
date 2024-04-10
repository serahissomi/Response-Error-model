package com.goorm.task.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequest {

    @NotNull(message = "name 값 비어있음")
    private String name;

    @NotNull(message = "grade 값 비어있음")
    @Min(value = 1, message = "1보다 작은 값 사용불가")
    @Max(value = 5, message = "5보다 큰 값 사용불가")

    private String grade;
}
