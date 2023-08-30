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

    private String number;

    private LocalDate creationDate;

    private Double balance;

    //--Relaciones--//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "Account_id")
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();


    //--Constructores--//
    public Account(){}

    public Account(String number, Double balance, LocalDate creationDate) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    //-- Getters y Setters--//
    public Long getId() {
        return id;
    }

    public String getNumber() { return number;}
    public void setNumber(String number) {
        this.number = number;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    //--Metodos accesores --//
    public void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

}
