package com.example.dashboard.integration;

/**
 * @author David Turanski
 **/

import com.example.dashboard.domain.CardList;
import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import com.example.dashboard.domain.OfferList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.ReleaseStrategy;

import java.util.List;

/**
 * @author David Turanski
 **/

public class DashboardAggregator {

	private static Logger log = LoggerFactory.getLogger(DashboardAggregator.class);

	private final int count;

	public DashboardAggregator(int count) {
		this.count = count;
	}

	@Aggregator(inputChannel = "aggregatorInput", outputChannel = "aggregatorOutput")
	public Dashboard aggregate(List<Object> components) {
		Dashboard dashboard = new Dashboard();
		log.info("aggregating {} components", components.size());
		for (Object obj : components) {
			if (obj instanceof CardList) {
				dashboard.setCards((CardList) obj);
			}
			if (obj instanceof OfferList) {
				dashboard.setOffers((OfferList) obj);
			}
		}
		return dashboard;
	}

	@ReleaseStrategy
	public boolean canRelease(List<Object> components) {
		return components.size() == count;
	}


}