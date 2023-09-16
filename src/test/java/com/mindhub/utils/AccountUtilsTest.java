package com.mindhub.utils;

import com.mindhub.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class AccountUtilsTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void accountNumberIsCreated() {
        int accountNumber = AccountUtils.getRandomNumberAccount(10000000,99999999);
        assertThat(accountNumber,is(not(nullValue())));
    }
}