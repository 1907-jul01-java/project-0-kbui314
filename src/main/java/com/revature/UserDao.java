package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An object that uses DAO patterns create,read,insert, and delete
 * from the bank database.
 * @author kbui3
 *
 */
public class UserDao implements Dao<User> {
	Connection connection;
	
	
	@Override
	public void insert(User user) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("insert into users(username,password,category) values(?, ?, ?)");
			pStatement.setString(1, user.getUsername());
			pStatement.setString(2, user.getPassword());
			pStatement.setString(3, user.getCategory());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			
		}
		
	}

	@Override
	public List<User> getAll() {
		User username;
		List<User> usernames = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from users");
			while(resultSet.next()) {
				username = new User();
				username.setUsername(resultSet.getString("username"));
				username.setPassword(resultSet.getString("password"));
				username.setCategory(resultSet.getString("category"));
				usernames.add(username);
			}
			
		} catch (SQLException e) {
			
		}
		return usernames;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public UserDao(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Inserts account users base on username and account number into accountusers.
	 * @param username
	 * @param accountNumber
	 */
	public void insertAccountUser(String username, int accountNumber) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("insert into accountusers(username,accountnumber) "
					+ "values(?,?)");
			pStatement.setString(1, username);
			pStatement.setInt(2, accountNumber);
			pStatement.executeQuery();
		} catch(SQLException e){
			e.getMessage();
		}
	}
	
	/**
	 * Inserts account number into account.
	 * @param accountNumber
	 */
	public void insertAccount(int accountNumber) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("insert into account(number,balance) "
					+"values(?,?)");
			pStatement.setInt(1, accountNumber);
			pStatement.setInt(2, 0);
			pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Update the balance of the passed account number.
	 * @param accountNumber
	 * @param balance
	 */
	public void updateBalance(int accountNumber, int balance) {
		
		try {
			PreparedStatement pStatement = connection.prepareStatement("Update account set balance=? where number=?");
			pStatement.setInt(1, balance);
			pStatement.setInt(2, accountNumber);
			pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Returns a result set of a specified account.
	 * @param accountNumber
	 * @return resultset
	 */
	public ResultSet getAccount(int accountNumber) {
		ResultSet resultSet = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select number,balance from account where number=?");
			pStatement.setInt(1, accountNumber);
			resultSet = pStatement.executeQuery();
			return resultSet;
		} catch(SQLException e) {
			
		}
		return resultSet;
	}
	
	/**
	 * Inserts account application to apply for an open account into application table.
	 * @param username
	 */
	public void apply(String username) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("insert into application(username) values(?)");
			pStatement.setString(1,username);
			pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Inserts account application to apply for an open joint account into joint table.
	 * @param username
	 * @param account
	 * @param addUser
	 */
	public void insertJointApplication(String username, int account, String addUser) {
		try{
			PreparedStatement pStatement = connection.prepareStatement("Insert into joint(username,account,adduser) values(?,?,?)");
			pStatement.setString(1, username);
			pStatement.setInt(2, account);
			pStatement.setString(3, addUser);
			pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Get the category of the specified user.
	 * @param username
	 * @return category
	 */
	public String getCategory(String username) {
		String category = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select category from users where username=?");
			pStatement.setString(1,username);
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				category = resultSet.getString("category");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return category;
	}
	
	/**
	 * Get all the accounts.
	 */
	public void getAllAccounts() {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select users.username,accountusers.accountnumber,account.balance "
					+ "from users,accountusers,account where users.username=accountusers.username and "
					+ "accountusers.accountnumber=account.number "+
					"group by users.username,accountusers.accountnumber,account.balance");
			ResultSet resultSet = pStatement.executeQuery();
			int count = 0;
			while(resultSet.next()) {
				count++;
				String username = resultSet.getString(1);
				int account = resultSet.getInt(2);
				int balance = resultSet.getInt(3);
				System.out.println("Username: "+username+"  Account Number: "+account+"  Balance: "+balance);
			}
			if(count == 0) {
				System.out.println("There are no open accounts.");
			}
		} catch(SQLException e) {
			
		}
	}
	
	/**
	 * Deletes the account application. 
	 * @param username
	 */
	public void deleteApplication(String username) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Delete from application where username=?");
			pStatement.setString(1, username);
			pStatement.executeQuery();
		}catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Deletes the joint account application.
	 * @param id
	 */
	public void deleteJointApplication(int id) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Delete from joint where id=?");
			pStatement.setInt(1, id);
			pStatement.executeQuery();
		}catch(SQLException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Gets the user from the user table.
	 * @param username
	 * @return
	 */
	public ResultSet getUser(String username) {
		ResultSet resultSet = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select username from users where username=?");
			pStatement.setString(1, username);
			resultSet = pStatement.executeQuery();
			return resultSet;
		}catch(SQLException e) {
			e.getMessage();
		}
		return resultSet;
	}
	
	/**
	 * Gets the specified joint account application.
	 * @return resultset
	 */
	public ResultSet getJointApplications() {
		ResultSet resultSet = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select * from joint");
			resultSet = pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
		return resultSet;
	}
	
	/**
	 * Deletes the specified account.
	 * @param accountNumber
	 */
	public void deleteAccount(int accountNumber) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Delete from accountusers where accountusers.accountnumber=?");
			pStatement.setInt(1, accountNumber);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement("Delete from account where account.number=?");
			pStatement.setInt(1, accountNumber);
			pStatement.executeUpdate();
		}catch(SQLException e) {
			e.getMessage();
		}
		
	}
	/**
	 * To be implemented.
	 * Shows information about each transaction.
	 * @param transaction
	 */
	public void insertTransaction(Transaction transaction) {
		
	}
	
}
