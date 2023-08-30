package com.mindhub.dtos;

import com.mindhub.models.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;

    private String number;

    private LocalDate date;

    private Double balance;

    private Set<TransactionDTO> transactions;


    public AccountDTO (Account account){
        id= account.getId();
        number= account.getNumber();
        balance= account.getBalance();
        date= account.getCreationDate();
        transactions = account.getTransactions().stream()
                .map(element -> new TransactionDTO(element))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() { return number; }

    public Double getBalance() {
        return balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

}
