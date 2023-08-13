package com.mindhub.repositories;

import com.mindhub.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan,Long> {
}
