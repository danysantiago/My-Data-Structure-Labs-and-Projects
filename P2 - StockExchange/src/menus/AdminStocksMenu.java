package menus;

import interfaces.Menu;

import java.util.ArrayList;

import options.Option;
import actions.ExitAction;
import actions.ShowReportOfSharesAction;
import actions.SplitCompanyAction;
import actions.UpdateShareValueAction;

public class AdminStocksMenu implements Menu {

	private ArrayList<Option> options = new ArrayList<Option>();
	
	public AdminStocksMenu(){
		addOption(new Option(new SplitCompanyAction(),"Split a company."));
		addOption(new Option(new UpdateShareValueAction(),"Update share value."));
		addOption(new Option(new ShowReportOfSharesAction(),"View report of shares."));
		addOption(new Option(new ExitAction(), "Exit"));
		
	}

	@Override
	public void addOption(Option o) {
		options.add(o);

	}

	@Override
	public Option activate(int input) {
		return options.get(input - 1);
	}

	@Override
	public void display() {
		System.out.println("\nAdministration of Stocks:");
		for(int i = 0; i < options.size(); i++){
			System.out.println("[" + (i+1) + "] - " + options.get(i).getDescription());
		}
		System.out.print("Enter option: ");
		
	}

	@Override
	public boolean checkOption(int input){
		if(input < 1 || input > options.size()){
			return false;
		}
		
		return true;
	}

}
