package com.mindhub.services;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.models.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();

    ClientDTO getClientById ( Long id);

    ClientDTO getCurrentClient (String email);

    void saveClient (Client client);
}
