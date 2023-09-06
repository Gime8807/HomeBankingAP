package com.mindhub.services.implement;

import com.mindhub.dtos.AccountDTO;
import com.mindhub.models.Account;
import com.mindhub.models.Client;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.utils.AccountUtils.getRandomNumberAccount;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }


    @Override
    public List<AccountDTO> getCurrentAccount(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName()).getAccounts().stream().
                map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createdAccount(Authentication authentication) {

        Client clientAuth =  clientRepository.findByEmail(authentication.getName());
        Account account = null;
        do {
            String number = "VIN" + getRandomNumberAccount(10000000,99999999);
            account= new Account(number,0.0, LocalDate.now());
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        clientAuth.addAccount(account);
        accountRepository.save(account);
    }

}
