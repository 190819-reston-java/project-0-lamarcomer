package com.revature.controller;

import org.junit.Test;
//import org.junit.Assert;
import org.postgresql.util.PSQLException;

import com.revature.model.Customer;
import com.revature.repositories.CustomerDAO;
import com.revature.services.CustomerService;
import com.revature.repositories.CustomerDAOimplCJDBC;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class CustomerCLITest {

	CustomerService customerService = new CustomerService();
	CustomerDAO customerDAO = new CustomerDAOimplCJDBC();
	CustomerCLI customerCLI = new CustomerCLI();

	static Object myNeededObject;

	@Test
	public void createNewCustomer() {
		customerService.createCustomer("Dee", "Lee");
		customerService.getSelectedCustomer();
	}

	@Test
	public void createNewCustomer2() {
		customerService.createCustomer("apples", "oranges");
		customerService.getSelectedCustomer();
	}

	@Test
	public void logInToAccount() {
		Customer c = customerService.getSelectedCustomer();
		customerService.checkAccount(c.getName(), c.getPassword());
	}

	@Test
	public void logInToAccount2() {
		Customer c = new Customer(0L, "new", "customer", 0);
		customerService.checkAccount(c.getName(), c.getPassword());
	}

	@Test
	public void changeCustomerBalance() {
		Customer c = customerService.getSelectedCustomer();
		customerService.depositSelectedCustomer(c, 900000000);
	}

	@Test
	public void changeCustomerBalance2() {
		Customer c = customerService.getSelectedCustomer();
		customerService.depositSelectedCustomer(c, -3);
	}

	@Test
	public void changeCustomerPassword() {
		Customer c = customerService.getSelectedCustomer();
		customerService.changeCustomerPassword(c, "thepassword");
	}

	@Test
	public void deleteCustomer() {
		for (Customer c : customerDAO.viewCustomers()) {
			if (c.getName().equals("Dee")) {
				customerDAO.deleteCustomer(c);
			}
		}

	}

	@Test
	public void deleteCustomer2() {
		for (Customer c : customerDAO.viewCustomers()) {
			if (c.getName().equals("apples")) {
				customerDAO.deleteCustomer(c);
			}
		}

	}

	@Before
	public void setUp() {
		System.out.println("Creating necessary objects for test");
		myNeededObject = new Object();
	}

	@After
	public void tearDown() {
		System.out.println("Getting rid of unneeded objects after test");
		myNeededObject = null;
	}

	@BeforeClass
	public static void setUpOnlyOnceAtBeginning() {
		System.out.println("Before Class");
	}

	@AfterClass
	public static void tearDownOnlyOneAtEnd() {
		System.out.println("After Class");
	}
}
