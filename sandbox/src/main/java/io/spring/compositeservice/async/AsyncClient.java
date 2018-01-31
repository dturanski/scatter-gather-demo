package io.spring.compositeservice.async;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.ws.WebServiceClient;
import java.util.concurrent.CompletableFuture;

/**
 * @author David Turanski
 **/
public class AsyncClient extends WebServiceGatewaySupport {
	public CompletableFuture<String> exec() {

//		return CompletableFuture.supplyAsync(() -> {
//			getWebServiceTemplate()
//				.marshalSendAndReceive("http://www.webservicex.com/stockquote.asmx",
//					request,
//					new SoapActionCallback("http://www.webserviceX.NET/GetQuote"));
//		});
//	}
		return null;
}}
