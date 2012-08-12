package actions;

import interfaces.Action;

import java.util.Scanner;

import classes.DataHandler;
import exceptions.InvalidCompanyException;
import exceptions.UnsoldSharesOfException;

public class RemoveCompanyAction implements Action {
	
	private Scanner in = DataHandler.in;
	
	private String companySymbol;

	@Override
	public int execute() {
		System.out.println("\nEnter parameters (-1 to Cancel):\n(CompanySymbol)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			try {
				DataHandler.removeCompany(companySymbol);
				System.out.println("Company removed sucessfully");
			} catch (InvalidCompanyException e) {
				System.out.println("No such company exists.");
			} catch (UnsoldSharesOfException e) {
				System.out.println("Cannot remove company with sold shares.");
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
		if(input.equals("-1")){
			return -1;
		} else if(input.length() < 3 || input.length() > 5){
			System.out.println("\nInvalid parameters: Company Symbol is invalid.");
			return 0;
		}

		companySymbol = input.trim();
		return 1;
	}

}
