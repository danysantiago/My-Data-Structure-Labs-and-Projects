/**
 * 
 */
package positionListLLDirect;

import java.util.Iterator;
import java.util.NoSuchElementException;

import exceptionClasses.EmptyListException;

import positionInterfaces.Position;
import positionInterfaces.PositionList;

/**
 * @author Pedro I. Rivera-Vega
 *
 */
public class PositionListIterator<T> implements Iterator<Position<T>> {

	private Position<T> current; 
	private PositionList<T> theList; 
	
	public PositionListIterator(PositionList<T> list) { 
		theList = list; 
		try { 
			current = theList.first(); 
		}
	    catch (EmptyListException e) { 
	    	current = null; 
	    }
	}
	
	public boolean hasNext() {
		return current != null;
	}

	public Position<T> next() throws NoSuchElementException {
		if (!hasNext()) 
			throw new NoSuchElementException("Iterator has past the end.");
		Position<T> ptr = current; 
		try { 
			current = theList.next(current); 
		}
		catch (Exception e) { 
			current = null; 
		}
		return ptr;
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
