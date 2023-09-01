package com.mindhub.controllers;

import com.mindhub.dtos.CardDTO;
import com.mindhub.models.Card;
import com.mindhub.models.CardColor;
import com.mindhub.models.CardType;
import com.mindhub.models.Client;
import com.mindhub.repositories.CardRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }
    @RequestMapping("/cards/{id}")
    public CardDTO getCard (@PathVariable Long id){
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }

    @RequestMapping (value = "/clients/current/cards",method = RequestMethod.POST)
    public  ResponseEntity<Object> createdCard (@RequestParam CardType cardType,@RequestParam CardColor cardColor,
    Authentication authentication){

        Client clientAuth = clientRepository.findByEmail(authentication.getName());

        List<Card> cardFiltered = clientAuth.getCards().stream()
                .filter(card -> card.getType() == cardType && card.getColor() == cardColor).collect(Collectors.toList());

       if ((long) cardFiltered.size() ==1){
           return new ResponseEntity<>("Already this type of Card", HttpStatus.FORBIDDEN);
       }

       String numberCard;

       Integer cvv = getRandomNumberCvv(0,999);

       do{
           numberCard = getRandomNumberCard();
       }
       while (cardRepository.existsByNumber(numberCard));

       Card newCard = new Card(clientAuth.getFirstName() + " "+ clientAuth.getLastName(),
               cardType, cardColor, numberCard,cvv,LocalDate.now(),LocalDate.now().plusYears(5));
       clientAuth.addCard(newCard);
       cardRepository.save(newCard);
       return new ResponseEntity<> (HttpStatus.CREATED);
    }
    /*Account account = null;
        do {
        String number = "VIN" + AccountUtils.getRandomNumberAccount(100000000,1000000);
        account= new Account(number,0.0,LocalDate.now());
    }
        while(accountRepository.existsByNumber(account.getNumber()));

        clientAuth.addAccount(account);
        accountRepository.save(account);*/
}
