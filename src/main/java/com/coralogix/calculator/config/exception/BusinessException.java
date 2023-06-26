package com.coralogix.calculator.config.exception;

import com.coralogix.calculator.util.enums.ExchangeRateResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime date;

    public BusinessException(ExchangeRateResponse response) {
        this.httpStatus = response.getHttpStatus();
        this.message = response.getMessage();
        this.date = LocalDateTime.now();
    }
}
