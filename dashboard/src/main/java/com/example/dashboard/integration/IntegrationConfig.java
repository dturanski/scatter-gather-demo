package com.example.dashboard.integration;

import com.example.dashboard.domain.CardList;
import com.example.dashboard.domain.OfferList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

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
	public MessageChannel aggregatorOutput() {
		return MessageChannels.queue().get();
	}

	@Bean
	public MessageChannel offersInput(Executor executor) {
		return MessageChannels.executor(executor).get();
		//return MessageChannels.direct().get();
	}

	@Bean
	public MessageChannel cardListInput(Executor executor) {
		return MessageChannels.executor(executor).get();
		//return MessageChannels.direct().get();
	}

	@Router(inputChannel = "input")
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

	@Bean
	public DashboardAggregator aggregator() {
		return new DashboardAggregator(2);
	}
}
