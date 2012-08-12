import interfaces.Action;
import interfaces.Menu;

import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import menus.MainMenu;
import GUI.MainMenuWindow;
import classes.DataHandler;
import classes.FileHandler;




public class Main {
	
	public static MainMenuWindow window;

	public static void main(String[] args) {

		try {
			FileHandler.readAllData();
		} catch (NumberFormatException e) {
			FileHandler.formatAllFiles();
			System.out.println("Files found corrupted.\nFile has been formated, all data has been lost.");
		} catch (ClassNotFoundException e) {
			FileHandler.formatAllFiles();
			System.out.println("Files found corrupted.\nFile has been formated, all data has been lost.");
		} catch (IOException e) {
			System.out.println("Unable to read files.\nPLease make sure files are not opened by another program.");
			System.exit(0);
		} catch (ParseException e) {
			FileHandler.formatAllFiles();
			System.out.println("Files found corrupted.\nFile has been formated, all data has been lost.");
		}


		if(args.length > 0){
			if(args[0].equals("-c"))
				startConsole();
			else {
				System.out.println("Unknown parameter. Sarting in GUI Mode");
				startGUI();
			}
				
		} else {
			startGUI();
		}
	}

	private static void startConsole() {
		
		final String invalidOptionString = "\nInvalid option, try again.";

		Scanner in = DataHandler.in;
		int input;
		int returnVal = 1;
		MainMenu mainMenu = new MainMenu();
		Menu submenu;

		do{
			try{
				mainMenu.display();
				input = in.nextInt();
				if(mainMenu.checkOption(input)){
					submenu = mainMenu.getSubMenu(input);
					do{
						try{
							submenu.display();
							input = in.nextInt();
							in.nextLine();
							if(submenu.checkOption(input)){
								do{
									returnVal = submenu.activate(input).getaction().execute();
								} while(returnVal == Action.INVALID_PARAMETER);
							} else {
								returnVal = 1;
								System.out.println(invalidOptionString);
							}
						} catch (InputMismatchException e){
							returnVal = 1;
							System.out.println(invalidOptionString);
							in.nextLine();
						}

					} while (returnVal != Action.EXIT);
				} else {
					System.out.println(invalidOptionString);
				}
			} catch (InputMismatchException e){
				System.out.println(invalidOptionString);
				in.nextLine();
			}
		} while (true);
	}

	private static void startGUI() {
		System.out.println("Starting in GUI Mode.");
		window = new MainMenuWindow();
	}

}
