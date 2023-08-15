package com.mindhub.controllers;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.dtos.ClientLoanDTO;
import com.mindhub.models.Client;
import com.mindhub.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    //Servlet
    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
        /*List<Client> listClient = clientRepository.findAll();
        List<ClientDTO> listClientDTO = listClient.stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
        return listClientDTO;*/
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient (@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
}

