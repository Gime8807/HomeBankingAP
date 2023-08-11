package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Loan(){

    }

    public Loan (String name, Double maxAmount, List<String> payments){
        this.name= name;
        this.maxAmount= maxAmount;
        this.payments= payments;
    }

}
