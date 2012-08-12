package positionTree;

import java.util.ArrayList;
import java.util.Iterator;

import exceptionClasses.BoundaryViolationException;
import exceptionClasses.EmptyTreeException;
import exceptionClasses.InvalidPositionException;

import positionInterfaces.Position;
import positionInterfaces.Tree;
import positionListLLDirect.NodePositionList;

public abstract class GTree<E> implements Tree<E>, Iterable<E> {
	
	public GTree() { 
	}

	protected abstract TreeNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException;
	
	public Iterable<Position<E>> children(Position<E> v)
			throws InvalidPositionException {
		TreeNode<E> tv = checkPosition(v); 
		return tv.getChildren();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TreeNode<E> tv = checkPosition(v); 
		return ((ArrayList<Position<E>>) tv.getChildren()).isEmpty(); 
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TreeNode<E> tv = checkPosition(v); 
		return !((NodePositionList<Position<E>>) tv.getChildren()).isEmpty();
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TreeNode<E> tv = checkPosition(v); 
		return tv == root();
	}

	public Iterator<E> iterator() {
		// fill a position list with elements in this tree and
		// return such position list...
		NodePositionList<Position<E>> pList = new NodePositionList<Position<E>>(); 
		fillIterator((TreeNode<E>) root(), pList); 
		NodePositionList<E> eList = new NodePositionList<E>(); 
		for (Position<E> p : pList) 
			eList.addLast(p.element());
		return eList.iterator();
	}

	protected void fillIterator(TreeNode<E> r,
			NodePositionList<Position<E>> list) 
	{
		if (r != null) { 
			list.addLast(r); 
			for (Position<E> p : children(r))
				fillIterator((TreeNode<E>) p, list);
		}			
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
		TreeNode<E> tv = checkPosition(v); 
		if (tv == (TreeNode<E>) root())
			throw new BoundaryViolationException("parent: Parent of root does not exist."); 
		return tv.getParent();
	}

	public Iterable<Position<E>> positions() {
		// fill a position list with elements in this tree and
		// return such position list...
		NodePositionList<Position<E>> pList = new NodePositionList<Position<E>>(); 
		fillIterator((TreeNode<E>) root(), pList); 
		return pList; 
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TreeNode<E> vtn = checkPosition(v); 
		E etr = vtn.element(); 
		vtn.setData(e); 
		return etr;
	}

	public abstract Position<E> root() throws EmptyTreeException;

	public abstract int size();

}
