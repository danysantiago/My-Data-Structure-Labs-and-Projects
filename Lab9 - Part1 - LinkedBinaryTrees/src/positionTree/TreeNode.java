package positionTree;

import java.util.ArrayList;

import positionInterfaces.Position;

public abstract class TreeNode<E> implements Position<E> {

	private E data; 
	private Position<E> parent; 
	
	public TreeNode() {} 
	
	public TreeNode(E data, Position<E> parent) { 
		this.data = data; 
		this.parent = parent; 
	}
	
	public TreeNode(TreeNode<E> p, E e) { 
		parent = p; 
		data = e; 
	}
	
	public E element() {
		return data;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public Position<E> getParent() {
		return parent;
	}

	public void setParent(Position<E> parent) {
		this.parent = parent;
	}

	public abstract Iterable<Position<E>> getChildren();

	public void clean() {

		data = null; 
		parent = null; 
	}


}
