package com.revature.services;

import java.util.Date;
import java.util.List;

import com.revature.model.Customer;
import com.revature.model.Transaction;
import com.revature.repositories.CustomerDAO;
import com.revature.repositories.CustomerDAOimplCJDBC;
import com.revature.repositories.TransactionDAO;
import com.revature.repositories.TransactionDAOimplCJDBC;

public class CustomerService {

	private Customer selectedCustomer = new Customer(0L, "Default", "Password", 0);
	private CustomerDAO customerDAO = new CustomerDAOimplCJDBC();
	private TransactionDAO transactionDAO = new TransactionDAOimplCJDBC();
	private Date date = new Date();
	private Transaction selectedTransaction = new Transaction(0L, 0L, 0, 0, 0, date);

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}
	
	public Transaction getSelectedTransaction() {
		return selectedTransaction;
	}
	
	public void setSelectedTransaction(Transaction selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public List<Customer> getCustomers() {
		return customerDAO.viewCustomers();
	}
	
	public List<Transaction> getTransactions(long id) {
		transactionDAO.viewTransactions(id)
		.forEach((Transaction t)->{System.out.println(t);});
		return transactionDAO.viewTransactions(id);
	}

	public void changeSelectedCustomer(String username) {
		setSelectedCustomer(customerDAO.viewCustomer(username));
	}

	public void updateSelectedCustomer() {
		customerDAO.updateCustomer(selectedCustomer);
	}

	public void withdrawSelectedCustomer(Customer c2, double amount) {
		double sum=0;
		for (Customer c : customerDAO.viewCustomers()) {
			if (c.getName().equals(c2.getName())) {
				c.setAmount(amount);
				sum = c.getBalance() - amount;
				c.setBalance(sum);
				customerDAO.withdrawFromBalance(c);
				setSelectedCustomer(c);
			}
		}
		
	}

	public void depositSelectedCustomer(Customer c2, double amount) {
		double sum=0;
		for (Customer c : customerDAO.viewCustomers()) {
			if(c.getName().equals(c2.getName())) {
			c.setAmount(amount);
			sum = amount + c.getBalance();
			c.setBalance(sum);
			customerDAO.depositFromBalance(c);
			setSelectedCustomer(c);
			}
		}
	}

	public void changeCustomerPassword(Customer c2, String password) {
		for (Customer c : customerDAO.viewCustomers()) {
			if (c.getName().equals(c2.getName())) {
				c.setPassword(password);
				customerDAO.updateCustomer(c);
			}
		}
	}

	public void signIn(String userInput) {
		setSelectedCustomer(customerDAO.viewCustomer(userInput));
	}

	public boolean checkAccount(String username, String password) {
		for (Customer c : customerDAO.viewCustomers()) {
			if (c.getName().equals(username) && c.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public void createCustomer(String name, String password) {
		customerDAO.createCustomer(new Customer(0L, name, password, 0.0));
	}

	public Transaction viewTransactionHistory(long id) {
		return transactionDAO.viewTransaction(id);
	}
}
