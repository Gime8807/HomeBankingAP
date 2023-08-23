package com.mindhub.controllers;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.dtos.ClientLoanDTO;
import com.mindhub.models.Client;
import com.mindhub.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @RequestMapping(value = "/clients/current", method = RequestMethod.GET)
    public ClientDTO getCurrent ( Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }


    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

