package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.UserDao;
import models.Account;

public class AccountMenu {
	private String userName;
	// private String category;
	private Connection connection;

	public void display() {
		UserDao userDao = new UserDao(connection);
		String category = userDao.getCategory(userName);
		if (category.equals("employee")) {
			EmployeeMenu emMenu = new EmployeeMenu(connection);
			emMenu.display();
		} else if (category.equals("admin")) {
			AdminMenu adminMenu = new AdminMenu(connection);
			adminMenu.display();
		} else {
			accountMenu();
			int input = 0;
			while (input != 5) {
				Scanner accScanner = new Scanner(System.in);
				try {
					input = accScanner.nextInt();
					switch (input) {
					case 1:
						System.out.println("1. Depositing");
						System.out.println();
						deposit();
						break;
					case 2:
						System.out.println("2. Withdrawing");
						System.out.println();
						withdraw();
						break;
					case 3:
						System.out.println("3. Transferring");
						System.out.println();
						transfer();
						break;
					case 4:
						System.out.println("4. Applying for Regular Account");
						System.out.println();
						application();
						break;
					case 5:
						System.out.println("5. Applying for Joint Account");
						System.out.println();
						joint();
						break;
					case 6:
						System.out.println("Logging out");
						MainMenu menu = new MainMenu(connection);
						menu.display();
						break;
					default:
						System.out.println("This is not a valid menu choice. Try again");
					}

				} catch (Exception e) {
					System.out.println("This is not a valid input. Please try again.");
					accountMenu();
				}
			}
		}
		return;
	}

