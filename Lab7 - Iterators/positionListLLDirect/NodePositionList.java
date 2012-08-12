/**
 * 
 */
package positionListLLDirect;

import java.util.Iterator;

import exceptionClasses.BoundaryViolationException;
import exceptionClasses.EmptyListException;
import exceptionClasses.InvalidPositionException;

import positionInterfaces.Position;
import positionInterfaces.PositionList;

/**
 * Implementation of PositionList based on a doubly linked
 * list with dummy header and dummy trailer nodes. 
 * 
 * @author Pedro I. Rivera-Vega
 *
 */
public class NodePositionList<T> implements PositionList<T> {

	private DNode<T> header, trailer; 
	private int count; 
	
	public NodePositionList() {
		header = new DNode<T>(null, null, null, this); 
		trailer = new DNode<T>(null, header, null, this);
		header.setNext(trailer); 
		count = 0; 
	}
	
	private DNode<T> checkPosition(Position<T> p) throws InvalidPositionException { 
		// in order to be valid: p must represent a valid data position inside
		// the list where it is applied. Since in this case the positions are 
		// implemented as DNode, p must also be of type DNode.
		if (p==null) 
			throw new InvalidPositionException("Invalid position: null reference."); 
	
		try {
			DNode<T> node = (DNode<T>) p;
			// check if position is header or trailer
			if (node.getPrev()==null || node.getNext()==null)    
				throw new InvalidPositionException("Not a valid data position."); 
			if (node.getList()!=this) 
				throw new InvalidPositionException
				  ("Position does not belong to the right list.");
			return node; 			
		}
		catch (ClassCastException e) { 
			throw new InvalidPositionException("Position is not of type DNode"); 
		}
	}
	
	public Position<T> first() throws EmptyListException {
		if (this.isEmpty()) 
			throw new EmptyListException("Empty list..."); 
		return header.getNext();
	}

	public Position<T> last() throws EmptyListException {
		if (this.isEmpty()) 
			throw new EmptyListException("Empty list..."); 
		return trailer.getPrev();
	}

	public Position<T> next(Position<T> p) throws 
		InvalidPositionException, BoundaryViolationException {
		DNode<T> node = checkPosition(p); 
		DNode<T> next = node.getNext(); 
		if (next==trailer)
			throw new BoundaryViolationException
			 ("Cannot advance past the end of the list.");
		return next;
	}

	public Position<T> prev(Position<T> p) throws 
		InvalidPositionException, BoundaryViolationException {
		DNode<T> node = checkPosition(p); 
		DNode<T> prev = node.getPrev(); 
		if (prev==header)
			throw new BoundaryViolationException
			 ("Cannot advance past the beginning of the list.");
		return prev;
	}
	
	public T remove(Position<T> p) throws InvalidPositionException {
		DNode<T> node = checkPosition(p); 
		T e2r = node.element(); 
		count--; 
		DNode<T> pNode = node.getPrev(); 
		DNode<T> aNode = node.getNext(); 
		pNode.setNext(aNode); 
		aNode.setPrev(pNode); 
		
		// disconnect from rest of list the position to remove ....
		node.cleanLinks(); 
		return e2r; 
	}
	
	public T set(Position<T> p, T e) throws InvalidPositionException {
		DNode<T> node = checkPosition(p); 
		T e2r = node.element(); 
		node.setElement(e); 
		return e2r;
	}

	public boolean isEmpty() {
		return count==0;
	}

	public int size() {
		return count;
	}

	public void addFirst(T e) {
		DNode<T> nuevo = new DNode<T>(e, header, header.getNext(), this); 
		header.getNext().setPrev(nuevo); 
		header.setNext(nuevo); 
		count++; 
	}

	public void addLast(T e) {
		DNode<T> nuevo = new DNode<T>(e, trailer.getPrev(), trailer, this); 
		trailer.getPrev().setNext(nuevo); 
		trailer.setPrev(nuevo); 
		count++; 
	}

	public void addAfter(Position<T> p, T e) throws InvalidPositionException {
		DNode<T> nodo = checkPosition(p); 
		DNode<T> nuevo = new DNode<T>(e, nodo, nodo.getNext(), this); 
		nodo.getNext().setPrev(nuevo); 
		nodo.setNext(nuevo); 
		count++; 
	}

	public void addBefore(Position<T> p, T e) throws InvalidPositionException {
		DNode<T> nodo = checkPosition(p); 
		DNode<T> nuevo = new DNode<T>(e, nodo.getPrev(), nodo, this); 
		nodo.getPrev().setNext(nuevo); 
		nodo.setPrev(nuevo); 
		count++; 
	}

	public Iterable<Position<T>> positions() {
		NodePositionList<Position<T>> pList = new NodePositionList<Position<T>>(); 
		try { 
			if (!this.isEmpty()) { 
				Position<T> current = this.first(); 
				while (current != this.last()) { 
					pList.addLast(current); 
					current = this.next(current); 
				}			
			}
		} catch (Exception e) { 
			System.out.println("NodePositionList - positions(): Invalid list"); 
			e.printStackTrace(); 
		}
		return pList;
	}
	
	/**
	 * Method to comply with Iterble...
	 */
	public Iterator<T> iterator() {
		return new PositionListElementsIterator<T>(this);
	}


	/**
	 * Prepares every node so that the garbage collector can free 
	 * its memory space, at least from the point of view of the
	 * list. This method is supposed to be used whenever the 
	 * list object is not going to be used anymore. Removes all
	 * physical nodes (data nodes and control nodes, if any)
	 * from the linked list. It disconnects every node in the
	 * linked list from its data and its neighbors. Just need
	 * to assign null to all the fields in every object. 
	 */
	private void destroy() {
		while (header != null) { 
			DNode<T> nnode = header.getNext(); 
			header.setElement(null); 
			header.setNext(null); 
			header.setPrev(null); 
			header = nnode; 
		}
	}
	
	/**
	 * This method is automatically invoked when the GC finds
	 * an object if this type having reference count ==0.
	 */
	protected void finalize() throws Throwable {
		//System.out.println("GC is WORKING!");
		//System.out.println("Number of nodes to remove is: "+ this.length); 
		try {
			this.destroy(); 
		} finally {
			super.finalize();
		}
	}

	
	/****************************************************************************************/
	/*  Private class implementing Position<>  For this implementation of the PositionList  */
	/*  it has to be a DNode<>. Note that under this implementation each node has a         */
	/*  reference to the list that it belongs to. It must be at most one.                   */
	/****************************************************************************************/
	private static class DNode<T2> implements Position<T2> {
        private T2 element; 
        private DNode<T2> next, prev; 
        private NodePositionList<T2> myList;     // list it belongs to
        
        public DNode(T2 e, DNode<T2> p, DNode<T2> n, NodePositionList<T2> list) { 
        	element = e; 
        	next = n; 
        	prev = p; 
        	myList = list; 
        }
		public T2 element() {
			return element;
		} 
		public void setElement(T2 e) { 
			element = e; 
		}
		public void setNext(DNode<T2> n) { 
			next = n; 
		}
		public DNode<T2> getNext() { 
			return next; 
		}
		public void setPrev(DNode<T2> p) { 
			prev = p; 
		}
		public DNode<T2> getPrev() { 
			return prev; 
		}
		public void setList(NodePositionList<T2> n) { 
			myList = n; 
		}
		public NodePositionList<T2> getList() { 
			return myList; 
		}
		
		public void cleanLinks() { 
			next = prev = null; 
		}
		
		public void cleanAll() { 
			element = null; 
			cleanLinks(); 
		}
		
	}

}
