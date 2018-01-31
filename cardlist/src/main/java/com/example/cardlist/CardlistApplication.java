package com.example.cardlist;

import io.spring.compositeservice.domain.Card;
import io.spring.compositeservice.domain.CardList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class CardlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardlistApplication.class, args);
	}

	@GetMapping("/cards")
	public CardList getCardList() {
		CardList cardList = new CardList();
		Card c1 = new Card();
		c1.setActive(true);
		c1.setBalance(1000.0);
		c1.setNumber("12345678");

		Card c2 = new Card();
		c2.setBalance(2000.0);
		c2.setNumber("87654321");

		cardList.setCards(Arrays.asList(c1, c2));
		return cardList;
	}
}
