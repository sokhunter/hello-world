package com.coralogix.calculator.repository;

import com.coralogix.calculator.model.OrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends R2dbcRepository<OrderEntity, Integer> {
}
