package com.mindhub.services.implement;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.models.Client;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }

    @Override
    public ClientDTO getCurrent(String email) {
        return new ClientDTO(clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
         clientRepository.save(client);
    }


}
