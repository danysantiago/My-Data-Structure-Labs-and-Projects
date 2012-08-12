package actions;

import interfaces.Action;

import java.util.Scanner;

import classes.DataHandler;

public class AddShareholderAction implements Action {
	
	Scanner in = DataHandler.in;
	private String shareholderName;

	@Override
	public int execute() {
		System.out.println("\nEnter parameters:\n(ShareholderName)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			System.out.println("Added shareholder sucessfully. ID is: " + DataHandler.addShareholder(shareholderName));
			return EXECUTED;
		} else if(validateInput == 0){
			return INVALID_PARAMETER;
		} else {
			return CANCEL;
		}
	}

	@Override
	public int validateInput(String input) {
		if(input.equals("-1"))
			return -1;

		shareholderName = input.trim();
		return 1;
	}

}
