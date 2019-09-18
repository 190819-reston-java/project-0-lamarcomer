package com.revature.repositories;

import java.util.List;

import com.revature.model.Customer;

public interface CustomerDAO {
	
	Customer viewCustomer(long id);
	
	Customer viewCustomer(String name);
	
	Customer viewBalance(String name);
	
	List<Customer> viewCustomers();
	
	boolean createCustomer(Customer c);
	
	boolean updateCustomer(Customer c);
	
	boolean deleteCustomer(Customer c);
	
	boolean withdrawFromBalance(Customer c);
	
	boolean depositFromBalance(Customer c);


}
