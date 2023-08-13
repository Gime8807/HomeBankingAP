package com.mindhub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private String name;

    private Double amount;

    private Integer payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="client_id")
    private Client client;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan(){
    }
    public ClientLoan (String name, Double amount, Integer payments, Client client, Loan loan){
        this.name =name;
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
