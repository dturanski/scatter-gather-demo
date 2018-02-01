package com.example.sidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

import java.util.List;

@SpringBootApplication
public class SiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiDemoApplication.class, args);
	}

	@Bean
	MessageChannel output() {
		return MessageChannels.queue().get();
	}

	@MessageEndpoint
	static class MyAggregator {

		@Aggregator(inputChannel = "input", outputChannel = "output")
		public Integer aggregate(List<Integer> list) {
			int sum = 0;
			for (int i : list) {
				sum += i;
			}
			return sum;
		}

		@ReleaseStrategy
		public boolean canRelease(List<Integer> list) {
			return list.size() == 10;
		}

	}
}
