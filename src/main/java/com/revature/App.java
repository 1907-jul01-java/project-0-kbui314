package com.revature;


/**
 * Hello world! 
 *
 */
public class App {
    public static void main(String[] args) {
    	ConnectionUtil connectionUtil = new ConnectionUtil();
        MainMenu mainMenu = new MainMenu(connectionUtil.getConnection()); 
        mainMenu.display();
        connectionUtil.close();
    	
    }
}
