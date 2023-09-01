package com.mindhub.controllers;

import com.mindhub.dtos.CardDTO;
import com.mindhub.dtos.TransactionDTO;
import com.mindhub.models.*;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }
    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransactions (@PathVariable Long id){
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }

    @Transactional
    @RequestMapping (value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction (
            @RequestParam (defaultValue = "0") Double amount, @RequestParam String description,
            @RequestParam String numberAccountOrigin,@RequestParam String numberAccountDestination,
                                               Authentication authentication) {

        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Account accountAuthOrigin = accountRepository.findByNumber(numberAccountOrigin);
        Account accountAuthDestination = accountRepository.findByNumber(numberAccountDestination);

        //Verificar que los parámetros no estén vacíos

        if (amount <= 0.0 || description.isBlank() || numberAccountOrigin.isBlank() || numberAccountDestination.isBlank()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }
        // Verificar que los números de cuenta no sean iguales
        if (accountAuthOrigin==accountAuthDestination) {
            return new ResponseEntity<>("You are not allowed to perform this operation", HttpStatus.FORBIDDEN);
        }
        //Verificar que exista la cuenta de origen
        if (!accountRepository.existsByNumber(numberAccountOrigin)) {
            return new ResponseEntity<>("Account origin don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de origen pertenezca al cliente autenticado
        if (!clientAuth.getAccounts().contains(accountAuthOrigin)) {
            return new ResponseEntity<>(" ", HttpStatus.FORBIDDEN);
        }
        //Verificar que exista la cuenta de destino
        if (!accountRepository.existsByNumber(numberAccountDestination)) {
            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de origen tenga el monto disponible.
        if (accountAuthOrigin.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }

        //Creacion de tipos de transacciones

        Transaction transactionDebit = new Transaction(amount, description, TransactionType.DEBIT);
        Transaction transactionCredit = new Transaction(amount, description, TransactionType.CREDIT);

        //Seteamos los balances de ambas transacciones
        accountAuthOrigin.setBalance(accountAuthOrigin.getBalance() - amount);
        accountAuthDestination.setBalance(accountAuthDestination.getBalance() + amount);

        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);

        accountAuthOrigin.addTransaction(transactionDebit);
        accountAuthDestination.addTransaction(transactionCredit);

        accountRepository.save(accountAuthOrigin);
        accountRepository.save(accountAuthDestination);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}





