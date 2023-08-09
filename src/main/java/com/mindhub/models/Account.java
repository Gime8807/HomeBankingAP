package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<TransactionType> type = new HashSet<>();
    private String number;

    private LocalDate creationDate;

    private LocalDateTime localDateTime;

    private Double balance;

    public Account(){

    }
    public Account(String number, Double balance) {
        this.number = number;
        this.balance = balance;
        this.creationDate = LocalDate.now();
        this.localDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }


    public String getNumber() {
        return number;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }


    public Double getBalance() {
        return balance;
    }

    public LocalDateTime getLocalDateTime(LocalDate localDate) {
        return localDateTime;
    }

    public void setClient(Client client) {

    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<TransactionType> getType() {
        return type;
    }

    public void addTransactionType(TransactionType type){
        type.setTransactionType(this);
        this.type.add(type);
    }
}
