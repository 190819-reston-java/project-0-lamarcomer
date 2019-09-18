package com.revature.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exceptions.InvalidNumberException;
import com.revature.exceptions.NegativeNumberException;
import com.revature.model.Customer;
import com.revature.model.Transaction;
import com.revature.services.CustomerService;

public class CustomerCLI {

	public static boolean isSignedIn;
	private static int optionNotRecognizedCounter = 0;
	private static int optionNotRecognizedCounter2 = 0;
	private static Scanner sc = new Scanner(System.in);
	private static CustomerService customerService = new CustomerService();
	public static Logger logger = Logger.getLogger(CustomerCLI.class);
	static NumberFormat formatter = new DecimalFormat("#.00");

	public static void loginmenu() {
		try {
		logger.info("Login menu started");
		System.out.println("--------------------------------------");
		System.out.println("Hello and welcome to Bank!");
		System.out.println("What would you like to do?");
		System.out.println("--------------------------------------");
		System.out.println("1: Sign In 2: Create New Account 3: Exit Bank");
		System.out.println("--------------------------------------");
		String i = sc.next();
		System.out.println("--------------------------------------");
		int input = Integer.parseInt(i);	
		if(input !=1 | input != 2 | input != 3) {
			throw new InvalidNumberException();
		}
		logger.debug("Received user input: " + i);
		switch (i) {
		case "1":
			menu();
			break;
		case "2":
			createAccount();
			menu();
			break;
		case "3":
			System.out.println("Thank you for visiting Bank!");
			System.out.println("--------------------------------------");
			System.exit(0);
			break;
		default:
			System.out.println("Input not valid. Please Try Again.");
			optionNotRecognizedCounter++;
			logger.debug(i + " was not recognized.");
			logger.debug("This has happened " + optionNotRecognizedCounter + " times.");

			if (optionNotRecognizedCounter > 5) {
				logger.fatal("Failed to recognize option 5 times, exiting");
				System.exit(1);
			}

			break;

		}
		}catch (InvalidNumberException e) {
			System.out.println("Invalid input:");
			System.out.println("Value must be a valid number");
			logger.debug("Invalid input entered");
		} catch(Exception e) {
			logger.fatal("Invalid Input. Exiting.");
		}
		loginmenu();
	}

	public static void createAccount() {
		System.out.println("Enter Username:");
		String username = sc.next();
		System.out.println("--------------------------------------");
		logger.debug("Received user input username: " + username);
		System.out.println("Enter Password:");
		String password = sc.next();
		System.out.println("--------------------------------------");
		logger.debug("Received user input password: " + password);
		customerService.createCustomer(username, password);
		if (customerService.checkAccount(username, password) == true) {
			System.out.println("Account Created Successfully!");
			logger.info("Account Creation Successful");
		} else {
			System.out.println("Account Creation Failed. Please try another username.");
			logger.debug("Account Creation Failed");
		}
	}

	public static void logIn() {
		System.out.println("Please1 enter your Username and Password:");
		System.out.println("------------------------------");
		System.out.println("Enter Username:");
		String username = sc.next();
		System.out.println("--------------------------------------");
		logger.debug("Received user input username: " + username);
		System.out.println("Enter Password:");
		String password = sc.next();
		System.out.println("------------------------------");
		logger.debug("Received user input password: " + password);
		System.out.println("Username: " + username + "\n" + "Password: " + password);
		System.out.println("-----------------------------------------");
		if (customerService.checkAccount(username, password) == true) {
			customerService.changeSelectedCustomer(username);
			customerService.getSelectedCustomer();
			System.out.println("Login Successful.");
			System.out.println("--------------------------------------");
			logger.info("User login successful");
			isSignedIn = true;
		} else {
			System.out.println("Login Unsuccessful. Please Try Again.");
			System.out.println("--------------------------------------");
			logger.info("User login failed");
			loginmenu();
		}
	}

