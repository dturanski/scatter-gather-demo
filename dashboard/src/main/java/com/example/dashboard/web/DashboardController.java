package com.example.dashboard.web;

import com.example.dashboard.domain.Dashboard;
import com.example.dashboard.domain.DashboardRequest;
import com.example.dashboard.integration.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author David Turanski
 **/

@RestController
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@PostMapping("/dashboard")
	Dashboard getDashboard(@RequestBody @Validated DashboardRequest request) {
		return dashboardService.getDashboad(request);

	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new DashboardRequestValidator());
	}
}
