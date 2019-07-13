package com.revature;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountMenu {
	private String userName;
	private Connection connection;
	public void display() {
		accountMenu();
		int input = 0;
		while(input != 5) {
			try {
				@SuppressWarnings("resource")
				Scanner accScanner = new Scanner(System.in);
				input = accScanner.nextInt();
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
					break;
				case 4:
					System.out.println("4. Apply for Regular Account");
					break;
				case 5:
					System.out.println("5. Apply for Joint Account");
					break;
				case 6:
					System.out.println("logging out");
					MainMenu menu = new MainMenu(connection);
					menu.display();
					break;
				default:
					System.out.println("This is valid menu choice. Try again");
				}
			}catch(Exception e) {
				System.out.println("This is not a valid input.");
			}
		}
		return;
		
	}
	
	public void accountMenu() {
		System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4."
				+ " Apply for Regular Account\n5. Apply for Joint Account\n6. Logout");
	}
	public AccountMenu(String username, Connection connection) {
		this.userName = username;
		this.connection = connection;
	}
	
	public void deposit() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to deposit?");
		try {
			PreparedStatement pStatement = connection.prepareStatement("select accountusers.username as person, account.number as accnumber, account.balance as amount from accountusers, account where accountusers.accountnumber=account.number and accountusers.username=?");
			pStatement.setString(1,userName);
			ResultSet resultSet = pStatement.executeQuery();

			while(resultSet.next()) {
				account = new Account();
				account.setName(resultSet.getString("person"));
				System.out.println(resultSet.getString("person"));
				account.setAccountNumber(resultSet.getInt("accnumber"));
				account.setBalance(resultSet.getInt("amount"));
				accounts.add(account);
			}
			if(accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for(Account temp : accounts) {
				System.out.println(temp.toString());
			}
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the desired account number.");
			int accountSelected = in.nextInt();
			System.out.println("Enter deposit amount.");
			int deposit = in.nextInt();
			for(Account temp : accounts) {
				if(temp.getAccountNumber() == accountSelected) {
					int newAmount = temp.getBalance() + deposit;
					userDao.updateBalance(accountSelected, newAmount);
					System.out.println("Deposited "+deposit+". New balance is "+newAmount);
					break;
				}
			}
			display();
			
			
		} catch (SQLException e) {
			
		}
	}
	
	public void withdraw() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to withdraw?");
		try {
			PreparedStatement pStatement = connection.prepareStatement("select accountusers.username as person, account.number as accnumber, account.balance as amount from accountusers, account where accountusers.accountnumber=account.number and accountusers.username=?");
			pStatement.setString(1,userName);
			ResultSet resultSet = pStatement.executeQuery();

			while(resultSet.next()) {
				account = new Account();
				account.setName(resultSet.getString("person"));
				System.out.println(resultSet.getString("person"));
				account.setAccountNumber(resultSet.getInt("accnumber"));
				account.setBalance(resultSet.getInt("amount"));
				accounts.add(account);
			}
			if(accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for(Account temp : accounts) {
				System.out.println(temp.toString());
			}
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the desired account number.");
			int accountSelected = in.nextInt();
			System.out.println("Enter withdraw amount.");
			int deposit = in.nextInt();
			for(Account temp : accounts) {
				if(temp.getAccountNumber() == accountSelected) {
					int newAmount = temp.getBalance() - deposit;
					userDao.updateBalance(accountSelected, newAmount);
					System.out.println("Withdrawed "+deposit+". New balance is "+newAmount);
					break;
				}
			}
			display();	
		} catch (SQLException e) {	
		}
	}
}
