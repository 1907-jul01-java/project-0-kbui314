package com.revature;

import utils.ConnectionUtil;
import views.MainMenu;

/**
 * 
 * This is a application that simulates real-world behaviors of 
 * a bank system. 
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
