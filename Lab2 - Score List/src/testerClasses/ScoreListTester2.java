package testerClasses;

import interfaces.InvalidRankException;
import interfaces.ScoreList;
import interfaces.ScoreList2;

import javaClasses.ArrayScoreList3;
import javaClasses.Score;

public class ScoreListTester2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\n\n\n**** Testing ArrayScoreList3: ");
		test(new ArrayScoreList3(5)); 

	}

	private static void test(ScoreList2 sl) { 
		System.out.println("\n\n---Testing with capacity = " + sl.capacity()); 
		addScore(sl, new Score("Pepita", 567)); 
		addScore(sl, new Score("Juana", 389)); 
		
		showHighestScores(sl, 3); 
		
		addScore(sl, new Score("Manuel", 500)); 
		addScore(sl, new Score("Maria", 1567)); 
		addScore(sl, new Score("Pepita", 350)); 
		addScore(sl, new Score("Esperanza", 100)); 
		addScore(sl, new Score("Yonaliz", 507)); 

		showHighestScores(sl, sl.size()); 

		sl.showInternalContent();
	}
	
	
	private static void addScore(ScoreList2 sl, Score s) { 
		System.out.println("+++ adding score " + s + ", removing score: " + sl.addScore(s)); 
		
	}
	
	private static void showHighestScores(ScoreList2 sl, int n) { 
		try{ 
		System.out.println("\nThe " + n + " highest scores are: ");
		for (int rank = 1; rank <= n; rank++)
			System.out.println(sl.getScore(rank)); 
		}
		catch (InvalidRankException e) {
			System.out.println(e); 
		}
	}
}
