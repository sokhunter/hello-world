package com.coralogix.calculator.controller.dto;

import lombok.Data;

@Data
public class ExchangeRateOutDTO {
    private String source;
    private String target;
    private Double price;
}
