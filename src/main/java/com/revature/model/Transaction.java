package com.revature.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Transaction {
	
	private long transactionid;
	private long customerid;
	private double balance;
	private double withdrawn;
	private double deposited;
	private Date date;
	static NumberFormat formatter = new DecimalFormat("#.00");  

	
	public Transaction(long transactionid, long customerid, double balance, double withdrawn, double deposited, 
			Date date) {
		super();
		this.transactionid = transactionid;
		this.customerid = customerid;
		this.balance = balance;
		this.withdrawn = withdrawn;
		this.deposited = deposited;
		this.date = date;
	}

	public long getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(long transactionid) {
		this.transactionid = transactionid;
	}

	public long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getWithdrawn() {
		return withdrawn;
	}

	public void setWithdrawn(double withdrawn) {
		this.withdrawn = withdrawn;
	}

	public double getDeposited() {
		return deposited;
	}

	public void setDeposited(double deposited) {
		this.deposited = deposited;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "date=" + date + ", withdrawn=" + formatter.format(withdrawn)
				+ ", deposited=" + formatter.format(deposited) + ", balance=" + formatter.format(balance);
	}

	
	
	

}
