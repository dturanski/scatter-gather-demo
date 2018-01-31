package com.example.dashboard.web;

import com.example.dashboard.domain.Dashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.messaging.Message;

import java.util.List;

/**
 * @author David Turanski
 **/
public class DashboardAggregator {

	private static Logger log = LoggerFactory.getLogger(DashboardAggregator.class);

	public Dashboard aggregate(List<Object> components) {
		log.info("aggregating {} components", components.size());
		return new Dashboard();
	}

	@ReleaseStrategy
	public boolean releaseChecker(List<Message<?>> messages) {
		log.info("checking release");
		return true;
	}


}
