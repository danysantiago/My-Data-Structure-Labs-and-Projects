
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.Utils;

import exceptions.InvalidCommandException;
import exceptions.InvalidOperandException;
import exceptions.MissingOperandException;


/**
 * InputValidator class used to validate the operands and the commands.
 * This validator only handles syntax errors, (ie a missing operand) any other
 * error is handled by the execution of the command (ie "No such file" while executing 'cat')
 * @author Dany
 *
 */
public class InputValidator {
	
	//Max length of any name string used in the program
	public static final int MAX_NAME_LENGTH = Utils.MAX_NAME_SIZE;
	
	private static Pattern wordCharPattern = Pattern.compile("\\w*"); //Word char pattern regex
	
	
	/**
	 * Validate method used by the Shell
	 * @param inputLine input line captured by the Shell
	 * @return the command's index in the commandList
	 * @throws InvalidCommandException if command dosen't exists
	 * @throws MissingOperandException if there is a missing operand
	 * @throws InvalidOperandException if an operand is invalid (ie number only operands), or there are too many operands 
	 */
	public static int validate(String inputLine) 
			throws InvalidCommandException, MissingOperandException, InvalidOperandException{
		//Splits the input by the whitespaces
		String[] inputArray = inputLine.split("\\s+");
		//Find the command in the coomand list
		int commandIndex = getCommandIndex(inputArray[0]);
		
		//If such command exists it's index will NOT bet -1 and thus we can call
		//the validateCommand method, if such method throw an exception
		//this method forwards it back to the Shell so display the correct message
		if(commandIndex == -1)
			throw new InvalidCommandException();
		
		validateCommand(inputArray,commandIndex);
		return commandIndex;
	}
	
	
	/**
	 * Command validate method
	 * @param inputArray commands and it's operands
	 * @param commandIndex command index in the command list
	 * @throws MissingOperandException if the command is missing an operand
	 * @throws InvalidOperandException if the command has an invalid operand (ie number only operands), or there are too many operands 
	 */
	private static void validateCommand(String[] inputArray, int commandIndex) 
			throws MissingOperandException, InvalidOperandException {
		Matcher matcher; //Matcher used for regex
		
		//The BIG SWITCH
		//Based upon the command index validate it's input
		//and validate the amount of opeand with the checkForAmountOperands method
		switch(commandIndex){
		case Commands.CM_CAT:
			checkForAmountOperands(inputArray, 1);
			break;
		case Commands.CM_CD:
			checkForAmountOperands(inputArray, 1);
			break;
		case Commands.CM_CP:
			checkForAmountOperands(inputArray, 2);
			if(inputArray[2].length() > MAX_NAME_LENGTH){
				throw new InvalidOperandException(", Name must be less than " + MAX_NAME_LENGTH + " chars long");
			}
			break;
		case Commands.CM_CREATEDISK:
			checkForAmountOperands(inputArray, 1);
			matcher = wordCharPattern.matcher(inputArray[1]);
			if(!matcher.matches())
				throw new InvalidOperandException();
			break;
		case Commands.CM_DELETEDISK:
			checkForAmountOperands(inputArray, 1);
			break;
		case Commands.CM_FORMATDISK:
			checkForAmountOperands(inputArray, 3);
			try {
				Integer.parseInt(inputArray[2]);
				Integer.parseInt(inputArray[3]);
			} catch (NumberFormatException e){
				throw new InvalidOperandException();
			}
			break;
		case Commands.CM_LOADFILE:
			checkForAmountOperands(inputArray, 2);
			if(inputArray[1].length() > MAX_NAME_LENGTH){
				throw new InvalidOperandException(", Name must be less than " + MAX_NAME_LENGTH + " chars long");
			}
			break;
		case Commands.CM_LS:
			break;
		case Commands.CM_MKDIR:
			checkForAmountOperands(inputArray, 1);
			matcher = wordCharPattern.matcher(inputArray[1]);
			if(!matcher.matches())
				throw new InvalidOperandException();
			if(inputArray[1].length() > MAX_NAME_LENGTH){
				throw new InvalidOperandException(", Name must be less than " + MAX_NAME_LENGTH + " chars long");
			}
			break;
		case Commands.CM_MOUNT:
			checkForAmountOperands(inputArray, 1);
			break;
		case Commands.CM_RM:
			checkForAmountOperands(inputArray, 1);
			break;
		case Commands.CM_RMDIR:
			checkForAmountOperands(inputArray, 1);
			break;
		case Commands.CM_SHOWDISKS:
			break;
		case Commands.CM_UNMOUNT:
			break;
		case Commands.CM_HELP:
			break;
		}
		
	}
	
	/**
	 * Check that the amount of operand in a command is correct
	 * @param inputArray the arrays of openads and the command
	 * @param n the amount of operands such command should have
	 * @throws MissingOperandException when there are missing operands
	 * @throws InvalidOperandException when there are too many operands
	 */
	private static void checkForAmountOperands(String[] inputArray, int n) 
			throws MissingOperandException, InvalidOperandException {
		int numOfOperands = n + 1;
		if(inputArray.length < numOfOperands)
			throw new MissingOperandException();
		if(inputArray.length > numOfOperands)
			throw new InvalidOperandException();
	}
	
	
	/**
	 * Gets the command index from the commandList
	 * @param c command
	 * @return if command nis not found return -1, else return the command index
	 */
	private static int getCommandIndex(String c) {
		for(int i = 0; i < Commands.commandList.length; i++){
			if(c.equals(Commands.commandList[i]))
				return i;
		}
		
		return -1;
	}

}
