package com.mindhub.services;

import com.mindhub.dtos.AccountDTO;
import com.mindhub.models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccounts();

    AccountDTO getAccountById (Long id);

    List<AccountDTO> getCurrentAccount (Authentication authentication);

    void createdAccount (Authentication authentication);


}
