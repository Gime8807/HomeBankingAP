package com.mindhub.repositories;

import com.mindhub.models.Card;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface CardRepository extends JpaRepository<Card,Long> {
    boolean existsByNumber (String number);
}
