package com.mindhub.dtos;

import com.mindhub.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;

    private String name;

    private Double amount;

    private Integer payments;

    private Long idLoan;


    public ClientLoanDTO (ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.name= clientLoan.getName();
        this.amount = clientLoan.getAmount();
        this.payments = Integer.valueOf(clientLoan.getPayments());
        this.idLoan = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public Long getIdLoan() {
        return idLoan;
    }
}
