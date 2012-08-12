/**
 * 
 */
package javaClasses;

import interfaces.InvalidRankException;
import interfaces.ScoreList;

/**
 * @author pirvos
 *
 */
public class ArrayScoreList2 implements ScoreList {
	private static final int DEFCAP = 5;    	
	private Score[] element; 
	private int size; 

	public ArrayScoreList2(int cap) { 
		if (cap < DEFCAP)
		   cap = DEFCAP; 
		element = new Score[cap]; 
		size = 0;     // initially empty
	}
	public ArrayScoreList2() { 
		this(DEFCAP);  // invoke the other constructor
	} 

	/* (non-Javadoc)
	 * @see interfaces.ScoreList#addScore(javaClasses.Score)
	 */
	public void addScore(Score score) {
		if (size < element.length)      // the array is not full
		{ 
			this.insert1(score);
			size++; 
		} 
		else if (element[0].compareTo(score) < 0) { 
			this.insert2(score);
		}
	}

	private void insert1(Score score) { 
		int pos = size-1; 
		while (pos >= 0 && (element[pos].compareTo(score) >= 0)) {
			element[pos+1] = element[pos]; 
			pos--; 
		}
		element[pos+1] = score; 
	}

	private void insert2(Score score) { 
		int pos = 1; 
		while (pos < size && (element[pos].compareTo(score) < 0)) {
			element[pos-1] = element[pos]; 
			pos++; 
		}
		element[pos-1] = score; 
	}


	/* (non-Javadoc)
	 * @see interfaces.ScoreList#capacity()
	 */
	public int capacity() {
		return element.length;
	}

	/* (non-Javadoc)
	 * @see interfaces.ScoreList#getScore(int)
	 */
	public Score getScore(int rank) throws InvalidRankException {
		if (rank < 1 || rank > size) 
			throw new InvalidRankException("Method getScore: rank = " + rank); 
		return element[size-rank];
	}

	/* (non-Javadoc)
	 * @see interfaces.ScoreList#isEmpty()
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/* (non-Javadoc)
	 * @see interfaces.ScoreList#size()
	 */
	public int size() {
		return size;
	}

	
	///////////// THE FOLLOWING IS INCLUDED ONLY FOR TESTING PURPOSES
	public void showInternalContent() { 
		System.out.println("\nThe ScoreListObjec internals are: ");
		System.out.println("List capacity = " + element.length); 
		System.out.println("Size = " + size); 
		System.out.println("Array content:"); 
		for (int i=0; i < element.length; i++) 
			System.out.println("element["+i+"] = " + element[i]); 
	}
}
