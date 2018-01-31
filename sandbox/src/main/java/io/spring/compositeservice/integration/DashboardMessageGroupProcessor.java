package io.spring.compositeservice.integration;

import io.spring.compositeservice.domain.CardList;
import io.spring.compositeservice.domain.Customer;
import io.spring.compositeservice.domain.Dashboard;
import io.spring.compositeservice.domain.OfferList;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @author David Turanski
 **/
public class DashboardMessageGroupProcessor extends AbstractAggregatingMessageGroupProcessor {
	@Override
	protected Object aggregatePayloads(MessageGroup messageGroup, Map<String, Object> headers) {
		Dashboard dashboard = new Dashboard();
		for (Message m : messageGroup.getMessages()) {
			if (m.getPayload() instanceof Customer) {
				dashboard.setCustomer((Customer) m.getPayload());
			}
			if (m.getPayload() instanceof CardList) {
				dashboard.setCards((CardList) m.getPayload());
			}
			if (m.getPayload() instanceof OfferList) {
				dashboard.setOffers((OfferList) m.getPayload());
			}
			if (m.getPayload() instanceof Customer) {
				dashboard.setCustomer((Customer) m.getPayload());
			}
		}
		validate(dashboard);
		return dashboard;
	}

	private void validate(Dashboard dashboard) {
		//Or return an Error type as aggregate.
		//Throw exception if problem.
	}
}
