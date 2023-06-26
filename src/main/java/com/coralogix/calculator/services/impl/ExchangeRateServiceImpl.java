package com.coralogix.calculator.services.impl;

import com.coralogix.calculator.config.exception.BusinessException;
import com.coralogix.calculator.controller.dto.ExchangeRateInDTO;
import com.coralogix.calculator.controller.dto.ExchangeRateOutDTO;
import com.coralogix.calculator.model.ExchangeRateEntity;
import com.coralogix.calculator.model.OrderEntity;
import com.coralogix.calculator.repository.ExchangeRateProxy;
import com.coralogix.calculator.repository.ExchangeRateRepository;
import com.coralogix.calculator.services.ExchangeRateService;
import com.coralogix.calculator.util.enums.ExchangeRateResponse;
import com.coralogix.calculator.util.mapper.ExchangeRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateProxy exchangeRateProxy;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    public Mono<ExchangeRateOutDTO> conversion(ExchangeRateInDTO exchangeRateInDTO) {
        Mono<ExchangeRateEntity> exchangeRateEntityMono = exchangeRateProxy.getExchangeRateFromClient(
                exchangeRateInDTO.getSource(), exchangeRateInDTO.getTarget());
        return exchangeRateEntityMono.flatMap(exchangeRateEntity -> {
            if (exchangeRateEntity.getRates().isEmpty()) {
                return Mono.error(new BusinessException(ExchangeRateResponse.CURRENCY_NOT_FOUND));
            }
            OrderEntity orderEntity = exchangeRateMapper.toOrder(exchangeRateEntity);
            return exchangeRateRepository.save(orderEntity)
                    .map(exchangeRateMapper::toDTO)
                    .switchIfEmpty(Mono.error(new BusinessException(ExchangeRateResponse.INSERT_ERROR)));
        });
    }

    @Override
    public Flux<ExchangeRateOutDTO> findAll() {
        return exchangeRateRepository.findAll()
                .map(exchangeRateMapper::toDTO);
    }
}
