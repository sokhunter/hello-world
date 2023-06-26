package com.coralogix.calculator.util.mapper;

import com.coralogix.calculator.controller.dto.ExchangeRateOutDTO;
import com.coralogix.calculator.model.ExchangeRateEntity;
import com.coralogix.calculator.model.OrderEntity;
import com.coralogix.calculator.util.DateUtil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {
    default OrderEntity toOrder(ExchangeRateEntity exchangeRateEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSource(exchangeRateEntity.getBase());
        orderEntity.setDate(DateUtil.stringToLocalDate(exchangeRateEntity.getDate()));
        exchangeRateEntity.getRates().forEach((target, price) -> {
            orderEntity.setTarget(target);
            orderEntity.setPrice(price);
        });
        return orderEntity;
    }

    ExchangeRateOutDTO toDTO(OrderEntity orderEntity);
}