	/**
	 * Shows the account menu of customers.
	 */
	public void accountMenu() {
		System.out.println();
		System.out.println("ACCOUNT MENU");
		System.out.println("------------");
		System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4."
				+ " Apply for Regular Account\n5. Apply for Joint Account\n6. Logout");
	}

	public AccountMenu(String username, Connection connection) {
		this.userName = username;
		// this.category = category;
		this.connection = connection;
	}

	/**
	 * A method that deposits into the user's account.
	 */
	public void deposit() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to deposit?");
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"select accountusers.username as person, account.number as accnumber, account.balance as amount from accountusers, account where accountusers.accountnumber=account.number and accountusers.username=?");
			pStatement.setString(1, userName);
			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				account = new Account();
				account.setName(resultSet.getString("person"));
				account.setAccountNumber(resultSet.getInt("accnumber"));
				account.setBalance(resultSet.getInt("amount"));
				accounts.add(account);
			}
			if (accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for (Account temp : accounts) {
				System.out.println(temp.toString());
			}
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the desired account number.");
			int accountSelected = in.nextInt();
			System.out.println("Enter deposit amount.");
			int amount = in.nextInt();
			if (amount < 0) {
				System.out.println("Input amount is invalid. Please try again.");
				display();
			}
			int count = 0;
			for (Account temp : accounts) {
				if (temp.getAccountNumber() == accountSelected) {
					count++;
					int newAmount = temp.getBalance() + amount;
					userDao.updateBalance(accountSelected, newAmount);
					System.out.println("Deposited " + amount + ". New balance is " + newAmount);
					break;
				}
			}
			if (count == 0) {
				System.out.println("Account Number doesn't exist. Please try again.");
			}
			display();

		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
	}

	/**
	 * A method that withdraws from the user's account.
	 */
	public void withdraw() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to withdraw?");
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"select accountusers.username as person, account.number as accnumber, account.balance as amount from accountusers, account where accountusers.accountnumber=account.number and accountusers.username=?");
			pStatement.setString(1, userName);
			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				account = new Account();
				account.setName(resultSet.getString("person"));
				account.setAccountNumber(resultSet.getInt("accnumber"));
				account.setBalance(resultSet.getInt("amount"));
				accounts.add(account);
			}
			if (accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for (Account temp : accounts) {
				System.out.println(temp.toString());
			}
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the desired account number.");
			int accountSelected = in.nextInt();
			System.out.println("Enter withdraw amount.");
			int amount = in.nextInt();
			if (amount < 0) {
				System.out.println("Input amount is invalid. Please try again.");
				display();
			}
			int count = 0;
			for (Account temp : accounts) {
				if (temp.getAccountNumber() == accountSelected) {
					count++;
					int newAmount = temp.getBalance() - amount;
					if(newAmount < 0) {
						System.out.println("Insufficient funds.");
						break;
					}
					userDao.updateBalance(accountSelected, newAmount);
					System.out.println("Withdrawed " + amount + ". New balance is " + newAmount);
					break;
				}
			}
			if(count == 0) {
				System.out.println("Account doesn't exist. Please try again.");
			}
			display();
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
	}

	/**
	 * A method that transfers from the user's account to another account.
	 */
	public void transfer() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		System.out.println("Which account do you want to tranfer money from?");
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"select accountusers.username as person, account.number as accnumber, account.balance as amounts from accountusers, account where accountusers.accountnumber=account.number and accountusers.username=?");
			pStatement.setString(1, userName);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				account = new Account();
				account.setName(resultSet.getString("person"));
				account.setAccountNumber(resultSet.getInt("accnumber"));
				account.setBalance(resultSet.getInt("amounts"));
				accounts.add(account);
			}
			if (accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for (Account temp : accounts) {
				System.out.println(temp.toString());
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
			for (Account temp : accounts) {
				if (temp.getAccountNumber() == accountFrom) {
					count++;
					PreparedStatement pState = connection
							.prepareStatement("Select number,balance from account where number=?");
					pState.setInt(1, accountTo);
					resultSet = pState.executeQuery();
					if (resultSet.next() == false) {
						System.out.println("Account number to transfer to doesn't exist. Please try again.");
						display();
					} else {
						int newAmount = temp.getBalance() - amount;
						if(newAmount < 0) {
							System.out.println("Insufficient funds.");
							break;
						}
						userDao.updateBalance(accountFrom, newAmount);
						System.out.println("Transfered " + amount + ". New balance is " + newAmount);
						do {
							account = new Account();
							account.setAccountNumber(resultSet.getInt("number"));
							account.setBalance(resultSet.getInt("balance"));
						} while (resultSet.next());
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
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
	}

	/**
	 * A method that sends an application to open an account.
	 */
	public void application() {
		UserDao userDao = new UserDao(connection);
		userDao.apply(userName);
		System.out.println("Application to open account sent.");
		display();
	}

	/**
	 * A method that sends an application to open a joint account.
	 */
	@SuppressWarnings("resource")
	public void joint() {
		Account account = new Account();
		List<Account> accounts = new ArrayList<>();
		UserDao userDao = new UserDao(connection);
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"select accountusers.username as person, account.number as accnumber, account.balance as amounts from accountusers, account where accountusers.accountnumber=account.number and accountusers.username=?");
			pStatement.setString(1, userName);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				account = new Account();
				account.setName(resultSet.getString("person"));
				account.setAccountNumber(resultSet.getInt("accnumber"));
				account.setBalance(resultSet.getInt("amounts"));
				accounts.add(account);
			}
			if (accounts.size() == 0) {
				System.out.println("You don't have any open accounts");
				display();
			}
			for (Account temp : accounts) {
				System.out.println(temp.toString());
			}
			System.out.println("Select account to do a joint account.");
			resultSet = pStatement.executeQuery();
			Scanner in = new Scanner(System.in);
			int accountInput;
			try {
				accountInput = Integer.parseInt(in.nextLine().split(" ")[0]);
			} catch (NumberFormatException e) {
				accountInput = 0;
			}
			System.out.println("Input username to add access to account.");
			String addUser = in.nextLine();
			int count = 0;
			while (resultSet.next()) {
				if (resultSet.getInt("accnumber") == accountInput) {
					count++;
					resultSet = userDao.getUser(addUser);
					if (resultSet.next() == false) {
						System.out.println("Username doesn't exist. Please try again.");
						display();
					} else {
						userDao.insertJointApplication(userName, accountInput, addUser);
						System.out.println("Application for joint account sent.");
					}
				}
			}
			if (count == 0) {
				System.out.println("Not a valid account number. Please try again.");
				display();
			}

		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
		display();
	}
}
