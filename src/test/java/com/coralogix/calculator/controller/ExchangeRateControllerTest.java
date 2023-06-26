package com.coralogix.calculator.controller;

import com.coralogix.calculator.config.R2dbcConfig;
import com.coralogix.calculator.config.exception.BusinessException;
import com.coralogix.calculator.controller.dto.ExchangeRateInDTO;
import com.coralogix.calculator.controller.dto.ExchangeRateOutDTO;
import com.coralogix.calculator.services.ExchangeRateService;
import com.coralogix.calculator.util.enums.ExchangeRateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ExchangeRateController.class)
@Import(R2dbcConfig.class)
@AutoConfigureWebTestClient
class ExchangeRateControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ExchangeRateService exchangeRateService;

    private final String EXECUTE_CONVERSION_PATH = "/exchange-rate/conversion";

    @Test
    void executeConversion_completed_returnConversion() {
        ExchangeRateInDTO exchangeRateInDTO = getExchangeRateInDTO();
        ExchangeRateOutDTO exchangeRateOutDTO = getExchangeRateOutDTO();
        Mockito.when(exchangeRateService.conversion(exchangeRateInDTO)).thenReturn(Mono.just(exchangeRateOutDTO));

        webTestClient
                .post()
                .uri(EXECUTE_CONVERSION_PATH)
                .bodyValue(exchangeRateInDTO)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(ExchangeRateOutDTO.class)
                .consumeWith(response -> {
                    ExchangeRateOutDTO responseBody = response.getResponseBody();
                    Assertions.assertEquals(3.67, responseBody.getPrice());
                });
    }

    @Test
    void executeConversion_failure_returnBusinessException() {
        ExchangeRateInDTO exchangeRateInDTO = getExchangeRateInDTO();
        Mockito.when(exchangeRateService.conversion(exchangeRateInDTO))
                .thenReturn(Mono.error(new BusinessException(ExchangeRateResponse.INSERT_ERROR)));

        webTestClient
                .post()
                .uri(EXECUTE_CONVERSION_PATH)
                .bodyValue(exchangeRateInDTO)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(BusinessException.class)
                .consumeWith(response -> {
                    BusinessException responseBody = response.getResponseBody();
                    Assertions.assertEquals(ExchangeRateResponse.INSERT_ERROR.getMessage(), responseBody.getMessage());
                });
    }

    private ExchangeRateInDTO getExchangeRateInDTO() {
        ExchangeRateInDTO exchangeRateInDTO = new ExchangeRateInDTO();
        exchangeRateInDTO.setSource("USD");
        exchangeRateInDTO.setTarget("PEN");
        return exchangeRateInDTO;
    }

    private ExchangeRateOutDTO getExchangeRateOutDTO() {
        ExchangeRateOutDTO exchangeRateOutDTO = new ExchangeRateOutDTO();
        exchangeRateOutDTO.setSource("USD");
        exchangeRateOutDTO.setTarget("PEN");
        exchangeRateOutDTO.setPrice(3.67);
        return exchangeRateOutDTO;
    }
}