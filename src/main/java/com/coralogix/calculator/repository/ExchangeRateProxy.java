package com.coralogix.calculator.repository;

import com.coralogix.calculator.model.ExchangeRateEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "exchange-rate-service-client", url = "${client.exchange-rate.url}")
public interface ExchangeRateProxy {
    @GetMapping("/fixer/latest")
    Mono<ExchangeRateEntity> getExchangeRateFromClient(@RequestParam String base, @RequestParam String symbols);
}
