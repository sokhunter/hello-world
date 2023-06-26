package com.coralogix.calculator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Data
public class OrderEntity implements Persistable<Integer> {
    @Id
    private Integer id;
    private String source;
    private String target;
    private Double price;
    private LocalDate date;

    @Transient
    private boolean isNewOrder;

    @Override
    public boolean isNew() {
        return this.isNewOrder || this.id == null;
    }
}
