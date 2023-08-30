package com.mindhub.controllers;

import com.mindhub.dtos.AccountDTO;
import com.mindhub.models.Account;
import com.mindhub.models.Client;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.utils.AccountUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.utils.AccountUtils.getRandomNumberAccount;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    //Servlet
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());

        /*List<Account> listAccount = accountRepository.findAll();
        List<AccountDTO> listAccountDTO = listAccount.stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
        return listAccountDTO;*/
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return  new AccountDTO(accountRepository.findById(id).orElse(null));
    }



    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createdAccount (Authentication authentication){
            Client clientAuth =  clientRepository.findByEmail(authentication.getName());
        if (clientAuth.getAccounts().stream().count()==3){
            System.out.println("tiene 3 cuentas, alcanzo el maximo");
            return new ResponseEntity<>("Already max number accounts", HttpStatus.FORBIDDEN);
        }

        Account account = null;
        do {
            String number = "VIN" + getRandomNumberAccount(10000000,99999999);
            account= new Account(number,0.0,LocalDate.now());
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        clientAuth.addAccount(account);
        accountRepository.save(account);
        System.out.println("creaste una cuenta");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
