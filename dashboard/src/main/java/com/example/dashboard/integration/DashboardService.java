package com.example.dashboard.integration;

import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * @author David Turanski
 **/
@MessagingGateway
public interface DashboardService {
	@Gateway(requestChannel = "input", replyChannel = "output")
	Dashboard getDashboad(@Payload  DashboardRequest request);
}