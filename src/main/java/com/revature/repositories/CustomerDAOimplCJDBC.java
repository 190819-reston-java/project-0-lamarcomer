package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Customer;
import com.revature.model.Transaction;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.StreamCloser;

public class CustomerDAOimplCJDBC implements CustomerDAO {

	@Override
	public Customer viewCustomer(long id) {
		Customer customer = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM project_0.customers WHERE id = ?;";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setLong(1, id);
				if (stmt.execute()) {
					try (ResultSet resultSet = stmt.getResultSet()) {
						if (resultSet.next()) {
							customer = createCustomerFromRS(resultSet);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;

	}

	@Override
	public Customer viewCustomer(String name) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Customer customer = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			statement = conn.prepareStatement("SELECT * FROM project_0.customers WHERE personusername = ?;");
			statement.setString(1, name);
			if (statement.execute()) {
				resultSet = statement.getResultSet();
				if (resultSet.next()) {
					customer = createCustomerFromRS(resultSet);
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

	@Override
	public List<Customer> viewCustomers() {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;

		List<Customer> customers = new ArrayList<Customer>();

		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM project_0.customers;");
			while (resultSet.next()) {
				customers.add(createCustomerFromRS(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			StreamCloser.close(resultSet);
			StreamCloser.close(statement);
			StreamCloser.close(conn);
		}

		return customers;

	}
	

	
	@Override
	public boolean createCustomer(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;

		String query = "INSERT INTO project_0.customers VALUES (DEFAULT, ?, ?, ?);";
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getPassword());
			stmt.setDouble(3, c.getBalance());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}

		return true;
	}

	@Override
	public boolean updateCustomer(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;

		final String query = "UPDATE project_0.customers SET personusername=?, personpassword=?, balance=? WHERE id = ?;";

		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getPassword());
			stmt.setDouble(3, c.getBalance());
			stmt.setLong(4, c.getId());

			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}

		return true;
	}

	@Override
	public boolean deleteCustomer(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;

		final String query = "DELETE FROM project_0.customers WHERE personusername = ?;";
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getName());

			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return true;
	}

	private Customer createCustomerFromRS(ResultSet resultSet) throws SQLException {
		return new Customer(resultSet.getLong("id"), resultSet.getString("personusername"),
				resultSet.getString("personpassword"), resultSet.getDouble("balance"));
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
	public Customer viewBalance(String name) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Customer customer = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			statement = conn.prepareStatement("SELECT * FROM project_0.customers WHERE personusername = ?;");
			statement.setString(1, name);
			if (statement.execute()) {
				resultSet = statement.getResultSet();
				if (resultSet.next()) {
					customer = createCustomerFromRS(resultSet);
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

	@Override
	public boolean withdrawFromBalance(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;

		final String query = "UPDATE project_0.customers SET personusername=?, personpassword=?, balance=? WHERE id = ?;"
				+ "INSERT INTO project_0.transactions (id, withdrawn, balance) VALUES (?, ?, ?);";

		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getPassword());
			stmt.setDouble(3, c.getBalance());
			stmt.setLong(4, c.getId());
			stmt.setLong(5, c.getId());
			stmt.setDouble(6, c.getAmount());
			stmt.setDouble(7, c.getBalance());


			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}

		return true;
	}

	@Override
	public boolean depositFromBalance(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;

		final String query = "UPDATE project_0.customers SET personusername=?, personpassword=?, balance=? WHERE id = ?;"
				+ "INSERT INTO project_0.transactions (id, deposited, balance) VALUES (?, ?, ?);";

		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getPassword());
			stmt.setDouble(3, c.getBalance());
			stmt.setLong(4, c.getId());
			stmt.setLong(5, c.getId());
			stmt.setDouble(6, c.getAmount());
			stmt.setDouble(7, c.getBalance());

			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}

		return true;
	}

}