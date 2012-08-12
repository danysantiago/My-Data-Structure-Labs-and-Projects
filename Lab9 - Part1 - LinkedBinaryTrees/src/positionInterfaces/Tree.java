package positionInterfaces;

import java.util.Iterator;

import exceptionClasses.BoundaryViolationException;
import exceptionClasses.EmptyTreeException;
import exceptionClasses.InvalidPositionException;

public interface Tree<T> extends Iterable<T>{
	int size(); 
	boolean isEmpty(); 
	Iterator<T> iterator(); 
	Iterable<Position<T>> positions(); 
	T replace(Position<T> v, T e)
	  throws InvalidPositionException; 
	Position<T> root() throws EmptyTreeException; 
	Position<T> parent(Position<T> v) 
	  throws InvalidPositionException, BoundaryViolationException; 
	Iterable<Position<T>> children(Position<T> v)
	  throws InvalidPositionException; 
	boolean isInternal(Position<T> v) 
	  throws InvalidPositionException; 
	boolean isExternal(Position<T> v) 
	  throws InvalidPositionException; 
	boolean isRoot(Position<T> v) 
	  throws InvalidPositionException; 
}
