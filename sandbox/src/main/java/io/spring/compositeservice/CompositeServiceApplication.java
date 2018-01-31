package io.spring.compositeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class CompositeServiceApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("input")
	MessageChannel input;

	@Autowired
	@Qualifier("output")
	PollableChannel output;

	public static void main(String[] args) {
		new SpringApplicationBuilder().web(WebApplicationType.NONE).sources(CompositeServiceApplication.class)
			.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		input.send(new GenericMessage<>("Hello"));

		Message<?> message;
		while ((message = output.receive(500)) != null) {
			System.out.println(message.getPayload());
		}

		System.exit(0);
	}
}
