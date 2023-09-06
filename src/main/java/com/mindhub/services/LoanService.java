package com.mindhub.services;

import com.mindhub.dtos.LoanDTO;
import com.mindhub.models.Loan;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getLoans();

    Loan getLoanById (Long id);

    void createdLoans (Loan loan);

    boolean existsById(Long loanId);
}
