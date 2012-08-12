package menus;

import interfaces.Menu;

import java.util.ArrayList;

import options.Option;
import actions.BuyAction;
import actions.ExitAction;
import actions.SellAction;

public class BuySellMenu implements Menu {

	private ArrayList<Option> options = new ArrayList<Option>();
	
	public BuySellMenu(){
		addOption(new Option(new BuyAction(),"Buy shares."));
		addOption(new Option(new SellAction(),"Sell shares."));
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
		System.out.println("\nBuy & Sell Transactions:");
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
