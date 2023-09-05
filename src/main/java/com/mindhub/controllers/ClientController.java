package com.mindhub.controllers;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.models.Account;
import com.mindhub.models.Client;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.CardRepository;
import com.mindhub.repositories.ClientRepository;

import com.mindhub.services.ClientService;
import com.mindhub.utils.AccountUtils;
import com.mindhub.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    ClientService clientService;

    //Servlet
    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();

        /*
        metodo resumido:
        clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());

        metodo desarrollado:
        List<Client> listClient = clientRepository.findAll();
        List<ClientDTO> listClientDTO = listClient.stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
        return listClientDTO;*/
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient (@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @RequestMapping(value = "/clients/current", method = RequestMethod.GET)
    public ClientDTO getCurrent ( Authentication authentication){
        return clientService.getCurrent(authentication.getName());
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Account account = null;
        do {

            String number = "VIN" + AccountUtils.getRandomNumberAccount(100000000,1000000);
            account= new Account(number,0.0,LocalDate.now());
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        String numberCard = CardUtils.getRandomNumberCard();

        Integer cvv = CardUtils.getRandomNumberCvv(0,999);

        Client clientRegistered = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRegistered.addAccount(account);

        clientService.saveClient(clientRegistered);
        accountRepository.save(account);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

