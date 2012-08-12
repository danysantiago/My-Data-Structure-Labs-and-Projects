package arrayIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReverseArrayIterator<E> implements Iterator<E> {

	private E[] arr;    // the array to iterate over
	private int current; 
	
	public ReverseArrayIterator(E[] arr) { 
		this.arr = arr; 
		current = arr.length; 
	}
	
	public boolean hasNext() {
		return current > 0;
	}

	public E next() throws NoSuchElementException {
		if (!hasNext())
			throw new 
				NoSuchElementException("No more elements to iterate over."); 
		current--; 
		return arr[current];
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Remove peration not implemented.");
	}

}
