package com.coralogix.calculator.services;

import com.coralogix.calculator.controller.dto.ExchangeRateInDTO;
import com.coralogix.calculator.controller.dto.ExchangeRateOutDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {
    Mono<ExchangeRateOutDTO> conversion(ExchangeRateInDTO exchangeRateInDTO);
    Flux<ExchangeRateOutDTO> findAll();
}
