package actions;

import interfaces.Action;

import java.util.InputMismatchException;
import java.util.Scanner;

import classes.DataHandler;
import exceptions.InvalidShareholderException;

public class ShowReportOfShareholderAction implements Action {
	
	Scanner in = DataHandler.in;
	private int shareholderId;

	@Override
	public int execute(){
		System.out.println("\nEnter parameters (-1 to Cancel):\n(ShareholderId)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			try {
				System.out.println(DataHandler.showReportOfShareholder(shareholderId));
			} catch (InvalidShareholderException e) {
				System.out.println("No such shareholder exists.");
			}
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
		
		try {
			shareholderId = Integer.parseInt(input.trim());
		} catch (InputMismatchException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		} catch (NumberFormatException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		}
		
		return 1;
	}

}
