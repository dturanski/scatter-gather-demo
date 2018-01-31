package io.spring.compositeservice.domain;

import java.util.List;

/**
 * @author David Turanski
 **/
public class OfferList {
	private List<Offer> offers;

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
}
