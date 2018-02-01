package com.example.dashboard.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author David Turanski
 **/
public class RestRequestHandler implements MessageHandler {

	private static Logger log = LoggerFactory.getLogger(RestRequestHandler.class);

	private final HttpRequestExecutingMessageHandler handler;

	public RestRequestHandler(String uri, HttpMethod method, MessageChannel outputChannel, Class<?>
		expectedResponseType)
	{
		handler = new HttpRequestExecutingMessageHandler(uri);
		handler.setHttpMethod(method);
		handler.setExpectedResponseType(expectedResponseType);
		handler.setOutputChannel(outputChannel);
	}



	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		log.info("processing request ...");
		handler.handleMessage(message);
		log.info("request processed");
	}
}
