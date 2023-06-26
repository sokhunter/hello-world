package com.coralogix.calculator.controller;

import com.coralogix.calculator.controller.dto.ExchangeRateInDTO;
import com.coralogix.calculator.controller.dto.ExchangeRateOutDTO;
import com.coralogix.calculator.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @PostMapping(value = "/conversion", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> executeConversion(@RequestBody ExchangeRateInDTO exchangeRateInDTO) {
        return exchangeRateService.conversion(exchangeRateInDTO).map(ResponseEntity::ok);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> findAll() {
        return exchangeRateService.findAll();
    }
}
