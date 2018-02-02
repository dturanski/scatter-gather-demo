package com.example.dashboard.integration;

import static org.springframework.integration.IntegrationMessageHeaderAccessor.CORRELATION_ID;

import com.example.dashboard.domain.CardList;
import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import com.example.dashboard.domain.OfferList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * @author David Turanski
 **/

@Configuration
@EnableIntegration
public class IntegrationConfig {

	@Autowired
	BeanFactory beanFactory;

	@Bean
	Executor executor() {
		return new ForkJoinPool();
	}

	@Bean
	public MessageChannel input() {
		return MessageChannels.direct().get();
	}

	@Bean
	public MessageChannel aggregatorInput() {
		return MessageChannels.direct().get();
	}

	@Bean
	public MessageChannel output() {
		return MessageChannels.direct().get();
	}

	@Bean
	public MessageChannel aggregatorOutput() {
		return MessageChannels.publishSubscribe().get();
	}

	@Bean
	public MessageChannel offersInput(Executor executor) {
		return MessageChannels.executor(executor).get();
	}

	@Bean
	public MessageChannel cardListInput(Executor executor) {
		return MessageChannels.executor(executor).get();
	}

	@Bean
	@ServiceActivator(inputChannel = "input", outputChannel = "toRouter")
	public Transformer correlationIdProvider() {
		return message -> MessageBuilder.fromMessage(message)
			.copyHeadersIfAbsent(Collections.singletonMap(CORRELATION_ID, UUID.randomUUID().toString())).build();
	}

	@Router(inputChannel = "toRouter")
	@Bean
	public RecipientListRouter restServicesRouter(BeanFactory beanFactory) {
		RecipientListRouter router = new RecipientListRouter();
		router.setBeanFactory(beanFactory);
		router.addRecipient("cardListInput");
		router.addRecipient("offersInput");
		router.setSendTimeout(1000);
		router.afterPropertiesSet();
		return router;
	}

	@ServiceActivator(inputChannel = "offersInput")
	@Bean
	public MessageHandler offersHandler() {

		return new RestRequestHandler("http://localhost:8081/offers", HttpMethod.GET, aggregatorInput(),
			OfferList.class);
	}

	@ServiceActivator(inputChannel = "cardListInput")
	@Bean
	public MessageHandler cardListHandler() {
		return new RestRequestHandler("http://localhost:8082/cards", HttpMethod.GET, aggregatorInput(), CardList.class);
	}

	@ServiceActivator(inputChannel = "aggregatorOutput")
	@Bean
	MessageHandler outputBridge() {
		BridgeHandler bridgeHandler = new BridgeHandler();
		bridgeHandler.setOutputChannel(output());
		return bridgeHandler;
	}

	@Bean
	public DashboardAggregator aggregator() {
		return new DashboardAggregator(2);
	}


}
