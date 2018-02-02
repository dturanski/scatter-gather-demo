package com.example.dashboard.web;

import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import com.example.dashboard.integration.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David Turanski
 **/

@RestController
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/dashboard")
	Dashboard getDashboard() {
		return dashboardService.getDashboad(new DashboardRequest());

	}
}
