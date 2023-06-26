package com.coralogix.calculator.services.impl;

import com.coralogix.calculator.controller.dto.ExchangeRateInDTO;
import com.coralogix.calculator.controller.dto.ExchangeRateOutDTO;
import com.coralogix.calculator.model.ExchangeRateEntity;
import com.coralogix.calculator.model.OrderEntity;
import com.coralogix.calculator.repository.ExchangeRateProxy;
import com.coralogix.calculator.repository.ExchangeRateRepository;
import com.coralogix.calculator.services.ExchangeRateService;
import com.coralogix.calculator.util.mapper.ExchangeRateMapper;
import com.coralogix.calculator.util.mapper.ExchangeRateMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {
    private ExchangeRateService exchangeRateService;
    @Mock
    private ExchangeRateProxy exchangeRateProxy;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    private ExchangeRateMapper exchangeRateMapper;

    @BeforeEach
    void setUp() {
        exchangeRateMapper = new ExchangeRateMapperImpl();
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateProxy, exchangeRateRepository, exchangeRateMapper);
    }

    @Test
    void callClientService_successful_returnConversion() {
        ExchangeRateEntity exchangeRateEntity = getExchangeRateEntity();
        Mockito.when(exchangeRateProxy.getExchangeRateFromClient("USD", "PEN"))
                .thenReturn(Mono.just(exchangeRateEntity));

        OrderEntity orderEntity = exchangeRateMapper.toOrder(exchangeRateEntity);
        Mockito.when(exchangeRateRepository.save(orderEntity)).thenReturn(Mono.just(orderEntity));

        ExchangeRateInDTO exchangeRateInDTO = getExchangeRateInDTO();
        Mono<ExchangeRateOutDTO> conversion = exchangeRateService.conversion(exchangeRateInDTO);

        ExchangeRateOutDTO exchangeRateOutDTO = getExchangeRateOutDTO();
        StepVerifier.create(conversion).expectNext(exchangeRateOutDTO).verifyComplete();
    }

    private ExchangeRateEntity getExchangeRateEntity() {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        exchangeRateEntity.setBase("USD");
        exchangeRateEntity.setRates(Collections.singletonMap("PEN", 3.67));
        exchangeRateEntity.setSuccess(Boolean.TRUE);
        exchangeRateEntity.setDate("2023-06-26");
        exchangeRateEntity.setTimestamp(0L);
        return exchangeRateEntity;
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