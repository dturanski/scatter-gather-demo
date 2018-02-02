package com.example.dashboard.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.integration.IntegrationMessageHeaderAccessor.CORRELATION_ID;

import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
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
	DashboardService dashboardService;

	@Test
	public void testAll() {

		Dashboard dashboard = dashboardService.getDashboad(new DashboardRequest());
		assertThat(dashboard).isNotNull();
		assertThat(dashboard.getCards().getCards()).hasSize(2);
		assertThat(dashboard.getOffers().getOffers()).hasSize(2);
	}


}
