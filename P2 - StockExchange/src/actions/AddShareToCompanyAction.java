package actions;

import interfaces.Action;

import java.util.InputMismatchException;
import java.util.Scanner;

import classes.DataHandler;
import exceptions.InvalidCompanyException;


public class AddShareToCompanyAction implements Action {
	
	private Scanner in = DataHandler.in;
	private String companySymbol;
	private int amountOfShares;

	@Override
	public int execute() {
		System.out.println("\nEnter parameters (-1 to Cancel):\n(Symbol, AmountOfShares)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			try {
				int addedShares = DataHandler.addShareToCompany(companySymbol, amountOfShares);
				System.out.println("Shares added sucessfully, new amount: " + addedShares);
			} catch (InvalidCompanyException e) {
				System.out.println("No such company exists.");
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
		String[] inputparts;
		try {
			if(input.equals("-1")){
				return -1;
			} else {
				inputparts = input.split(",");
				if(inputparts.length != 2){
					System.out.println("\nInvalid parameters: Missing parameters.");
					return 0;}
				if(inputparts[0].length() > 5 || inputparts[0].length() < 3){
					System.out.println("\nInvalid parameters: Company Symbol is invalid.");
					return 0;}
				if(Integer.parseInt(inputparts[1].trim()) < 1){
					System.out.println("\nInvalid parameters: amount must be greater than zero.");
					return 0;}
			}
		} catch (InputMismatchException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		} catch (NumberFormatException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		}
		
		companySymbol = inputparts[0].trim();
		amountOfShares = Integer.parseInt(inputparts[1].trim());
		
		return 1;
	}

}
