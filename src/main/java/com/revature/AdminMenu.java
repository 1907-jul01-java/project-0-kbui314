package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AdminMenu {
	private Connection connection;

	public AdminMenu(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public void display() {
		adminMenu();
		int input = 0;
		while(input != 8) {
			try {
				Scanner in = new Scanner(System.in);
				input = in.nextInt();
				switch(input) {
				case 1:
					System.out.println("1. Deposit");
					deposit();
					break;
				case 2:
					System.out.println("2. Withdraw");
					withdraw();
					break;
				case 3:
					System.out.println("3. Transfer");
					transfer();
					break;
				case 4:
					System.out.println("Viewing Regular Applications");
					reviewApplications();
					break;
				case 5:
					System.out.println("Viewing Joint Applications");
					reviewJointApplications();
					break;
				case 6:
					System.out.println("Viewing Customer's Account Information...");
					displayAccountInformation();
					break;
				case 7:
					System.out.println("Deleting Account");
					deleteAccount();
					break;
				case 8:
					System.out.println("Logging Out...");
					MainMenu menu = new MainMenu(connection);
					menu.display();
					break;
				}
			}catch(Exception e) {
				System.out.println("This is not a valid input. Please try again.");
				adminMenu();
			}
		}
	}
	
	public void adminMenu() {
		System.out.println();
		System.out.println("ADMINISTRATOR MENU");
		System.out.println("------------------");
		System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. View Applications\n5. View "
				+ "Joint Applications\n6. View Customer's Account Information\n7. Delete Account\n8. Log Out");
	}
	
	public void deposit() {
			Account account = new Account();
			List<Account> accounts = new ArrayList<>();
			UserDao userDao = new UserDao(connection);
			System.out.println("Which account do you want to deposit?");
			try {
				PreparedStatement pStatement = connection.prepareStatement("select * from account");
				ResultSet resultSet = pStatement.executeQuery();
				
				while(resultSet.next()) {
					account = new Account();
					account.setAccountNumber(resultSet.getInt("number"));
					account.setBalance(resultSet.getInt("balance"));
					accounts.add(account);
				}
				if(accounts.size() == 0) {
					System.out.println("You don't have any open accounts");
					display();
				}
				for(Account temp : accounts) {
					int accountNumber = temp.getAccountNumber();
					int balance = temp.getBalance();
					System.out.println("Account: "+accountNumber+" Balance: "+balance);
				}
				Scanner in = new Scanner(System.in);
				System.out.println("Enter the desired account number.");
				int accountSelected = in.nextInt();
				System.out.println("Enter deposit amount.");
				int amount = in.nextInt();
				if (amount < 0) {
					System.out.println("Deposit input is invalid. Please try again.");
					display();
				}
				int count = 0;
				for(Account temp : accounts) {
					if(temp.getAccountNumber() == accountSelected) {
						count++;
						int newAmount = temp.getBalance() + amount;
						userDao.updateBalance(accountSelected, newAmount);
						System.out.println("Deposited "+amount+". New balance is "+newAmount);
						break;
					}
				}
				if(count == 0) {
					System.out.println("Account number input is invalid. Please try again.");
					display();
				}
				display();
		}catch(Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
		display();
	}
	
	public void withdraw() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to deposit?");
		try {
			PreparedStatement pStatement = connection.prepareStatement("select * from account");
			ResultSet resultSet = pStatement.executeQuery();
			
			while(resultSet.next()) {				
				account = new Account();
				account.setAccountNumber(resultSet.getInt("number"));
				account.setBalance(resultSet.getInt("balance"));
				accounts.add(account);
			}
			if(accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for(Account temp : accounts) {
				int accountNumber = temp.getAccountNumber();
				int balance = temp.getBalance();
				System.out.println("Account: "+accountNumber+" Balance: "+balance);
			}
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the desired account number.");
			int accountSelected = in.nextInt();
			System.out.println("Enter withdraw amount.");
			int withdraw = in.nextInt();
			if (withdraw < 0) {
				System.out.println("Withdraw input is invalid. Please try again.");
				display();
			}
			int count = 0;
			for(Account temp : accounts) {
				if(temp.getAccountNumber() == accountSelected) {
					count++;
					int newAmount = temp.getBalance() - withdraw;
					if(newAmount < 0) {
						System.out.println("Insufficient funds.");
						break;
					}
					userDao.updateBalance(accountSelected, newAmount);
					System.out.println("Withdrawed "+withdraw+". New balance is "+newAmount);
					break;
				}
			}
			if(count == 0) {
				System.out.println("Account number input is invalid. Please try again.");
				display();
			}
			display();
	}catch(Exception e) {
		System.out.println("Invalid input. Please try again.");
		display();
	}
	display();
	}
	
	public void transfer() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to tranfer money from?");
		try {
			PreparedStatement pStatement = connection.prepareStatement("Select * from account");
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				account = new Account();
				account.setAccountNumber(resultSet.getInt("number"));
				account.setBalance(resultSet.getInt("balance"));
				accounts.add(account);
			}
			if(accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for(Account temp : accounts) {
				int accountNumber = temp.getAccountNumber();
				int balance = temp.getBalance();
				System.out.println("Account: "+accountNumber+" Balance: "+balance);
			}
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the account number to transfer from.");
			int accountFrom = in.nextInt();
			System.out.println("Enter transfer amount.");
			int amount = in.nextInt();
			System.out.println("Enter account number to transfer to.");
			int accountTo = in.nextInt();
			if (amount < 0) {
				System.out.println("Input amount is invalid. Please try again.");
				display();
			}
			int count = 0;
			for(Account temp : accounts) {
				if(temp.getAccountNumber() == accountFrom) {
					count++;
					PreparedStatement pState = connection.prepareStatement("Select number,balance from account where number=?");
					pState.setInt(1, accountTo);
					resultSet = pState.executeQuery();
					if(resultSet.next() == false) {
						System.out.println("Account number to transfer to doesn't exist. Please try again.");
						display();
					}else {
						int newAmount = temp.getBalance() - amount;
						if(newAmount < 0) {
							System.out.println("Insufficient funds.");
							break;
						}
						userDao.updateBalance(accountFrom, newAmount);
						System.out.println("Transfered "+amount+". New balance is "+newAmount);
						do {
							account = new Account();
							account.setAccountNumber(resultSet.getInt("number"));
							account.setBalance(resultSet.getInt("balance"));
						}while(resultSet.next());
						newAmount = account.getBalance() + amount;
						userDao.updateBalance(accountTo, newAmount);
						System.out.println("Transfer complete.");
						display();
					}
					break;
				}
			}
			if(count == 0) {
				System.out.println("Account tranferring from doesn't exist. Please try again.");
			}
			display();
		} catch(Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
		display();
	}
	
	public void reviewApplications() {
		try {
			UserDao userDao = new UserDao(connection);
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
						userDao = new UserDao(connection);
						userDao.insertAccountUser(username, randomAccount);
						userDao.insertAccount(randomAccount);
						userDao.deleteApplication(username);
						System.out.println("Accepted application for an account for "+username+" with account number "+randomAccount);
					}
				}
				if(count == 0) {
					System.out.println("Username input doesn't exist. Please try again.");
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
	
	public void displayAccountInformation() {
		UserDao userDao = new UserDao(connection);
		userDao.getAllAccounts();
		display();
	}
	
	public void deleteAccount() {
		UserDao userDao = new UserDao(connection);
		System.out.println("Enter account number to delete.");
		Scanner in = new Scanner(System.in);
		int accountInput;
		try {
			try {
				accountInput = Integer.parseInt(in.nextLine().split(" ")[0]);
			} catch (NumberFormatException e) {
				accountInput = 0;
			}
			
			ResultSet resultSet = userDao.getAccount(accountInput);
			if(resultSet.next() == false) {
				System.out.println("Selected account doesn't exist or invalid.");
				display();
			}else {
				userDao.deleteAccount(accountInput);
				System.out.println("Successfully deleted account "+accountInput);
				display();
			}
			System.out.println("hello");
			
		}catch(Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
		display();
	}
}
