package com.mindhub;

import com.mindhub.models.*;
import com.mindhub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
		return args -> {

			//--CLIENTS--//

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com",
					passwordEncoder.encode("ads12345"));
			Client client2 = new Client("Gimena","Sasso","gimesasso@gmail.com",
					passwordEncoder.encode("bvc12345"));
			Client client3 = new Client("pablo","mendez","pmendez@admin.com",
					passwordEncoder.encode("12345"));

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			//--ACCOUNTS--//

			Account account1= new Account("VIN001", 5000.0,LocalDate.now());
			client1.addAccount(account1);



			Account account2= new Account("VIN002",7500.0, LocalDate.now());
			account2.setCreationDate(LocalDate.now().plusDays(1));
			client1.addAccount(account2);

			//--TRANSACTIONS--//

			Transaction transaction1= new Transaction(500.0, "transfer of mom", TransactionType.CREDIT,LocalDateTime.now());
			Transaction transaction2= new Transaction(-250.25, "dinner mc donals", TransactionType.DEBIT,LocalDateTime.now());
			Transaction transaction3= new Transaction(-360.5, "charge bus card", TransactionType.DEBIT,LocalDateTime.now());

			accountRepository.save(account1);
			accountRepository.save(account2);

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

			ClientLoan clientLoan1 = new ClientLoan(400000.0,60);
			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12);
			ClientLoan clientLoan3 = new ClientLoan(100000.0,24);
			ClientLoan clientLoan4 = new ClientLoan(200000.0,36);

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

			//--CARDS--//

			Card card1= new Card();
			Card card2= new Card();
			Card card3 = new Card();

			card1.setCardHolder(client1.getFirstName().toUpperCase() +" "+ client1.getLastName().toUpperCase());
			card1.setType(CardType.DEBIT);
			card1.setColor(CardColor.GOLD);
			card1.setNumber("4546 8688 7912 3625");
			card1.setCvv(601);
			card1.setFromDate(LocalDate.now());
			card1.setThruDate(LocalDate.now().plusYears(5));

			card2.setCardHolder(client1.getFirstName().toUpperCase() +" "+ client1.getLastName().toUpperCase());
			card2.setType(CardType.CREDIT);
			card2.setColor(CardColor.TITANIUM);
			card2.setNumber("4547 5555 3210 9991");
			card2.setCvv(801);
			card2.setFromDate(LocalDate.now());
			card2.setThruDate(LocalDate.now().plusYears(5));

			card3.setCardHolder(client2.getFirstName().toUpperCase() +" "+ client2.getLastName().toUpperCase());
			card3.setType(CardType.DEBIT);
			card3.setColor(CardColor.SILVER);
			card3.setNumber("4546 3333 6532 2415");
			card3.setCvv(701);
			card3.setFromDate(LocalDate.now());
			card3.setThruDate(LocalDate.now().plusYears(5));


			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		};
	}
}
