package com.mindhub.repositories;

import com.mindhub.models.Account;
import com.mindhub.models.Client;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository <Account,Long> {
    boolean existsByNumber (String number);

    Account findByNumber (String number);
}
