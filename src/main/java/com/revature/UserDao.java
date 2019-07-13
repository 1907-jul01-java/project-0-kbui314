package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public void updateBalance(int accountNumber, int balance) {
		try {
			PreparedStatement pStatement = connection.prepareStatement("Update account set balance=? where number=?");
			pStatement.setInt(1, balance);
			pStatement.setInt(2, accountNumber);
			ResultSet resultSet = pStatement.executeQuery();
		} catch(SQLException e) {
			e.getMessage();
		}
	}
	
}
