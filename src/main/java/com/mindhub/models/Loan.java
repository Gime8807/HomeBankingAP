package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private String name;

    private Double maxAmount;
    @ElementCollection
    private List<String> payments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loan")
    private Set<ClientLoan> clients = new HashSet<>();

    public Loan(){

    }

    public Loan (String name, Double maxAmount, List<String> payments){
        this.name= name;
        this.maxAmount= maxAmount;
        this.payments= payments;
    }

    public Long getId() {
        return id;
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setLoan(this);
        this.clients.add(clientLoan);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<String> getPayments() {
        return payments;
    }

    public void setPayments(List<String> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClients() {
        return clients;
    }
}
