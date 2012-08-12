package indexList;

import linkedLists.LinkedList2;
import linkedLists.Node;
import linkedLists.NodeOutOfBoundsException;

public class LLIndexList2<E> implements IndexList<E> {
	private LinkedList2<E> internalLL;  

	/**
		Creates an empty instance of a list. 
	 **/ 
	public LLIndexList2(LinkedList2<E> theList) 
	{ 
		internalLL = theList;
	}

	/**
		Determines the size of the list. 
		@return size of the list � number of elements. 
	 **/
	public int size() {
		int count = 0;
		
		try {

			Node<E> curr = internalLL.getFirstNode();
			count++;

			try {
				while(internalLL.getNodeAfter(curr) != null){
					count++;
					curr = internalLL.getNodeAfter(curr);
				}
			} catch (NodeOutOfBoundsException e){

			}

		} catch (NodeOutOfBoundsException e){

		} 
		
		return count;
	} 

	/** 	
		Determines if the list is empty. 	
		@return true if empty, false if not. 
	 **/ 
	public boolean isEmpty() { 
		return this.size() == 0; 
	} 
	
	/**
	Private method to access the data node at the
    position given in the internal linked list. 
    If the list is not empty, he first data node 
    has position 0, the following data node (if any)
    has position 1, and so on. 
    @param posIndex the index of the position being 
           accessed. 
    @return reference to the data node in the given
           position of the internal linked list. 
	 **/
	private Node<E> getDataNodeAtPosition(int posIndex)
	{ 
		
		Node<E> target = internalLL.getFirstNode(); 
		for (int p=1; p<= posIndex; p++)
			target = internalLL.getNodeAfter(target); 
		return target; 
	} 

	/** 
		Adds a new element to the list. 
		@param i the index of the position where the 
			new element is to be inserted. 
		@param e the new element to insert. 
		@throws IndexOutOfBoundsException if the index
			i does not corresponds to the index 
			of a valid position to insert�
	 **/ 
	public void add(int index, E e) 
	throws IndexOutOfBoundsException 
	{
		if (index < 0  ||  index > this.size()) 
			    throw new IndexOutOfBoundsException("add: " 
				+ "index=" + index + " is out of bounds. size = " + 
				this.size());

     	Node<E> nuevoNodo = internalLL.createNewNode();
     	nuevoNodo.setElement(e); 
		if (index==0) 
       		internalLL.addFirstNode(nuevoNodo); 
		else { 
			Node<E> nodoPrevio = 
				getDataNodeAtPosition(index-1); 
			internalLL.addNodeAfter(nodoPrevio, 
				nuevoNodo); 
		}
	}

	public void add(E e) { 
     	Node<E> nuevoNodo = internalLL.createNewNode();
     	nuevoNodo.setElement(e); 
		Node<E> nodoPrevio = 
			internalLL.getLastNode(); 
		internalLL.addNodeAfter(nodoPrevio, 
			nuevoNodo); 
    	
	}
	
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0  ||  index >= this.size()) 
		    throw new IndexOutOfBoundsException("get: " 
			+ "index=" + index + " is out of bounds.");

		Node<E> targetINode = this.getDataNodeAtPosition(index);
		return targetINode.getElement(); 
	}

	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0  ||  index >= this.size()) 
		    throw new IndexOutOfBoundsException("remove: " 
			+ "index=" + index + " is out of bounds.");

		Node<E> ntr = this.getDataNodeAtPosition(index); 
		E etr = ntr.getElement(); 
		this.internalLL.removeNode(ntr); 

		return etr;
	}

	public E set(int index, E e) throws IndexOutOfBoundsException {
		if (index < 0  ||  index >= this.size()) 
		    throw new IndexOutOfBoundsException("set: " 
			+ "index=" + index + " is out of bounds.");

		Node<E> ntc = this.getDataNodeAtPosition(index); 
		E etr = ntc.getElement(); 
		ntc.setElement(e); 
		return etr; 
	}
	

}
