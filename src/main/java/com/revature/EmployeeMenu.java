package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class EmployeeMenu {
	private Connection connection;

	public EmployeeMenu(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public void display() {
		employeeDisplay();
		
		int input = 0;
		while(input != 4) {
			try {
				Scanner in = new Scanner(System.in);
				input = in.nextInt();
				switch(input) {
				case 1:
					System.out.println("View All Customer Account Information");
					UserDao userDao = new UserDao(connection);
					userDao.getAllAccounts();
					break;
				case 2:
					System.out.println("Viewing Regular Applications");
					reviewApplications();
					break;
				case 3:
					System.out.println("Viewing Joint Applications");
					reviewJointApplications();
				case 4:
					System.out.println("Logging Out...");
					MainMenu menu = new MainMenu(connection);
					menu.display();
					break;
				}
			}catch(Exception e) {
				
			}
		}
	}
	
	public void employeeDisplay() {
		System.out.println();
		System.out.println("EMPLOYEE MENU");
		System.out.println("-------------");
		System.out.println("1. View Customer Accounts\n2. View Applications\n3. View Joint Applications\n4. Log Out");
	}
	
	public void reviewApplications() {
		try {
			
			PreparedStatement pStatement = connection.prepareStatement("Select username from application");
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				System.out.println(resultSet.getString("username"));
			}
			resultSet = pStatement.executeQuery();
			Scanner in = new Scanner(System.in);
			System.out.println("Select a username to accept/deny.");
			String username = in.nextLine();
			System.out.println("Do you accept/deny application?(y/n)");
			String input = in.nextLine();
			if(input.equals("y")) {
				int count = 0;
				while(resultSet.next()) {
					if(resultSet.getString("username").equals(username)) {
						count++;
						Random random = new Random();
						int randomAccount = 100000 + random.nextInt(900000);
						UserDao userDao = new UserDao(connection);
						userDao.insertAccountUser(username, randomAccount);
						userDao.insertAccount(randomAccount);
						userDao.deleteApplication(username);
						System.out.println("Accepted application for an account for "+username+" with account number "+randomAccount);
					}
				}
				if(count == 0) {
					System.out.println("Username input was invalid. Please try again.");
				}
			} else {
				System.out.println("Application for an account for "+username+" has been denied.");
			}
			
		}catch(SQLException e) {
			
		}
		display();
	}
	
	public void reviewJointApplications() {
		UserDao userDao = new UserDao(connection);
		ResultSet resultSet = userDao.getJointApplications();
		try {
			int count = 0;
			while(resultSet.next()) {
				count++;
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String account = resultSet.getString("account");
				String addUser = resultSet.getString("adduser");
				System.out.println("ID: "+id+" User: "+username+" Account: "+account+" User to add: "+addUser);
			}
			if(count == 0) {
				System.out.println("No joint account applications are open.");
				display();
			}
			System.out.println("Select a joint application based on ID.");
			Scanner in = new Scanner(System.in);
			int idInput;
			try {
				idInput = Integer.parseInt(in.nextLine().split(" ")[0]);
			} catch (Exception e) {
				idInput = 0;
			}
			resultSet = userDao.getJointApplications();
			count = 0;
			while(resultSet.next()) {
				if(resultSet.getInt("id") == idInput) {
					count++;
					String addUser = resultSet.getString("adduser");
					int accNumber = resultSet.getInt("account");
					userDao.insertAccountUser(addUser, accNumber);
					System.out.println("Added user "+addUser+" to account "+accNumber);
					break;
				}
			}
			
		} catch (SQLException e) {
			e.getMessage();
		}
		display();
	}
}
