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
					System.out.println("Viewing Customer's Account Information");
					displayAccountInformation();
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
				System.out.println("This is not a valid input. Please try again.");
				employeeDisplay();
			}
		}
	}
	
	/**
	 * Shows Employee Menu
	 */
	public void employeeDisplay() {
		System.out.println();
		System.out.println("EMPLOYEE MENU");
		System.out.println("-------------");
		System.out.println("1. View Customer's Account Information\n2. View Applications\n3. View Joint Applications\n4. Log Out");
	}
	
	/**
	 * A method that displays all account information. 
	 */
	public void displayAccountInformation() {
		UserDao userDao = new UserDao(connection);
		userDao.getAllAccounts();
		display();
	}
	
	/**
	 * A method that displays all open account applications and
	 * the employee can either accept/deny applications.
	 */
	public void reviewApplications() {
		try {
			UserDao userDao = new UserDao(connection);
			PreparedStatement pStatement = connection.prepareStatement("Select username from application");
			ResultSet resultSet = pStatement.executeQuery();
			int numApplication = 0;
			while(resultSet.next()) {
				numApplication++;
				System.out.println(resultSet.getString("username"));
			}
			if(numApplication == 0) {
				System.out.println("You have no open account application.");
				display();
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
						userDao = new UserDao(connection);
						userDao.insertAccountUser(username, randomAccount);
						userDao.insertAccount(randomAccount);
						userDao.deleteApplication(username);
						System.out.println("Accepted application for an account for "+username+" with account number "+randomAccount);
					}
				}
				if(count == 0) {
					System.out.println("Username input was invalid. Please try again.");
				}
			} else if(input.equals("n")) {
				userDao.deleteApplication(username);
				System.out.println("Application for an account for "+username+" has been denied.");
			} else {
				System.out.println("Accept/Deny input is invalid. Please try again.");
			}
			
		}catch(Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
		display();
	}
	
	/**
	 * A method that displays all open joint account applications and
	 * the employee can either accept/deny applications.
	 */
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
			System.out.println("Do you accept or deny joint application?(y/n)");
			String adInput = in.nextLine();
			resultSet = userDao.getJointApplications();
			count = 0;
			while(resultSet.next()) {
				if(resultSet.getInt("id") == idInput) {
					count++;
					if(adInput.equals("y")) {
						String addUser = resultSet.getString("adduser");
						int accNumber = resultSet.getInt("account");
						userDao.insertAccountUser(addUser, accNumber);
						System.out.println("Added user "+addUser+" to account "+accNumber);
						userDao.deleteJointApplication(idInput);
					} else if(adInput.equals("n")) {
						userDao.deleteJointApplication(idInput);
						System.out.println("Application for an joint account for "+resultSet.getString("username")+
								" and "+resultSet.getString("adduser")+" has been denied.");
					} else {
						System.out.println("Accept/Deny input is invalid. Please try again.");
					}
					
					break;
				}
			}
			if(count == 0) {
				System.out.println("Input for ID is invalid. Please try again.");
			}
			
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
		display();
	}
}
