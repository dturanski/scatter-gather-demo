package io.spring.compositeservice.domain;

import java.util.List;

/**
 * @author David Turanski
 **/
public class Dashboard {
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private Customer customer;
	private CardList cards;
	private OfferList offers;

	public CardList getCards() {
		return cards;
	}

	public void setCards(CardList cards) {
		this.cards = cards;
	}

	public OfferList getOffers() {
		return offers;
	}

	public void setOffers(OfferList offers) {
		this.offers = offers;
	}
}
