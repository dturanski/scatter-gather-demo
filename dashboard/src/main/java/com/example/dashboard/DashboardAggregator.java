package com.example.dashboard;

import io.spring.compositeservice.domain.CardList;
import io.spring.compositeservice.domain.Dashboard;
import io.spring.compositeservice.domain.OfferList;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collector;

/**
 * @author David Turanski
 **/
public class DashboardAggregator {

	public Mono<Dashboard> aggregate() {

		WebClient offersClient = WebClient.create("http://localhost:8081");
		WebClient cardListClient = WebClient.create("http://localhost:8082");

			return
			Flux.just(offersClient.get()
				.uri("/offers").accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(OfferList.class),
				cardListClient.get()
					.uri("/cards").accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(CardList.class))
				.collect(new Collector<Mono<Dashboard>,, Dashboard>() {
				}, Object, Object > () {
				})
			.collect(() -> {
				Dashboard dashboard = new Dashboard();
				dashboard.setOffers(o);
				dashboard.setCards(c);
				return Mono.just(dashboard);
			});
	}
}
