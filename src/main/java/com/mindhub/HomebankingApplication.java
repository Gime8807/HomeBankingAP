package com.mindhub;

import com.mindhub.models.*;
import com.mindhub.repositories.*;
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
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
		return args -> {

			//--CLIENTS--//
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Gimena","Sasso","gimesasso@gmail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);

			//--ACCOUNTS--//

			Account account1= new Account("VIN001", 5000.0);
			client1.addAccount(account1);

			LocalDate today= LocalDate.now();

			Account account2= new Account("VIN002",7500.0);
			account2.setCreationDate(today.plusDays(1));
			client1.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);

			//TRANSACTIONS--//

			Transaction transaction1= new Transaction(500.0, "transfer of mom", TransactionType.CREDIT);
			Transaction transaction2= new Transaction(-250.25, "dinner mc donals", TransactionType.DEBIT);
			Transaction transaction3= new Transaction(-360.5, "charge bus card", TransactionType.DEBIT);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

			//--LOANS--//
			Loan loan1 = new Loan("MORTGAGE LOAN",500000.0, List.of(12,24, 36,48,60));
			Loan loan2= new Loan("PERSONAL LOAN", 100000.0,List.of(6,12,24));
			Loan loan3= new Loan("CARD LOAN",300000.0,List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.0,60,client1,loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12,client1,loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000.0,24,client2,loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000.0,36, client2,loan3);

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);

			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);

			client2.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);

			client2.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
		};
	}
}
