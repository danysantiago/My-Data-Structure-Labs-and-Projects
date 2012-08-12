package testers;

import classes.SymmetricStringAnalyzer;

public class Tester4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 = "CWZzMXxmwcAMma"; 
		String s2 = "AaBbCDdc"; 
		String s3 = "ABCcbDEeca"; 
		String s4 = "ABCcbDEeda"; 
		String s5 = "ABcCbDEeda"; 
		String s6 = "AAAaaBbaDDdEe"; 
		showAndAnalyze(s1); 
		showAndAnalyze(s2); 
		showAndAnalyze(s3); 
		showAndAnalyze(s4); 
		showAndAnalyze(s5);
		showAndAnalyze(s6); 
	}
	
	private static void showAndAnalyze(String s) { 
		SymmetricStringAnalyzer sa = new SymmetricStringAnalyzer(s); 
		System.out.println("\n** Analyzing string: " + s); 
		if (sa.isValidContent()) {
			System.out.println(" The string is a valid symmetric string."); 
			System.out.println(" The parenthesized expression is: " + sa.parenthesizedExpression()); 
		}
		else 
			System.out.println(" The string is not a valid symmetric string."); 
	}

}
