package com.coralogix.calculator.controller.dto;

import com.coralogix.calculator.config.exception.BusinessException;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime date;

    public ErrorResponseDTO(BusinessException exception) {
        this.httpStatus = exception.getHttpStatus();
        this.message = exception.getMessage();
        this.date = LocalDateTime.now();
    }
}
