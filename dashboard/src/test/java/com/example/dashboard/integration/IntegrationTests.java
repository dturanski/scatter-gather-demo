package com.example.dashboard.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Collections;

/**
 * @author David Turanski
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegrationTests {


	@Autowired
	DashboardService dashboardService;

	@Test
	public void testAll() {

		Dashboard dashboard = dashboardService.getDashboad(new DashboardRequest());
		assertThat(dashboard).isNotNull();
		assertThat(dashboard.getCards().getCards()).hasSize(2);
		assertThat(dashboard.getOffers().getOffers()).hasSize(2);
	}

	@Test
	public void TestWs() {

		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		Message<?> request = new GenericMessage("hello");
		webServiceTemplate.marshalSendAndReceive(request.getPayload(), new WebServiceMessageCallback(){

			@Override
			public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
				System.out.println(request);

			}
		});



	}


}
