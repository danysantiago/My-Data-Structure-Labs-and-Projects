package menus;

import java.io.IOException;

import interfaces.Menu;
import classes.FileHandler;

public class MainMenu {
	private String menuStrings[] = new String[5];
	
	public MainMenu(){
		menuStrings[0] = "[1] - Administration of Companies";
		menuStrings[1] = "[2] - Administration of Shareholders";
		menuStrings[2] = "[3] - Administration of Stocks";
		menuStrings[3] = "[4] - Buy & Sell Transactions";
		menuStrings[4] = "[5] - Exit";
		
	}

	public void display() {
		System.out.println("\nMain Menu:");
		for(String submenu : menuStrings){
			System.out.println(submenu);
		}
		
		System.out.print("Select option: ");
		
	}
	
	public boolean checkOption(int input){
		if(input < 1 || input > menuStrings.length){
			return false;
		}
		
		return true;
	}

	public Menu getSubMenu(int input) {
		switch(input){
		case 1:
			return new AdminCompaniesMenu();
		case 2:
			return new AdminShareholderMenu();
		case 3:
			return new AdminStocksMenu();
		case 4:
			return new BuySellMenu();
		default:
			try {
				FileHandler.writeAllData();
			} catch (IOException e) {
				System.out.println("Exited with IO Errors");
			}
			System.exit(0);
			return null;
		}
	}
	

}
