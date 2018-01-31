package io.spring.compositeservice.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author David Turanski
 **/
@Configuration
public class IntegrationConfiguration {

	@Bean
	Executor executor() {
		return Executors.newFixedThreadPool(10);
	}

	@Bean
	public MessageChannel channelA() {
		return MessageChannels.executor(executor()).get();
	}

	@Bean
	MessageChannel channelB() {
		return MessageChannels.executor(executor()).get();
	}

	@Bean
	MessageChannel channelC() {
		return MessageChannels.executor(executor()).get();
	}

	@Bean
	public QueueChannel output() {
		//return MessageChannels.executor(Executors.newSingleThreadExecutor()).get();
		return MessageChannels.queue().get();
	}

	@Bean
	public MessageChannel input() {
		return MessageChannels.direct().get();
	}

	@Bean
	public IntegrationFlow routerFlow() {
		return IntegrationFlows.from("input").routeToRecipients(r -> {
			r.recipient("channelA").recipient("channelB").recipient("channelC").get();
		}).get();
	}

	@Bean
	public Services services() {
		return new Services();
	}

	static class Services {
		private static Logger logger = LoggerFactory.getLogger(Services.class);

		@ServiceActivator(inputChannel = "channelA", outputChannel = "output")
		public String a(String payload) {
			logger.info("A: {}", payload);
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return payload +"A";
		}

		@ServiceActivator(inputChannel = "channelB", outputChannel = "output")
		public String b(String payload) {
			logger.info("B: {}", payload);
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return payload + "B";
		}

		@ServiceActivator(inputChannel = "channelC", outputChannel = "output")
		public String c(String payload) {
			logger.info("C: {}", payload);
			try {
				Thread.sleep(2);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return payload + "C";
		}
	}

}


