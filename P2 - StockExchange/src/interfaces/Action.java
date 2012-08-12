package interfaces;

public interface Action {
	int EXIT = -2;
	int CANCEL = -1;
	int INVALID_PARAMETER = 0;
	int EXECUTED = 1;
	
	int execute();
	int validateInput(String input);
}
