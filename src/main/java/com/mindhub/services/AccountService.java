package com.mindhub.services;

import com.mindhub.dtos.AccountDTO;
import com.mindhub.models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccounts();

    Account getAccountById (Long id);

    Account findByNumber (String number);

    boolean existsByNumber (String number);

    List<AccountDTO> getCurrentAccount (Authentication authentication);

    void createdAccount (Account account);


}
