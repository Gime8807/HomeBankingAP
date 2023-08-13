package com.mindhub.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> loans = new HashSet<>();

    public Client(){

    }

    public Client(String firstName, String lastName, String mail){
        this.firstName= firstName;
        this.lastName= lastName;
        this.mail= mail;
    }

    public Long getId() {
        return id;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.loans.add(clientLoan);
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<ClientLoan> getClientLoans() {
        return loans;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }


    public List<Loan> getLoans (){
        return loans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }
}
