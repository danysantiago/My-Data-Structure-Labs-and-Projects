package classes;

/**
 * Class with utility methods used throughout the program
 * @author Dany
 *
 */
public class Utils {
	//Max size of a name used in this program
	public static final int MAX_NAME_SIZE = 20;
	
	/**
	 * Check if a number is a power of 2
	 * @param number being checked
	 * @return
	 */
	public static boolean powerOf2(int number) {
		return (Math.log(number)/Math.log(2))%1 == 0;
	}
	
	/**
	 * Converts a string into a byte array of size MAX_NAME_SIZE
	 * @param name being converted
	 * @return byte array
	 */
	public static byte[] stringToNameByteArray(String name) {
	    byte[] array = new byte[MAX_NAME_SIZE];
	    int curPos = 0;
	    for(int i = 0; i < name.length(); i++)
	    	array[curPos++] = (byte) name.charAt(i);
	    while(curPos < MAX_NAME_SIZE)
	    	array[curPos++] = 0;
	    return array;
	}

}
