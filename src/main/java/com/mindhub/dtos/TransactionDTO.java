package com.mindhub.dtos;

import com.mindhub.models.Transaction;
import com.mindhub.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private TransactionType type;

    private Double amount;

    private String description;

    private LocalDateTime date;

    public TransactionDTO (Transaction transaction){
        type=transaction.getType();
        amount=transaction.getAmount();
        description= transaction.getDescription();
        date=transaction.getLocalDateTime();
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

    public LocalDateTime getDate() {
        return date;
    }
}
