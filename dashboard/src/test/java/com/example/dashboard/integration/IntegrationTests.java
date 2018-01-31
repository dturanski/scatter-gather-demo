package com.example.dashboard.integration;

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

/**
 * @author David Turanski
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegrationTests {

	@Autowired
	MessageChannel input;

	@Autowired
	PollableChannel aggregatorOutput;

	@Test
	public void testAll() {
		Message request = MessageBuilder.withPayload("").copyHeaders(Collections.singletonMap(CORRELATION_ID,"01"))
			.build();
		input.send(request);
		Message<?> response = aggregatorOutput.receive(1000);
		assertThat(response).isNotNull();

	}


}
