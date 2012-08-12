package actions;

import interfaces.Action;

import java.util.InputMismatchException;
import java.util.Scanner;

import classes.DataHandler;
import exceptions.InvalidShareholderException;
import exceptions.UnsoldSharesOfException;

public class RemoveShareholderAction implements Action {

	Scanner in = DataHandler.in;
	private int shareholderId;
	
	@Override
	public int execute() {
		System.out.println("\nEnter parameters (-1 to Cancel):\n(ShareholderId)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			try {
				DataHandler.removeShareholder(shareholderId);
				System.out.println("Shareholder removed sucessfully.");
			} catch (InvalidShareholderException e) {
				System.out.println("No such shareholder exists.");
			} catch (UnsoldSharesOfException e) {
				System.out.println("Cannot remove shareholder with unsold shares.");
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
