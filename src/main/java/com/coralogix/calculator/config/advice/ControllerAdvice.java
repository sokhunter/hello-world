package com.coralogix.calculator.config.advice;

import com.coralogix.calculator.config.exception.BusinessException;
import com.coralogix.calculator.controller.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(new ErrorResponseDTO(exception), exception.getHttpStatus());
    }
}
