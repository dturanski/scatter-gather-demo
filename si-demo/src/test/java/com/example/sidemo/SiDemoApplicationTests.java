package com.example.sidemo;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.integration.IntegrationMessageHeaderAccessor.CORRELATION_ID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiDemoApplicationTests {

	@Autowired
	MessageChannel input;

	@Autowired
	PollableChannel output;



	@Test
	public void contextLoads() {
	}

	@Test
	public void testAggregator() {
		for (int i=1; i<= 10; i++) {
			Message<?> message = MessageBuilder.withPayload(new Integer(i))
				.copyHeaders(Collections.singletonMap(CORRELATION_ID, "01")).build();
			input.send(message);
		}
		Message<Integer> response = (Message<Integer>)output.receive(1000);
		assertThat(response).isNotNull();
		assertThat(response.getPayload()).isEqualTo(55);

	}
}
