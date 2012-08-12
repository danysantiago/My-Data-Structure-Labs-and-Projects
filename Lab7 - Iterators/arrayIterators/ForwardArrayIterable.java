package arrayIterators;

import java.util.Iterator;

public class ForwardArrayIterable<E> implements Iterable<E> {

	private E[] arr; 
	public ForwardArrayIterable(E[] arr) { 
		this.arr = arr; 
	}
	public Iterator<E> iterator() {	
		return new ForwardArrayIterator<E>(arr);
	}

}
