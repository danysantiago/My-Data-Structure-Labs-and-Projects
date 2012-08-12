package menus;

import interfaces.Menu;

import java.util.ArrayList;

import options.Option;
import actions.AddCompanyAction;
import actions.AddShareToCompanyAction;
import actions.ExitAction;
import actions.RemoveCompanyAction;
import actions.ShowReportOfCompanyAction;

public class AdminCompaniesMenu implements Menu {
	
	private ArrayList<Option> options = new ArrayList<Option>();
	
	public AdminCompaniesMenu(){
		addOption(new Option(new AddCompanyAction(),"Add new company."));
		addOption(new Option(new RemoveCompanyAction(),"Remove an existing company."));
		addOption(new Option(new AddShareToCompanyAction(),"Add share to a company."));
		addOption(new Option(new ShowReportOfCompanyAction(),"View report of a company."));
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
		System.out.println("\nAdministration of Companies:");
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
