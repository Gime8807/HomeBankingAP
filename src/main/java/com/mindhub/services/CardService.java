package com.mindhub.services;

import com.mindhub.dtos.CardDTO;
import com.mindhub.models.Card;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    List<CardDTO> getCards();

    boolean exitsCardByNumber (String number);

    public void createdCard (Card card);

}
