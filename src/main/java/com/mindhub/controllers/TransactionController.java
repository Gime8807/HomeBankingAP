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
import java.time.LocalDateTime;
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
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String fromAccountNumber ,@RequestParam String toAccountNumber,
                                               Authentication authentication) {

        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Account fromAccountAuth = accountRepository.findByNumber(fromAccountNumber);
        Account toAccountAuth = accountRepository.findByNumber(toAccountNumber);

        //Verificar que los parámetros no estén vacíos

        if (amount.isNaN() || description.isBlank() || fromAccountNumber.isBlank() || toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.BAD_REQUEST);
        }
        // Verificar que los números de cuenta no sean iguales
        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("You are not allowed to perform this operation", HttpStatus.FORBIDDEN);
        }
        //Verificar que exista la cuenta de origen
        if (!accountRepository.existsByNumber(fromAccountNumber)) {
            return new ResponseEntity<>("Account origin don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de origen pertenezca al cliente autenticado
        if (!clientAuth.getAccounts().equals(fromAccountAuth)) {
            return new ResponseEntity<>(" ", HttpStatus.FORBIDDEN);
        }
        //Verificar que exista la cuenta de destino
        if (!accountRepository.existsByNumber(toAccountNumber)) {
            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de origen tenga el monto disponible.
        if (fromAccountAuth.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }

        //Creacion de tipos de transacciones

        Transaction transactionDebit = new Transaction(-amount, description + "DEBIT - " + fromAccountNumber,
                TransactionType.DEBIT, LocalDateTime.now());
        Transaction transactionCredit = new Transaction(amount, description + "CREDIT " + toAccountNumber,
                TransactionType.CREDIT,LocalDateTime.now());

        //Seteamos los balances de ambas transacciones
        fromAccountAuth.setBalance(fromAccountAuth.getBalance() - amount);
        toAccountAuth.setBalance(toAccountAuth.getBalance() + amount);

        fromAccountAuth.addTransaction(transactionDebit);
        toAccountAuth.addTransaction(transactionCredit);

        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);

        accountRepository.save(fromAccountAuth);
        accountRepository.save(toAccountAuth);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}





