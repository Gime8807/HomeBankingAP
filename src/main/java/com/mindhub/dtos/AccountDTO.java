package com.mindhub.dtos;

import com.mindhub.models.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountDTO {
    private Long id;

    private String number;

    private LocalDate date;


    private Double balance;

    public AccountDTO (Account account){
        id= account.getId();
        number= account.getNumber();
        balance= account.getBalance();
        date= account.getCreationDate();

    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDate getDate() {
        return date;
    }
}
