package arrayIterators;

import java.util.Iterator;

public class ReverseArrayIterable1<E> implements Iterable<E> {

	private E[] arr; 
	public ReverseArrayIterable1(E[] arr) { 
		this.arr = arr; 
	}
	public Iterator<E> iterator() {	
		return new ReverseArrayIterator<E>(arr);
	}

}
