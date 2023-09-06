package com.mindhub.controllers;

import com.mindhub.dtos.LoanApplicationDTO;
import com.mindhub.dtos.LoanDTO;
import com.mindhub.models.*;
import com.mindhub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanApplicationController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping (value = "/loans",method = RequestMethod.POST)
    ResponseEntity <Object> createdLoans (@RequestBody LoanApplicationDTO loanApplicationDTO,
                                      Authentication authentication){

        Long loanId = loanApplicationDTO.getLoanId();
        Double amount = loanApplicationDTO.getAmount();
        Integer payments = loanApplicationDTO.getPayments();
        String toAccountNumber = loanApplicationDTO.getToAccountNumber();

        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Account accountDestination = accountRepository.findByNumber(toAccountNumber);
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        //Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if (loanId == null) {
            return new ResponseEntity<>("Missing data,loan is required", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("Amount ", HttpStatus.FORBIDDEN);
        }
        if (payments <=0){
            return new ResponseEntity<>("Payments",HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isBlank()){
            return new ResponseEntity<>("Missing Data, destination account is required",HttpStatus.FORBIDDEN);
        }

        //Verificar que el préstamo exista
        if (!loanRepository.existsById(loanId)) {
            return new ResponseEntity<>("This Loan don't exists",HttpStatus.FORBIDDEN);
        }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if(amount > loanRepository.findById(loanId).get().getMaxAmount()){
            return new ResponseEntity<>("This amount is ",HttpStatus.FORBIDDEN);
        }
        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        if(loan.getPayments().equals(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("This numbers of payments is not available",HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista
        if(!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("This destination account don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if(!clientAuth.getAccounts().contains(accountDestination)){
            System.out.println(clientAuth.getAccounts());
            return new ResponseEntity<>("This Account don't belong to authentication client ",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()+ amount*0.2, loanApplicationDTO.getPayments());
        Transaction transactionLoan = new Transaction(amount,  "Loan approved " + toAccountNumber,
                TransactionType.CREDIT, LocalDateTime.now());
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transactionLoan);

        loan.addClientLoan(clientLoan);
        clientAuth.addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transactionLoan);
        accountRepository.save(accountDestination);


        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
        //Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo) con la
        // descripción concatenando el nombre del préstamo y la frase “loan approved”
        //Se debe actualizar la cuenta de destino sumando el monto solicitado.
        return new ResponseEntity<>("Loan approved",HttpStatus.CREATED);


    }

}
