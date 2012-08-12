import java.util.Scanner;

import exceptions.InvalidCommandException;
import exceptions.InvalidOperandException;
import exceptions.MissingOperandException;


/**
 * The main class known as the 'Shell' because it handles the console input
 * @author Dany
 *
 */
public class Shell {
	//VM input stream
	public static Scanner in = new Scanner(System.in);

	//Program's main method
	public static void main(String[] args) {
		//Get the singleton of the File System Component
		FileSystemComponent fsc = FileSystemComponent.getFSC();
		String inputLine;
		int commandIndex;
		
		//Main loop to lisent for commands
		System.out.println("D-Unix File System Component 0.0.2");
		while(true){
			//Print the current location of the disk, if any
			if(fsc.isDiskMounted())
				System.out.print(fsc.getCurDiskName()+":\\"+fsc.getCurDirPath()+"$ ");
			else
				System.out.print("~$ ");
			
			//Exit command is special and this it is handled here
			inputLine = in.nextLine();
			if(inputLine.equals("exit")){
				//Unmount any disk before exiting
				if(fsc.isDiskMounted())
					fsc.unMount();
				break;
			}
			
			//Try catch of command validation, if method fails to validate the correct exception will be
			//catch-ed and a message will be displayed
			try {
				if(inputLine.length() > 0){
					//Validate
					commandIndex = InputValidator.validate(inputLine);
					//Execute
					fsc.execute(inputLine, commandIndex);
				}
			} catch (InvalidCommandException e){
				if(e.getMessage() != null)
					System.out.println(inputLine + ": command not found" + e.getMessage());
				else
					System.out.println(inputLine + ": command not found");
			} catch (MissingOperandException e){
				if(e.getMessage() != null)
					System.out.println(inputLine + ": missing operands" + e.getMessage());
				else
					System.out.println(inputLine + ": missing operands");
			} catch (InvalidOperandException e){
				if(e.getMessage() != null)
					System.out.println(inputLine + ": invalid operands" + e.getMessage());
				else
					System.out.println(inputLine + ": invalid operands");
			}

		}

	}

}
