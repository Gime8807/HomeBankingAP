package com.mindhub.controllers;

import com.mindhub.dtos.CardDTO;
import com.mindhub.dtos.ClientDTO;
import com.mindhub.models.Card;
import com.mindhub.models.CardColor;
import com.mindhub.models.CardType;
import com.mindhub.models.Client;
import com.mindhub.repositories.CardRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.services.CardService;
import com.mindhub.services.ClientService;
import com.mindhub.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.utils.CardUtils.getRandomNumberCard;
import static com.mindhub.utils.CardUtils.getRandomNumberCvv;

@RestController
@RequestMapping ("/api")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/cards")
    public List<CardDTO> getCards(){
        return cardService.getCards();
    }

    @PostMapping ("/clients/current/cards")
    public  ResponseEntity<Object> createdCard (@RequestParam CardType cardType,@RequestParam CardColor cardColor,
    Authentication authentication){
        Client client = clientService.getCurrentClient(authentication.getName());
        List<Card> cardFiltered = client.getCards().stream().filter
                (card -> card.getType() == cardType && card.getColor() == cardColor).collect(Collectors.toList());

       if ((long) cardFiltered.size() ==1){
           return new ResponseEntity<>("Already this type of Card", HttpStatus.FORBIDDEN);
       }

        String numberCard;

        Integer cvv = getRandomNumberCvv(0,999);

        do{
            numberCard = getRandomNumberCard();
        }
        while (cardService.exitsCardByNumber(numberCard));

        Card newCard = new Card(client.getFirstName() + " "+ client.getLastName(),
                cardType, cardColor, numberCard,cvv, LocalDate.now(),LocalDate.now().plusYears(5));
        client.addCard(newCard);
        cardService.createdCard(newCard);

       return new ResponseEntity<> (HttpStatus.CREATED);
    }
}
