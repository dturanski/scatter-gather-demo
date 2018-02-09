package com.example.dashboard.integration;

import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;
import org.springframework.ws.client.support.destination.DestinationProvider;

/**
 * @author David Turanski
 **/
public class CustomWebServiceOutboundGateway extends SimpleWebServiceOutboundGateway {
	public CustomWebServiceOutboundGateway(DestinationProvider destinationProvider) {
		super(destinationProvider);
	}
}
