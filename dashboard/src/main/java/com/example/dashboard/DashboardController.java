package com.example.dashboard;

import io.spring.compositeservice.domain.Dashboard;
import reactor.core.publisher.Mono;

/**
 * @author David Turanski
 **/
public class DashboardController {

	Mono<Dashboard> getDashboard() {
		return new DashboardAggregator().aggregate();
	}
}
