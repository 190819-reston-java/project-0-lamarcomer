package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Transaction;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.StreamCloser;

public class TransactionDAOimplCJDBC implements TransactionDAO {

	
	
	@Override
	public List<Transaction> viewTransactions(long id) {

		List<Transaction> transactions = new ArrayList<Transaction>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM project_0.transactions WHERE id = ?;";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setLong(1, id);
				if (stmt.execute()) {
					try (ResultSet resultSet = stmt.getResultSet()) {
						while (resultSet.next()) {
							transactions.add(createTransactionFromRS(resultSet));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactions;

	}


	
	private Transaction createTransactionFromRS(ResultSet resultset) throws SQLException {
		return new Transaction(resultset.getLong("transaction_id"),
				resultset.getLong("id"),
				resultset.getDouble("balance"),
				resultset.getDouble("withdrawn"),
				resultset.getDouble("deposited"),
				resultset.getDate("time"));
	}
	
	

	@Override
	public Transaction viewTransaction(long id) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Transaction customer = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			statement = conn.prepareStatement("SELECT * FROM project_0.transactions WHERE id = ?;");
			statement.setLong(1, id);
			if (statement.execute()) {
				resultSet = statement.getResultSet();
				if (resultSet.next()) {
					customer = createTransactionFromRS(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			StreamCloser.close(resultSet);
			StreamCloser.close(statement);
		}
		return customer;
	}
}