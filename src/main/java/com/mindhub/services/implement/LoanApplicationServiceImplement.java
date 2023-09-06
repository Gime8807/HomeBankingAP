package com.mindhub.services.implement;

import com.mindhub.dtos.LoanDTO;
import com.mindhub.models.Loan;
import com.mindhub.repositories.*;
import com.mindhub.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApplicationServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void createdLoans(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public boolean existsById(Long loanId) {
        return loanRepository.existsById(loanId);
    }
}
