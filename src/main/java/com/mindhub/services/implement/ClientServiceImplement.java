package com.mindhub.services.implement;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.models.Account;
import com.mindhub.models.Client;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.services.ClientService;
import com.mindhub.utils.AccountUtils;
import com.mindhub.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }

    @Override
    public ClientDTO getCurrentClient (String email) {
        return new ClientDTO(clientRepository.findByEmail(email));
    }

    @Override
    public void saveClient(Client client) {

        Account account = null;
        do {

            String number = "VIN" + AccountUtils.getRandomNumberAccount(100000000,1000000);
            account= new Account(number,0.0, LocalDate.now());
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        String numberCard = CardUtils.getRandomNumberCard();

        Integer cvv = CardUtils.getRandomNumberCvv(0,999);

        accountRepository.save(account);
        clientRepository.save(client);
    }


}
