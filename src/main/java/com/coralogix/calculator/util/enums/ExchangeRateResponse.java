package com.coralogix.calculator.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExchangeRateResponse {
    INVOKING_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error al invocar el servicio cliente"),
    CURRENCY_NOT_FOUND(HttpStatus.BAD_REQUEST, "No se encontró el tipo de cambio"),
    INSERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "No se insertó el registro en la base de datos");

    private final HttpStatus httpStatus;
    private final String message;
}
