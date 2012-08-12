package options;

import interfaces.Action;

public class Option{
	String description;
	Action action;
	
	public Option(Action a, String d){
		description = d;
		action = a;
	}

	public String getDescription() {
		return description;
	}

	public Action getaction() {
		return action;
	}

}
