package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn("client_id")
    private Client client;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn("loan_id")
    private Loan loan;

    public ClientLoan(){

    }
    public ClientLoan (Client client, Loan loan){
        this.client = client;
        this.loan = loan;
    }

    public Long getId() {
        return id;
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
