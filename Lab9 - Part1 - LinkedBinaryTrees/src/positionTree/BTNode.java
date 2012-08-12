package positionTree;

import java.util.ArrayList;

import positionInterfaces.BTPosition;
import positionInterfaces.Position;
import positionListLLDirect.NodePositionList;

public class BTNode<E> extends TreeNode<E> implements BTPosition<E> {
	//private E element; 
	private BTPosition<E> left, right; 
	
	public BTNode(E element, BTPosition<E> parent) { 
		super(element, parent); 
		left = right = null; 
	}

	
	public BTNode(E element, BTPosition<E> parent,
				BTPosition<E> left, BTPosition<E> right) { 
		super(element, parent); 
		setLeft(left); 
		setRight(right); 
	}
	
	public E getElement() {
		return super.element();
	}

	public void setElement(E element) {
		super.setData(element);
	}


	public BTPosition<E> getLeft() {
		return left;
	}


	public void setLeft(BTPosition<E> left) {
		this.left = left;
	}


	public BTPosition<E> getParent() {
		BTNode<E> parent2 = (BTNode<E>) super.getParent();
		return parent2;
	}


	public void setParent(BTPosition<E> parent) {
		super.setParent(parent);
	}


	public BTPosition<E> getRight() {
		return right;
	}


	public void setRight(BTPosition<E> right) {
		this.right = right;
	}

	@Override
	public Iterable<Position<E>> getChildren() {
		NodePositionList<Position<E>> pList = new NodePositionList<Position<E>>(); 
		if (left != null)
			pList.addLast(left); 
		if (right != null) 
			pList.addLast(right); 
		return pList;
	}
	
	public void clean() { 
		super.clean(); 
		left = right = null; 
	}

}
