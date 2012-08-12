package interfaces;

import javaClasses.Score;

public interface ScoreList2 {
	/** Determines the capacity of the list. 
	 * @return integer value corresponding to the 
	 * maximum number of scores that can be added
	 * to the list. 
	 **/
	int capacity(); 

	/** Attempts to add a new score to the list. 
	 * It is eventually added if the given score is
	 * larger than the current minimum score in the list
	 * or if the current size is less than the capacity
	 * of the list. If the new score is finally added, 
	 * and the current size is equal to the capacity
	 * (when the add is invoked), then the
	 * current lowest score is lost. 
	 * @param score the new score to add.
	 **/ 
	Score addScore(Score score); 

	/** Gets the score with the particular rank. 
	 * Rank 1 is the highest score, Rank 2 is the second
	 * highest, and so on…
	 * @param rank the integer value corresponding to the
	 * desired score.
	 * @return reference to the score currently listed 
	 * with the specified rank among all elements in the
	 * list.
	 * @throws InvalidRankException If the value of rank
	 * less than 1 or greater than the current size. 
	 **/
	Score getScore(int rank) throws 
	InvalidRankException;

	/** Determines the size (number of scores actually in the list. 
	 *  @return value corresponding to the current size
	 *  (number of scores currently in the list). 
	 **/
	int size(); 

	/** Determines if the list is empty.
	 * @return true if empty; false, otherwise. 
	 **/
	boolean isEmpty(); 
	
	
	// This is just for testing purposes... Should
	// be removed when a final version is reached..
	void showInternalContent(); 
} 

