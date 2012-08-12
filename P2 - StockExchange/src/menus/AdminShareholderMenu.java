package menus;

import interfaces.Menu;

import java.util.ArrayList;

import options.Option;
import actions.AddShareholderAction;
import actions.ExitAction;
import actions.RemoveShareholderAction;
import actions.ShowReportOfShareholderAction;

public class AdminShareholderMenu implements Menu {

	private ArrayList<Option> options = new ArrayList<Option>();
	
	public AdminShareholderMenu(){
		addOption(new Option(new AddShareholderAction(),"Add new shareholder."));
		addOption(new Option(new RemoveShareholderAction(),"Remove an existing shareholder."));
		addOption(new Option(new ShowReportOfShareholderAction(),"View report of a shareholder."));
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
		System.out.println("\nAdministration of Shareholders:");
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
