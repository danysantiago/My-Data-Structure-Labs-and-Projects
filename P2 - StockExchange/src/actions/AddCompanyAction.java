package actions;

import interfaces.Action;

import java.util.InputMismatchException;
import java.util.Scanner;

import classes.Company;
import classes.DataHandler;
import exceptions.DuplicateCompanyException;

public class AddCompanyAction implements Action {
	
	private Scanner in = DataHandler.in;
	private String companyName;
	private String companySymbol;
	private int amountOfShares;
	private double sharePrice;

	@Override
	public int execute() {
		System.out.println("\nEnter parameters (-1 to Cancel):\n(CompanyName, Symbol, AmountOfShares, SharePrice)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			Company c = new Company(companyName, companySymbol, amountOfShares, sharePrice);
			try {
				DataHandler.addCompany(c);
				System.out.println("Company added sucessfully");
			} catch (DuplicateCompanyException e) {
				System.out.println("Unable to add duplicated company.");
			}
			return EXECUTED;
		} else if(validateInput == 0){
			return INVALID_PARAMETER;
		} else {
			return CANCEL;
		}
	}

	public int validateInput(String input) {
		String[] inputparts;
		try {
			if(input.equals("-1")){
				return -1;
			} else {
				inputparts = input.split(",");
				if(inputparts.length != 4){
					System.out.println("\nInvalid parameters: Missing parameters.");
					return 0;}
				if(inputparts[1].length() > 5 || inputparts[1].length() < 3){
					System.out.println("\nInvalid parameters: Company Symbol is invalid");
					return 0;}
				if(Integer.parseInt(inputparts[2].trim()) < 10000){
					System.out.println("\nInvalid parameters: AmountOfShares must be greater than 10,000");
					return 0;}
				if(Double.parseDouble(inputparts[3].trim()) < 0){
					System.out.println("\nInvalid parameters: SharePrice cannot be negative.");
					return 0;}
			}
		} catch (InputMismatchException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		} catch (NumberFormatException e){
			System.out.println("\nInvalid parameters.");
			return 0;
		}
		
		companyName = inputparts[0].trim();
		companySymbol = inputparts[1].trim();
		amountOfShares = Integer.parseInt(inputparts[2].trim());
		sharePrice = Double.parseDouble(inputparts[3].trim());
		
		return 1;
	}

}
