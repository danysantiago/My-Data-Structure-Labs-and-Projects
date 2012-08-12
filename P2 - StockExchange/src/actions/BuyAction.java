package actions;

import interfaces.Action;

import java.util.InputMismatchException;
import java.util.Scanner;

import classes.DataHandler;
import exceptions.InsufficientSharesToBuyException;
import exceptions.InvalidCompanyException;
import exceptions.InvalidShareholderException;

public class BuyAction implements Action {
	
	Scanner in = DataHandler.in;
	private int shareholderId;
	private String companySymbol;
	private int amountOfShares;

	@Override
	public int execute() {
		System.out.println("\nEnter parameters (-1 to Cancel):\n(ShareholderId, CompanySymbol, AmountOfShares)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			try {
				String resume = DataHandler.buyShares(shareholderId, companySymbol, amountOfShares);
				System.out.println(resume);
			} catch (InvalidShareholderException e) {
				System.out.println("No such shareholder exists.");
			} catch (InvalidCompanyException e) {
				System.out.println("No such company exists.");
			} catch (InsufficientSharesToBuyException e) {
				System.out.println("Insufficient shares owned by company to buy from.");
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
				if(inputparts.length != 3){
					System.out.println("\nInvalid parameters: Missing parameters.");
					return 0;}
				if(inputparts[1].length() > 5 || inputparts[1].length() < 3){
					System.out.println("\nInvalid parameters: Company Symbol is invalid");
					return 0;}
				Integer.parseInt(inputparts[0].trim());
				Integer.parseInt(inputparts[2].trim());
			}
		} catch (InputMismatchException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		} catch (NumberFormatException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		}
		
		shareholderId = Integer.parseInt(inputparts[0].trim());
		companySymbol = inputparts[1].trim();
		amountOfShares = Integer.parseInt(inputparts[2].trim());
		
		return 1;
	}

}
