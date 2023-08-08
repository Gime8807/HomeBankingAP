package com.mindhub.dtos;

import com.mindhub.models.Account;
import com.mindhub.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String mail;

    private Set<AccountDTO> accounts;

    public ClientDTO (Client client){
        id = client.getId();
        firstName= client.getFirstName();
        lastName= client.getLastName();
        mail= client.getMail();
        accounts= client.getAccounts().stream()
                .map(element -> new AccountDTO(element))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
