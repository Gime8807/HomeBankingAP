package com.mindhub;

import com.mindhub.models.Account;
import com.mindhub.models.Client;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");

			clientRepository.save(client1);

			Account account1= new Account(client1, "VIN001", 5000.0);
			client1.addAccount(account1);

			LocalDate today= LocalDate.now();

			Account account2= new Account(client1, "VIN002",7500.0);

			account2.setCreationDate(today.plusDays(1));

			accountRepository.save(account1);
			accountRepository.save(account2);



		});
	}
}
