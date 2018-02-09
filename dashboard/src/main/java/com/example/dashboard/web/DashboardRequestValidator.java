package com.example.dashboard.web;

import com.example.dashboard.domain.DashboardRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * @author David Turanski
 **/
public class DashboardRequestValidator implements Validator {

	Pattern format = Pattern.compile("\\d{8}");

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass == DashboardRequest.class;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		DashboardRequest dashboardRequest = (DashboardRequest)obj;
		if (StringUtils.isEmpty(dashboardRequest.getAccountNumber().trim())) {
			errors.reject("account number is required");
		} else if (!format.matcher(dashboardRequest.getAccountNumber()).matches()) {
			errors.reject(String.format("Invalid account number [%s]. Must be an 8 digit number",dashboardRequest
				.getAccountNumber()));
		}
	}
}
