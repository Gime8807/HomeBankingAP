package com.mindhub.controllers;

import com.mindhub.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

}
