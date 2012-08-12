package classes;

public class Utils {
	public static boolean powerOf2(int capacity) {
		return (Math.log(capacity)/Math.log(2))%1 == 0;
	}

}
