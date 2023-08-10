package com.mindhub.dtos;

import com.mindhub.models.Transaction;
import com.mindhub.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private TransactionType type;

    private Double amount;

    private String description;

    private LocalDateTime localDateTime;

    public TransactionDTO (Transaction transaction){
        id= transaction.getId();
        type=transaction.getType();
        amount=transaction.getAmount();
        description= transaction.getDescription();
        localDateTime=transaction.getLocalDateTime();
    }

    public Long getId() {
        return id;
    }
    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
