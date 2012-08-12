package indexList;

public interface IndexList<T> { 
	/** Determines the size of the current list instance. 
	 * @return number of elements in the list
	 **/ 
	int size(); 

	/** Determines if the list is empty or not. *
	 * @returns true if empty, false if not. 
	 **/ 
	boolean isEmpty(); 

	/** Accesses an element in the list.
	 * @param index the index value of the position
	 * whose element is being requested
	 * @throws IndexOutOfBoundsException whenever the value
	 * of index is not in the valid range from 0 to size-1.
	 * @return reference to the particular element	
	 **/ 
	T get(int index) throws IndexOutOfBoundsException; 

	/** Removes an element from the list.
	 * @param index the index value of the position
	 * whose element is being deleted
	 * @throws IndexOutOfBoundsException whenever the value
	 * of index is not in the valid range from 0 to size-1.
	 * @return reference to the deleted element 	
	 **/ 
	T remove(int index) throws IndexOutOfBoundsException; 

	/** Replaces an element in the list.
	 * @param index the index value of the position
	 * whose element is being replaced
	 * @param e reference to the new (replacing) element
	 * @throws IndexOutOfBoundsException whenever the value
	 * of index is not in the valid range from 0 to size-1.
	 * @return reference to the element that was replaced	
	 **/ 
	T set(int index, T e) throws IndexOutOfBoundsException; 

	/** Adds a new element to the list.
	 * @param index the index value of the position to be
	 * occupied by the new element is being replaced
	 * @param e reference to the new element
	 * @throws IndexOutOfBoundsException whenever the value
	 * of index is not in the valid range from 0 to size.
	 * @return reference to the element that was replaced	
	 **/ 
	void add(int index, T e) throws IndexOutOfBoundsException; 

	/** Appends a new element to the list.
	 * @param e reference to the new element
	 **/ 
	void add(T e); 
	
	int capacity(); 

}
