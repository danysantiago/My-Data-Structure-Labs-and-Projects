package classes;

public class SNode<E>  {
	private E element; 
	private SNode<E> next; 
	public SNode() { 
		element = null; 
		next = null; 
	}
	public SNode(E data, SNode<E> next) { 
		this.element = data; 
		this.next = next; 
	}
	public SNode(E data)  { 
		this.element = data; 
		next = null; 
	}
	public E getElement() {
		return element;
	}
	public void setElement(E data) {
		this.element = data;
	}
	public SNode<E> getNext() {
		return next;
	}
	public void setNext(SNode<E> next) {
		this.next = next;
	}
	public void clean() { 
		element = null; 
		next = null; 
	}
}

