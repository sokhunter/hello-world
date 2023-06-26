package com.coralogix.calculator.model;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateEntity {
    private Boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;
}
