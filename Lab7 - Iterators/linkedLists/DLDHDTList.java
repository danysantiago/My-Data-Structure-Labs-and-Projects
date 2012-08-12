package linkedLists;

import java.util.Iterator;

public class DLDHDTList<E> implements LinkedList<E> {
	private DNode<E> dHeader, dTrailer; 
	private int size; 
	
	public DLDHDTList() { 
		dHeader = new DNode<E>(); 
		dTrailer = new DNode<E>(); 
		dHeader.setNext(dTrailer); 
		dTrailer.setPrev(dHeader); 
		size = 0; 
	}
	public void addFirstNode(Node<E> nuevo) {
		addNodeAfter(dHeader, nuevo); 
	}

	public void addNodeAfter(Node<E> target, Node<E> nuevo) {
		DNode<E> dnuevo = (DNode<E>) nuevo; 
		DNode<E> nBefore = (DNode<E>) target; 
		DNode<E> nAfter = nBefore.getNext(); 
		nBefore.setNext(dnuevo); 
		nAfter.setPrev(dnuevo); 
		dnuevo.setPrev(nBefore); 
		dnuevo.setNext(nAfter); 
		size++; 
	}

	public void addNodeBefore(Node<E> target, Node<E> nuevo) {
		DNode<E> dtarget = (DNode<E>) target; 
		addNodeAfter(dtarget.getPrev(), nuevo); 
	}

	public Node<E> createNewNode() {
		return new DNode<E>();
	}

	public Node<E> getFirstNode() throws NodeOutOfBoundsException {
		if (size == 0) 
			throw new NodeOutOfBoundsException("List is empty."); 
		return dHeader.getNext();
	}

	public Node<E> getLastNode() throws NodeOutOfBoundsException {
		if (size == 0) 
			throw new NodeOutOfBoundsException("List is empty."); 
		return dTrailer.getPrev();
	}

	public Node<E> getNodeAfter(Node<E> target)
			throws NodeOutOfBoundsException {
		DNode<E> dtarget = (DNode<E>) target; 
		if (dtarget.getNext() == dTrailer) 
			throw new NodeOutOfBoundsException("Target node is the last node."); 			
		return dtarget.getNext(); 
	}

	public Node<E> getNodeBefore(Node<E> target)
			throws NodeOutOfBoundsException {
		DNode<E> dtarget = (DNode<E>) target; 
		if (dtarget.getPrev() == dHeader) 
			throw new NodeOutOfBoundsException("Target node is the first node."); 			
		return dtarget.getPrev(); 
	}

	public int length() {
		return size;
	}

	public Node<E> removeLastNode() throws NodeOutOfBoundsException {
		if (size == 0) 
			throw new NodeOutOfBoundsException("List is empty."); 
		DNode<E> ntd = dTrailer.getPrev();
		dTrailer = ntd.getPrev(); 
		ntd.getPrev().setNext(dTrailer); 
		size--; 
		ntd.cleanLinks(); 
		return ntd; 
	}

	public void removeNode(Node<E> target) {
		DNode<E> dtarget = (DNode<E>) target; 
		DNode<E> pNode = dtarget.getPrev(); 
		DNode<E> aNode = dtarget.getNext(); 
		pNode.setNext(aNode); 
		aNode.setPrev(pNode); 
		size--; 
		dtarget.cleanLinks(); 
		dtarget.setElement(null); 
	}

	/**
	 * Prepares every node so that the garbage collector can free 
	 * its memory space, at least from the point of view of the
	 * list. This method is supposed to be used whenever the 
	 * list object is not going to be used anymore. Removes all
	 * physical nodes (data nodes and control nodes, if any)
	 * from the linked list
	 */
	private void removeAll() {
		while (dHeader != null) { 
			DNode<E> nnode = dHeader.getNext(); 
			dHeader.setElement(null); 
			dHeader.cleanLinks(); 
			dHeader = nnode; 
		}
	}
	
	/**
	 * The execution of this method removes all the data nodes
	 * from the current instance of the list. 
	 */
	public void makeEmpty() { 
		// TODO
	}
		
	protected void finalize() throws Throwable {
	    try {
			this.removeAll(); 
	    } finally {
	        super.finalize();
	    }
	}

	
	private class DNode<E> implements Node<E> {
		private E element; 
		private DNode<E> prev, next; 

		public DNode() {}
		public DNode(E e) { 
			element = e; 
		}
		public DNode(E e, DNode<E> p, DNode<E> n) { 
			prev = p; 
			next = n; 
		}
		public DNode<E> getPrev() {
			return prev;
		}
		public void setPrev(DNode<E> prev) {
			this.prev = prev;
		}
		public DNode<E> getNext() {
			return next;
		}
		public void setNext(DNode<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element; 
		}

		public void setElement(E data) {
			element = data; 
		} 
		
		public void cleanLinks() { 
			prev = next = null; 
		}
		
	}

}
