package com.example.dashboard.integration;

import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * @author David Turanski
 **/
@MessagingGateway
public interface DashboardService {
	@Gateway(requestChannel = "input", replyChannel = "output")
	Dashboard getDashboad(DashboardRequest request);
}