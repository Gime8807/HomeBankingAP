package com.mindhub.repositories;


import com.mindhub.models.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoriesTest {
        @Autowired
        LoanRepository loanRepository;

        @Test
        public void existLoans(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans,is(not(empty())));

        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("PERSONAL LOAN"))));
        }

        @Test
        public void existMortgageLoan(){

            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("MORTGAGE LOAN"))));
        }
        @Test
        public void existCardLoan(){

            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("CARD LOAN"))));
        }

}