	public static void menu() {
		if (isSignedIn == false) {
			logIn();
		}
		System.out.println("Choose an option:");
		System.out.println("1: View Current Balance");
		System.out.println("2: Deposit");
		System.out.println("3: Withdraw");
		System.out.println("4: Change Password");
		System.out.println("5: View Transactions");
		System.out.println("6: Sign Out");
		System.out.println("7: Exit Bank");
		System.out.println("8: View User Information");

		String userInput = sc.next();
		switch (userInput) {
		case "1":
			viewBalance();
			break;
		case "2":
			depositToAccount();
			break;
		case "3":
			withdrawFromAccount();
			break;
		case "4":
			changePassword();
			break;
		case "5":
			viewTransactions();
			break;
		case "6":
			signOut();

			break;
		case "7":
			System.out.println("Thank you for visiting Bank!");
			System.exit(0);
			break;
		case "8":
			userInformation();
			break;
		default:
			System.out.println("Input not valid. Please Try Again.");
			optionNotRecognizedCounter2++;
			logger.debug(userInput + " was not recognized.");
			logger.debug("This has happened " + optionNotRecognizedCounter2 + " times.");

			if (optionNotRecognizedCounter2 > 5) {
				logger.fatal("Failed to recognize option 5 times, exiting");
				System.exit(1);
			}
			break;
		}
		menu();

	}

	private static void userInformation() {
		Arrays.asList(customerService.getSelectedCustomer().toString(), "").forEach((String s) -> {
			System.out.println(s);
		});

	}

	public static void viewBalance() {
		Customer cs = customerService.getSelectedCustomer();
		customerService.changeSelectedCustomer(cs.getName());
		System.out.println("Current Balance is: " + formatter.format(cs.getBalance()));
		System.out.println("--------------------------------------");
		logger.info("Displayed User Balance successfully.");
	}

	public static void depositToAccount() {
		try {
			Customer cs = customerService.getSelectedCustomer();
			System.out.println("How much would you like to deposit?: ");
			double amount = sc.nextDouble();
			if (amount < 0) {
				throw new NegativeNumberException();
			}
			formatter.format(amount);
			logger.debug("Received user input: " + amount);
			customerService.depositSelectedCustomer(cs, amount);
			System.out.println("Amount Deposited: " + formatter.format(amount));
			System.out.println("Current Balance: " + formatter.format((cs.getBalance() + amount)));
			System.out.println("--------------------------------------");
			logger.info("Increased User balance from " + formatter.format(cs.getBalance()) + " to "
					+ formatter.format((cs.getBalance()) + amount) + " successfully");
		} catch (NegativeNumberException e) {
			System.out.println("Number cannot be a negative value");
			logger.debug("Negative number entered");
		} catch(InputMismatchException e) {
			System.out.println("Input must be a number");
			logger.debug("User entered invalid input");
		} catch (Exception e) {
			logger.fatal("Problems Encountered. Shutting down.");
			System.exit(1);
		}
	}

	public static void withdrawFromAccount() {
		try {
			Customer cs = customerService.getSelectedCustomer();
			System.out.println("How much would you like to withdraw?: ");
			double amount = sc.nextDouble();
			formatter.format(amount);
			logger.debug("Received user input: " + amount);
			customerService.withdrawSelectedCustomer(cs, amount);
			customerService.getSelectedCustomer();
			System.out.println("Amount Withdrawn: " + formatter.format(amount));
			System.out.println("Current Balance: " + formatter.format((cs.getBalance() - amount)));
			System.out.println("--------------------------------------");
			logger.info("Increased User balance from " + formatter.format(cs.getBalance()) + " to "
					+ formatter.format((cs.getBalance() - amount)) + " successfully.");
		} catch (NegativeNumberException e) {
			System.out.println("Error! Number value cannot be negative!");
			logger.debug("Negative number entered");
		} catch (Exception e) {
			logger.fatal("Problems Encountered. Shutting down.");
			System.exit(1);
		}
	}

	public static void changePassword() {
		Customer cs = customerService.getSelectedCustomer();
		System.out.println("Username: " + cs.getName());
		System.out.println("Set your new Password: ");
		String userInput = sc.next();
		logger.debug("Received user input: " + userInput);
		customerService.changeCustomerPassword(cs, userInput);
		System.out.println("Password changed to " + userInput);
		System.out.println("--------------------------------------");
		logger.info("Changed User Password to " + userInput + " successfully.");
	}

	public static void viewTransactions() {
		Transaction ts = customerService.getSelectedTransaction();
		Customer cs = customerService.getSelectedCustomer();
		long id = cs.getId();
		ts.setCustomerid(id);
		System.out.println("Your Transactions: ");
		customerService.getTransactions(id);
		System.out.println(ts.getBalance() + " " + ts.getDate());
		logger.info("Displayed User Transactions successfully");
	}

	public static void signOut() {
		System.out.println("Signing out");
		customerService = new CustomerService();
		isSignedIn = false;
		logger.info("User signed out successfully");
		loginmenu();
	}

}
