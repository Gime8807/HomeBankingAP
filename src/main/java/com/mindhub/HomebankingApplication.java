package com.mindhub;

import com.mindhub.models.*;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.repositories.LoanRepository;
import com.mindhub.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
	LoanRepository loanRepository){
		return args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");

			clientRepository.save(client1);

			Account account1= new Account("VIN001", 5000.0);
			client1.addAccount(account1);

			LocalDate today= LocalDate.now();

			Account account2= new Account("VIN002",7500.0);
			account2.setCreationDate(today.plusDays(1));
			client1.addAccount(account2);

			Transaction transaction1= new Transaction(500.0, "transfer of mom", TransactionType.CREDIT);
			Transaction transaction2= new Transaction(-250.25, "dinner mc donals", TransactionType.DEBIT);
			Transaction transaction3= new Transaction(-360.5, "charge bus card", TransactionType.DEBIT);

			accountRepository.save(account1);
			accountRepository.save(account2);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

			Loan loan1 = new Loan("MORTGAGE LOAN",500000.0, List.of("12","24", "36","48","60"));
			Loan loan2= new Loan("PERSONAL LOAN", 100000.0,List.of("6","12","24"));
			Loan loan3= new Loan("CARD LOAN",300000.0,List.of("6","12","24","36"));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

		};
	}
}
