package indexList;

import java.util.Comparator;

public class SLRIndexList<E> implements IndexList<E> {

	private Node<E> first; 
	private int size; 

	public SLRIndexList() { 
		first = null; 
		size = 0; 
	} 

	public boolean isEmpty() { 
		return size == 0; 
	} 

	public int size() { 
		return size; 
	} 

	public E get(int index) 
	throws IndexOutOfBoundsException 
	{ 
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException(
					"SLRIndexList.get: invalid index = " + index); 

		// index is valid
		return recGet(first, index); 
	}

	public void add(int index, E e) throws IndexOutOfBoundsException 
	{ 
		if (index < 0 || index > size) 
			throw new IndexOutOfBoundsException(
					"SLRIndexList.add: invalid index = " + index); 

		// index is valid for the add operation
		first = recAdd(first, index, e); 
		size++; 
		
	} 

	public E remove(int index) 
	throws IndexOutOfBoundsException 
	{ 
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException(
					"SLRIndexList.remove:invalid index = " + index); 

		// index is valid for remove operation
		E etr = get(index); 
		first = recRemove(first, index);
		size--; 
		return etr; 

	} 

	public E set(int index, E e) throws IndexOutOfBoundsException 
	{ 
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException(
					"SLRIndexList.set: invalid index = "+ index); 

		// index is valid for set operation
		return recSet(first, index, e);  
	}
	
	public void sort(Comparator<E> cmp) {
	if (first == null)
		return;
	// list is empty � nothing to sort
	else
		first = recInsertionSort(first, cmp);
	}

	private static <E> Node<E> recInsertionSort(Node<E> first, Comparator<E> cmp) {
		if (first.getNext() == null)
			return first; // size == 1, then list is already sorted
		else {
			Node<E> first2 = recInsertionSort(first.getNext(), cmp);
			return recInsertByOrder(first, first2, cmp);
		}
	}

	private static <E> Node<E> recInsertByOrder(Node<E> nti, Node<E> first, Comparator<E> cmp){
		if (first == null)
			return nti;
		else {
			if(cmp.compare(nti.getElement(), first.getElement()) <= 0){
				nti.setNext(first);
				return nti;
			} else {
				first.setNext(recInsertByOrder(nti, first.getNext(), cmp));
				return first;
			}
		}
		
	}
	
	/// recursive auxiliary methods for get, add, remove, and set operations
	private static <E> E recGet(Node<E> f, int i) 
	{ 
		if (i == 0)
			return f.getElement(); 
		else 
			return recGet(f.getNext(), i-1);
	} 

	private static <E> Node<E> recAdd(Node<E> f, int i, E e) 		
	{ 
		// adds a new node (containing e) as the ith node in
		// the list (the current list) whose first node is f�

		if (i==0) {
			Node<E> newNode = new Node<E>(e, f);
			return newNode;
		} else {
			Node<E> nextNode = recAdd(f.getNext(), i-1, e);
			f.setNext(nextNode);
			return f;
		}
	} 

	/**
	 * Recursively removes a node from the list.
	 * @param <E> Genetic value of the element.
	 * @param f node referred as first of the sublist.
	 * @param i index value of the node to be removed.
	 * @return Element of the node removed.
	 */
	private static <E> Node<E> recRemove(Node<E> f, int i) 
	{ 
		if (i==0) {
			Node<E> ntd = f;  
			f = f.getNext(); 
			ntd.clean(); 
			return f; 
		}
		else { 
			f.setNext( recRemove(f.getNext(), i-1)); 
			return f; 
		}
	} 

	/**
	 * Recursively sets the element of a node.
	 * @param <E> Genetic value of the element.
	 * @param f node referred as first of the sublist.
	 * @param index index value of the node to set the element.
	 * @param e New element to be set.
	 * @return The element that was replaced in the node.
	 */
	private static <E> E recSet(Node<E> f, int index, E e) 
	{ 
		if(index == 0){
			E etr = f.getElement();
			f.setElement(e);
			return etr;
		} else {
			E etr = recSet(f.getNext(), index-1, e);
			return etr;
		}
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
		while (first != null) { 
			Node<E> nnode = first.getNext(); 
			first.setElement(null); 
			first.setNext(null); 
			first = nnode; 
		}
	}

	/**
	 * The execution of this method makes the current instance of 
	 * the list to become empty.  
	 */
	public void makeEmpty() { 
		removeAll(); 
		size = 0; 
	}

	protected void finalize() throws Throwable {
		try {
			System.out.println("GC is WORKING!");
			System.out.println("Number of nodes to remove is: "+ this.size); 
			this.removeAll(); 
		} finally {
			super.finalize();
		}
	}

	
	
	private static class Node<E>  {
		private E element; 
		private Node<E> next; 
		public Node() { 
			element = null; 
			next = null; 
		}
		public Node(E data, Node<E> next) { 
			this.element = data; 
			this.next = next; 
		}
		public Node(E data)  { 
			this.element = data; 
			next = null; 
		}
		public E getElement() {
			return element;
		}
		public void setElement(E data) {
			this.element = data;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		public void clean() { 
			element = null; 
			next = null; 
		}
	}

}
