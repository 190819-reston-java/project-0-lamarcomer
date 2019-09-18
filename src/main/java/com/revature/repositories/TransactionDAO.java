package com.revature.repositories;

import java.util.List;

import com.revature.model.Transaction;

public interface TransactionDAO {
	
	
	Transaction viewTransaction(long id);
	
	List<Transaction> viewTransactions(long id);

}
