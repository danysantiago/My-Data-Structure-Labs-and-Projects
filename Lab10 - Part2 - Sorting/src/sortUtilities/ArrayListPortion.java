package sortUtilities;

import java.util.ArrayList;

/**
 * Creates a wrapper over an ArrayList. It allows a contiguous portion
 * of the array list to be seen as a list (almost IndexList, but without
 * the remove operation)
 * @author pirvos
 *
 * @param <E>
 */

public  class ArrayListPortion<E> 
{
	private ArrayList<E> arr;     // the array list object
	private int first, last;      // indexes marking the borders of the portion
	
	/**
	 * Creates the wrapper object corresponding to a contiguous portion
	 * of an ArrayList object. 
	 * @param theArray  reference to the ArrayList object
	 * @param first  index of the first position in the contiguous portion
	 * @param last index of the last position in the contiguous portion
	 * @throws InvalidListFromArrayException if at least one of the 
	 * 	borders is not valid to denote a correct portion of the ArrayList
	 *  being wrapped. 
	 */
	public ArrayListPortion(ArrayList<E> theArray, int first, int last) 
	throws InvalidListFromArrayException 
	{
		// PRE: theArray is not empty
		// if last < first, then we are creating an empty list
		if (first < 0 || first > theArray.size() 
				|| last >= theArray.size())
			throw new InvalidListFromArrayException("Invalid list setting: first = " + first 
					+ " last = " + last + " and size of array = " + theArray.size()); 
		
		arr = theArray; 
		this.first = first; 
		this.last = last; 
	}
	
	/**
	 * Creates a new wrapper over the same ArrayList of another existing
	 * wrapper...
	 * @param other the other ArrayListPortion whose ArrayList is used...
	 * @param first  index of the first position in the contiguous portion
	 * @param last index of the last position in the contiguous portion
	 * @throws InvalidListFromArrayException if at least one of the 
	 * 	borders is not valid to denote a correct portion of the ArrayList
	 *  being wrapped. 
	 */
	public ArrayListPortion(ArrayListPortion<E> other, int first, int last) 
	throws InvalidListFromArrayException
	{ 
		this(other.arr, other.first + first, other.first + last);  		
	}
	

	/**
	 * Gets the ith element in the list 
	 * @param i index or element.
	 * @return reference to the element in the underlying ArrayList which
	 *  corresponds to the ith element in the (wrapper) list. 
	 * @throws IndexOutOfBoundsException if i is not a valid position
	 *  in the currrent list instance...
	 */
	public E get(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= this.size())
			throw new IndexOutOfBoundsException("Index value " + i 
					+ " is out of bounds in current list instance. (first = "+first + ", last = "+ last); 
		
		return arr.get(first+i); 
	}
	
	/**
	 * Replaces the value in a given position of the list.
	 * @param i index of the position whose value is replaced
	 * @param e the new value
	 * @return the value being replaced
	 * @throws IndexOutOfBoundsException if i is not a valid position
	 *  in the currrent list instance...
	 */
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (i < 0 || i >= this.size())
			throw new IndexOutOfBoundsException("Index value " + i 
					+ " is out of bounds in current list instance."); 
		
		return arr.set(first + i, e);
	}
	
	/**
	 * 
	 * @return true if current portion has no elements;
	 *  false, otherwise.
	 */
	public boolean isEmpty() {
		return last < first;
	}

	/**
	 * Size of the portion list...
	 * @return size
	 */
	public int size() {
		if (isEmpty())
			return 0;
		else 
			return last-first+1; 
	}

}
