package actions;

import interfaces.Action;

public class ExitAction implements Action {

	@Override
	public int execute() {
		return EXIT;
	}

	@Override
	public int validateInput(String input) {
		return EXIT;
	}

}
