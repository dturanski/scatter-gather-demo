package io.spring.compositeservice.domain;

/**
 * @author David Turanski
 **/
public class Card {
	private String number;
	private double balance;
	private boolean active;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
