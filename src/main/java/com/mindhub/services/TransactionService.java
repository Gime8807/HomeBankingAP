package com.mindhub.services;

import com.mindhub.dtos.TransactionDTO;
import com.mindhub.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();

    Transaction getTransactionById (Long id);

    void createdTransaction (Transaction transaction);

}
