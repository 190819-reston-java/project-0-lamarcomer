package com.revature.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.revature.exceptions.NegativeNumberException;

public class Customer {

	private long id;
	private String name;
	private String password;
	private double balance;
	private double amount;
	static NumberFormat formatter = new DecimalFormat("#0.00");

	public Customer(long id, String name, String password, double balance) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		if (balance < 0) {
			throw new NegativeNumberException();
		}
		this.balance = balance;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + ", balance="
				+ formatter.format(balance) + "]";
	}

}
