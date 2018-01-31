package io.spring.compositeservice.domain;

import java.util.List;

/**
 * @author David Turanski
 **/
public class CardList {

	private List<Card> cards;

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
