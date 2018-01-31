package com.example.offers;

import io.spring.compositeservice.domain.Offer;
import io.spring.compositeservice.domain.OfferList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class OffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffersApplication.class, args);
	}

	@GetMapping("/offers")
	public OfferList getOffers() {
		OfferList offerList = new OfferList();
		Offer o1 = new Offer();
		o1.setDescription("offer 1!");
		Offer o2 = new Offer();
		o2.setDescription("offer 2!");
		offerList.setOffers(Arrays.asList(o1,o2));
		return offerList;
	}
}
