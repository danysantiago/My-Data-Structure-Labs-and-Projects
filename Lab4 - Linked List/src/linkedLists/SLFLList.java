package linkedLists;
/**
 * Singly linked list with references to its first and its
 * last node. 
 * 
 * @author pirvos
 *
 */

import linkedLists.LinkedList;

public class SLFLList<E> 
implements LinkedList<E>
{

	private SNode<E> first, last; 
	int length = 0; 
	
	public SLFLList() { 
		first = last = null; 
	}
	
	
	public void addFirstNode(Node<E> nuevo) {
		// Pre: nuevo is not a node in the list
		((SNode<E>) nuevo).setNext(first); 
		first = (SNode<E>) nuevo; 
		if(length == 0)
			last = first;
		length++; 
	}

	public void addNodeAfter(Node<E> target, Node<E> nuevo) {
		// Pre: target is a node in the list
		// Pre: nuevo is not a node in the list
		if(target == last)
			last = (SNode<E>) nuevo;
		((SNode<E>) nuevo).setNext(((SNode<E>) target).getNext()); 
		((SNode<E>) target).setNext((SNode<E>) nuevo);
		length++; 
	}

	public void addNodeBefore(Node<E> target, Node<E> nuevo) {
		// Pre: target is a node in the list
		// Pre: nuevo is not a node in the list

		if (target == first)
			this.addFirstNode(nuevo); 
		else { 
			Node<E> prevNode = findNodePrevTo(target);  
			this.addNodeAfter(prevNode, nuevo); 
		}
	}

	public Node<E> getFirstNode() throws NodeOutOfBoundsException {
		if (first == null)
			throw new NodeOutOfBoundsException("getFirstNode() : linked list is empty..."); 

		// the linked list is not empty....
		return first;
	}

	public Node<E> getLastNode() throws NodeOutOfBoundsException {
		if (last == null)
			throw new NodeOutOfBoundsException("getLastNode(): Empty list."); 
			
		// the linked list is not empty....
		return last; 
	}

	public Node<E> getNodeAfter(Node<E> target) throws NodeOutOfBoundsException {
		// Pre: target is a node in the list
		if (target == last)  
			throw new NodeOutOfBoundsException("getNextNode(...) : target is the last node."); 
		else 
			return ((SNode<E>) target).getNext();
	}

	public Node<E> getNodeBefore(Node<E> target)
			throws NodeOutOfBoundsException {
		// Pre: target is a node in the list
		if (target == first)  
			throw new NodeOutOfBoundsException("getPrevNode(...) : target is the first node."); 
		else 
			return findNodePrevTo(target);
	}

	public int length() {
		return length;
	}

	public Node<E> removeFirstNode() throws NodeOutOfBoundsException {
		if (first == null) 
			throw new NodeOutOfBoundsException("removeFirstNode(): linked list is empty."); 

		// the list is not empty....
		SNode<E> ntr = first; 
		if(first == last)
			last = null;
		first = first.getNext();
		ntr.setNext(null);      // notice that the node keeps its data..
		length--; 
		return ntr;
	}

	public Node<E> removeLastNode() throws NodeOutOfBoundsException {
		if (first == null) 
			throw new NodeOutOfBoundsException("removeFirstNode(): linked list is empty."); 

		// the list is not empty....
		if (first.getNext() == null)
			return this.removeFirstNode(); 
		else { 
			SNode<E> ntr = last;
			SNode<E> prevNode = (SNode<E>) findNodePrevTo(last);
			prevNode.setNext(null);
			last = prevNode;
			length--; 
			return ntr;
		} 
	}

	public void removeNode(Node<E> target) {
		// Pre: target is a node in the list
		
		if (target == first) 
			this.removeFirstNode();
		else if (target == last)
			this.removeLastNode();
		else { 
			SNode<E> prevNode = (SNode<E>) this.getNodeBefore(target); 
			prevNode.setNext(((SNode<E>) target).getNext()); 
			length--; 
		}
		
	}

	public Node<E> removeNodeAfter(Node<E> target)
			throws NodeOutOfBoundsException {
		// Pre: target is a node in the list
		if (target == last)
			throw new NodeOutOfBoundsException("removeNodeAfter(...) : target is the last node...");			

		SNode<E> ntr = ((SNode<E>) target).getNext();
		if(ntr == last)
			last = (SNode<E>) target;
		((SNode<E>) target).setNext(ntr.getNext()); 
		ntr.setNext(null); 
		length--; 
		return ntr;
	}

	public Node<E> removeNodeBefore(Node<E> target)
			throws NodeOutOfBoundsException {
		// Pre: target is a node in the list
		if (target == first)
			throw new NodeOutOfBoundsException("removeNodeBrfore(...) : target is the first node...");			

		// head is not the first node. 
		// need to find the node before target and the one before it, if any...
		Node<E> prevNode = this.getNodeBefore(target); 
		this.removeNode(prevNode); 
		return prevNode;
	}
	
	public Node<E> createNewNode() {
		return new SNode<E>();
	}
	
	private Node<E> findNodePrevTo(Node<E> target) {
		// Pre: target is a node in the list
		if (target == first) 
			return null; 
		else { 
			SNode<E> prev = first; 
			while (prev != null && prev.getNext() != target) 
				prev = prev.getNext();  
			return prev; 
		}
	}


	///////////////////////////////////////////////////
	// private and static inner class that defines the 
	// type of node that this list implementation uses
	private static class SNode<T> implements Node<T> {
		private T element; 
		private SNode<T> next; 
		public SNode() { 
			element = null; 
			next = null; 
		}
		public SNode(T data, SNode<T> next) { 
			this.element = data; 
			this.next = next; 
		}
		public SNode(T data)  { 
			this.element = data; 
			next = null; 
		}
		public T getElement() {
			return element;
		}
		public void setElement(T data) {
			this.element = data;
		}
		public SNode<T> getNext() {
			return next;
		}
		public void setNext(SNode<T> next) {
			this.next = next;
		}
	}

}
