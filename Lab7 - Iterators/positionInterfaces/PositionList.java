package positionInterfaces;

import exceptionClasses.BoundaryViolationException;
import exceptionClasses.EmptyListException;
import exceptionClasses.InvalidPositionException;

public interface PositionList<T> extends Iterable<T> {
	Position<T> first() throws EmptyListException; 
	Position<T> last() throws EmptyListException; 
	Position<T> next(Position<T> p) throws 
	            InvalidPositionException, BoundaryViolationException; 
	Position<T> prev(Position<T> p) throws 
                InvalidPositionException, BoundaryViolationException; 
	T remove(Position<T> p) throws InvalidPositionException;
	T set(Position<T> p, T e) throws InvalidPositionException; 
	void addFirst(T e); 
	void addLast(T e); 
	void addAfter(Position<T> p, T e) throws InvalidPositionException; 
	void addBefore(Position<T> p, T e) throws InvalidPositionException; 
	boolean isEmpty(); 
	int size(); 
	Iterable<Position<T>> positions(); 
}
