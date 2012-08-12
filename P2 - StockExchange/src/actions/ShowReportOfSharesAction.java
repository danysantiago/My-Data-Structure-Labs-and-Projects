package actions;

import interfaces.Action;

import java.util.Scanner;

import classes.DataHandler;
import exceptions.InvalidCompanyException;

public class ShowReportOfSharesAction implements Action {
	
	Scanner in = DataHandler.in;
	private String companySymbol;

	public int execute() {
		System.out.println("\nEnter parameters (-1 to Cancel):\n(CompanySymbol)");
		int validateInput = validateInput(in.nextLine());
		if(validateInput == 1){
			try {
				System.out.println(DataHandler.showReportOfShare(companySymbol));
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
