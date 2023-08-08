package com.mindhub.controllers;

import com.mindhub.models.Client;
import com.mindhub.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    //Servlet
    @RequestMapping("/clients")
    public List<Client> getClients(){
        return clientRepository.findAll();
    }
}

