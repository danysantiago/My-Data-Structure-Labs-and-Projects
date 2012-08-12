package interfaces;

import options.Option;

public interface Menu {
	void addOption(Option o);
	Option activate(int input);
	void display();
	boolean checkOption(int input);
}
