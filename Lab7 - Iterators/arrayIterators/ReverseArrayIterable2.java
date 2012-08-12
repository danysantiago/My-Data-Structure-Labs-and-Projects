package arrayIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Defining the iterator as an inner static class. 
 * @author pirvos
 *
 * @param <E>
 */
public class ReverseArrayIterable2<E> implements Iterable<E> {
	private E[] arr; 
	public ReverseArrayIterable2(E[] arr) { 
		this.arr = arr; 
	}
	public Iterator<E> iterator() {	
		return new ReverseArrayIterator<E>(arr);
	}

	private  static class ReverseArrayIterator<E> implements Iterator<E> {
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

}
