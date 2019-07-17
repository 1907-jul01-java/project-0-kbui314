package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import entities.UserDao;
import models.User;

/**
 * 
 * Shows the customer's main menu.
 *
 */
public class MainMenu {
	Connection connection;
	public void display() {
		
		int input = 0;
		while(input != 3) {
			menu();
			Scanner sc = new Scanner(System.in);
			try {
				input = sc.nextInt();
				switch(input) {
				case 1:
					System.out.println("Logging In...");
					login();
					break;
				case 2:
					System.out.println("Sign Up");
					signUp();
					break;
				case 3:
					sc.close();
					System.out.println("Exiting...");
					System.exit(0);
					break;
				default:
					System.out.println("Not a valid menu option. Please try again!");
				}
				
			}catch(Exception e) {
				System.out.println("This is not a valid input");
			}
		}
	}
	
	/**
	 * Shows the main menu.
	 */
	public void menu() {
		System.out.println();
		System.out.println("MAIN MENU");
		System.out.println("---------");
		System.out.println("1. Log In\n2. Sign Up\n3. Exit");
	}
	
	public MainMenu(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * A method that creates a new user base on the user's inputed user
	 * and password.
	 */
	public void signUp() {
		try {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			
			System.out.println("Provide a username.");
			String username = in.nextLine();
			System.out.println("Provide a password.");
			String password = in.nextLine();
			boolean bool = hasUser(username, password);
			
			UserDao userDao = new UserDao(connection);
			
			if(!bool) {
				userDao.insert(new User(username,password,"customer"));
				System.out.println("Sign up successful. You may now log in.");
			}
			
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			display();
		}
	}
	
	/**
	 * A method that logs into the customer's account.
	 */
	public void login() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		try {
			
			System.out.println("Provide a username.");
			String username = in.nextLine();
			
			System.out.println("Provide a password.");
			String password = in.nextLine();
			boolean bool = authenticateUser(username,password);
			if(bool) {
				AccountMenu accountMenu = new AccountMenu(username, connection);
				accountMenu.display();
			}
		}catch(Exception e) {
			
		}
	}
	
	/**
	 * A method that checks if the specified username is taken. If it is taken,
	 * then return true. If it is not taken, then return false.
	 * @param username
	 * @param password
	 * @return
	 */
	public Boolean hasUser(String username, String password) {
		
		int i = 0;
		char[] usernameCharacter = username.toCharArray();
		char passwordCharacter = password.charAt(0);
		boolean valid = true;
		try {
			if(username.isEmpty()) {
				System.out.println("Cannot have an empty username. Please try again.");
				return true;
			}
			else if(Character.isDigit(username.charAt(0))) {
				System.out.println("Usernames cannot start with a digit. Please try again.");
				return true;
			} else if(passwordCharacter == ' ') {
				System.out.println("Invalid start of a password. Please try again");
				return true;
			}
			for(char c : usernameCharacter) {
				valid = ((c >= 'a') && (c <='z')) ||
						((c >= 'A')&&(c <= 'Z')) ||
						((c >= '0')&&(c <= '9'));
				if(!valid) {
					System.out.println("Doesn't contain valid characters. Please try again.");
					return true;
				}
			}
			PreparedStatement pStatement = connection.prepareStatement("Select username from users where username=?");
			pStatement.setString(1, username);
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				i++;
			}
			if(i == 1) {
				System.out.println("Username is already taken. Please try again.");
				return true;
			}
			if(password.isEmpty()) {
				System.out.println("Cannot have an empty password.");
				return true;
			}
			
		} catch(SQLException e) {
			
		}
		return false;
	}
	
	/**
	 * A method that checks if the user exist and if the provided password is the
	 * same as the password associated with the username. If it is valid, then
	 * return true.
	 * @param username
	 * @param password
	 * @return
	 */
	public Boolean authenticateUser(String username, String password) {
		int i = 0;
		try {
			PreparedStatement pStatement = connection.prepareStatement("select username,password,category from users where username=? and password=?");
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				
				i++;
			}
			
			if(i == 1) {
				System.out.println("Login successful.");
				return true;
			}
			
		} catch(SQLException e) {
			e.getMessage();
		}
		System.out.println("Either username and/or password is invalid. Please try again.");
		return false;
	}
}
