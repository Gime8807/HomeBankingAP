package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double amount;

    private String description;

    private LocalDateTime date;
    private TransactionType type;

    //--Relaciones--//
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    //--Constructores--//
    public Transaction(){
    }
    public Transaction(Double amount, String description, TransactionType type,LocalDateTime date){
    this.amount= amount;
    this.description= description;
    this.date= date;
    this.type= type;
    }

    //--Getters y Setters--//
    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.date = localDateTime;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
