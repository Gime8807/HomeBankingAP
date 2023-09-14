package com.mindhub.controllers;

import com.mindhub.dtos.CardDTO;
import com.mindhub.dtos.TransactionDTO;
import com.mindhub.models.*;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.repositories.TransactionRepository;
import com.mindhub.services.AccountService;
import com.mindhub.services.ClientService;
import com.mindhub.services.TransactionService;
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
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransactions();
    }
    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransactions (@PathVariable Long id){
        return new TransactionDTO(transactionService.getTransactionById(id));
    }

    @Transactional
    @PostMapping ("/transactions")
    public ResponseEntity<Object> createdTransaction (
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String fromAccountNumber ,@RequestParam String toAccountNumber,
                                               Authentication authentication) {

        Client clientAuth = clientService.getCurrentClient(authentication.getName());
        Account accountSource = accountService.findByNumber(fromAccountNumber);
        Account accountDestination = accountService.findByNumber(toAccountNumber);

        //Verificar que los parámetros no estén vacíos
        if (amount <=0) {
            return new ResponseEntity<>("Missing Data, amount is required", HttpStatus.FORBIDDEN);
        }
        if (description.isBlank()) {
            return new ResponseEntity<>("Missing Data, description is required", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, source account is required", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, destination account is required", HttpStatus.FORBIDDEN);
        }
        // Verificar que los números de cuenta no sean iguales

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("This operation is prohibited", HttpStatus.FORBIDDEN);
        }
        //Verificar que exista la cuenta de origen

        if (!clientAuth.getAccounts().contains(fromAccountNumber)) {
            return new ResponseEntity<>("Source account don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de origen pertenezca al cliente autenticado
        if (!clientAuth.getAccounts().contains(accountSource)) {
            return new ResponseEntity<>("The source account does not belong to the authenticated client ", HttpStatus.FORBIDDEN);
        }

        //Verificar que exista la cuenta de destino
        if (!accountService.existsByNumber(toAccountNumber)) {
            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de origen tenga el monto disponible.
        if (accountSource.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }

        //Creacion de tipos de transacciones

        //Debit Transaction
        Transaction transactionDebit = new Transaction(-amount, description + " DEBIT - " + fromAccountNumber,
                TransactionType.DEBIT, LocalDateTime.now());
        accountSource.addTransaction(transactionDebit);
        accountSource.setBalance(accountSource.getBalance() - amount);
        transactionService.createdTransaction(transactionDebit);
        accountService.createdAccount(accountSource);

        // Credit Transaction
        Transaction transactionCredit = new Transaction(amount, description + " CREDIT " + toAccountNumber,
                TransactionType.CREDIT,LocalDateTime.now());
       accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transactionCredit);
        transactionService.createdTransaction(transactionCredit);
        accountService.createdAccount(accountDestination);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}





