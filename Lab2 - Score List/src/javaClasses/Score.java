package javaClasses;

public class Score implements Comparable<Score> { 
	private String name; 
	private int value; 
	public Score(String name, int value) { 
		this.name = name; 
		this.value = value; 
	}
	public String toString() { 
	 	return "["+name + "...." + value + "]"; 
	}
	public int compareTo(Score other) { 
		return this.value - other.value; 
	} 
}

