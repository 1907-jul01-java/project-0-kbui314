package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	public Account getAccount(int accountNumber) {
		Account account = new Account();
		try {
			System.out.println("Hello");
			PreparedStatement pStatement = connection.prepareStatement("Select number,balance from account where account=?");
			System.out.println("sir");
			pStatement.setInt(1, accountNumber);
			ResultSet resultSet = pStatement.executeQuery();
			System.out.println("iam");
			while(resultSet.next()) {
				account = new Account();
				int num = resultSet.getInt("number");
				account.setAccountNumber(num);
				System.out.println(num);
				int bal = resultSet.getInt("balance");
				account.setBalance(bal);
				System.out.println(bal);
			}
		} catch(SQLException e) {
			
		}
		return account;
	}
	
	public void apply(String username) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("insert into application(username) values(?)");
			pStatement.setString(1,username);
			pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
	}
	
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
	
	public void getAllAccounts() {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select users.username,accountusers.accountnumber,account.balance "
					+ "from users,accountusers,account where users.username=accountusers.username and "
					+ "accountusers.accountnumber=account.number "+
					"group by users.username,accountusers.accountnumber,account.balance");
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				String username = resultSet.getString(1);
				int account = resultSet.getInt(2);
				int balance = resultSet.getInt(3);
				System.out.println("Username: "+username+"  Account Number: "+account+"  Balance: "+balance);
			}
		} catch(SQLException e) {
			
		}
		EmployeeMenu eMenu = new EmployeeMenu(connection);
		eMenu.display();
	}
	
	public void deleteApplication(String username) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Delete from applications where username=?");
			pStatement.setString(1, username);
			pStatement.executeQuery();
		}catch(SQLException e) {
			e.getMessage();
		}
	}
	
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
	
}