package com.mindhub.services.implement;

import com.mindhub.dtos.CardDTO;
import com.mindhub.models.Card;
import com.mindhub.models.Client;
import com.mindhub.repositories.CardRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.utils.CardUtils.getRandomNumberCard;
import static com.mindhub.utils.CardUtils.getRandomNumberCvv;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean exitsCardByNumber(String number) {
        return cardRepository.existsByNumber(number);
    }

    @Override
    public void createdCard(Card card) {
        cardRepository.save(card);
    }


}
