package com.goorm.task.dto.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Status {
    private int code;
    private String message;
}

