package com.revature;

import java.util.Scanner;

public class MainMenu {
	public void display() {
		int input = 0;
		while(input != 3) {
			menu();
			Scanner sc = new Scanner(System.in);
			try {
				input = sc.nextInt();
				switch(input) {
				case 1:
					System.out.println("log in");
					break;
				case 2:
					System.out.println("sign up");
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
	public void menu() {
		System.out.println("1.Log In\n2. Sign Up\n3. Exit");
	}
	
}
