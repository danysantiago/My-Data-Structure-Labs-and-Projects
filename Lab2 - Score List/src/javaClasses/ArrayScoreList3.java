/**
 * 
 */
package javaClasses;

import interfaces.InvalidRankException;
import interfaces.ScoreList2;

/**
 * @author pirvos
 *
 */
public class ArrayScoreList3 implements ScoreList2 {
	private static final int DEFCAP = 5;    	
	private Score[] element; 
	private int size; 

	public ArrayScoreList3(int cap) { 
		if (cap < DEFCAP)
		   cap = DEFCAP; 
		element = new Score[cap]; 
		size = 0;     // initially empty
	}
	public ArrayScoreList3() { 
		this(DEFCAP);  // invoke the other constructor
	} 

	/* (non-Javadoc)
	 * @see interfaces.ScoreList#addScore(javaClasses.Score)
	 */
	public Score addScore(Score score) {
		if (size < element.length)      // the array is not full
		{ 
			this.insert(size, score);
			size++;
			return null;
		} 
		else if (element[size-1].compareTo(score) < 0) {
			Score temp = element[size-1];
			this.insert(size-1, score);
			return temp;
		}
		
		return score;
	}

	private void insert(int index, Score score) { 
		int pos = index-1; 
		while (pos >= 0 && (element[pos].compareTo(score) < 0)) {
			element[pos+1] = element[pos]; 
			pos--; 
		}
		element[pos+1] = score; 
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
		return element[rank-1];
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
