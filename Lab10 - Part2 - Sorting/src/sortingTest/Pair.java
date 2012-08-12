package sortingTest;

public class Pair {
	private int a; 
	private int b; 
	public Pair(int ai, int bi) { 
		a = ai; 
		b = bi; 
	}
	public int getFirst() { return a; } 
	public int getSecond() { return b; } 
	
	public String toString() { 
		return "[" + a + "," + b + "]"; 
	}
}
