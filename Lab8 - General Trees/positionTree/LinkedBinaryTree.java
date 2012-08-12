package positionTree;

import exceptionClasses.BoundaryViolationException;
import exceptionClasses.EmptyTreeException;
import exceptionClasses.InvalidPositionException;
import exceptionClasses.NonEmptyTreeException;

import positionInterfaces.BinaryTree;
import positionInterfaces.Position;

public class LinkedBinaryTree<E> extends GTree<E> implements BinaryTree<E> 
{

	private BTNode<E> root; 
	private int size; 
	
	public LinkedBinaryTree() {}
	
	protected BTNode<E> checkPosition(Position<E> v) 
			throws InvalidPositionException 
	{ 
		if (!(v instanceof BTNode))
			throw new InvalidPositionException("Invalid node for binary tree."); 
		return (BTNode<E>) v; 
	}
	
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNode<E> vbtn = checkPosition(v); 
		return vbtn.getLeft() != null; 
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNode<E> vbtn = checkPosition(v); 
		return vbtn.getRight() != null; 
	}

	public Position<E> left(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
		BTNode<E> vbtn = checkPosition(v); 
		return vbtn.getLeft(); 
	}

	public Position<E> right(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
		BTNode<E> vbtn = checkPosition(v); 
		return vbtn.getRight(); 
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		return !this.hasLeft(v) && !this.hasRight(v); 
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		return this.hasLeft(v) || this.hasRight(v); 
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTNode<E> vbtn = checkPosition(v); 
		return vbtn == root; 
	}

	public Position<E> root() throws EmptyTreeException {
		return root; 
	}

	public int size() {
		return size; 
	}

	public Position<E> addRoot(E e) throws NonEmptyTreeException { 
		if (size != 0) 
			throw new NonEmptyTreeException("Cannot add a root to a non-empty tree."); 
		root = new BTNode<E>(e, null); 
		size++; 
		return root; 
	}
	
	public Position<E> insertLeft(Position<E> v, E e) 
			throws InvalidPositionException {
		BTNode<E> vbtn = checkPosition(v); 
		if (hasLeft(vbtn)) 
			throw new InvalidPositionException("Cannot add a left child to a node having one already"); 
		// create new node with e as element and vbtn as parent
		BTNode<E> nNode = new BTNode<E>(e, vbtn); 
		vbtn.setLeft(nNode); 
		size++; 
		return nNode; 
	}
	
	public Position<E> insertRight(Position<E> v, E e) 
			throws InvalidPositionException {
		BTNode<E> vbtn = checkPosition(v); 
		if (hasRight(vbtn)) 
			throw new InvalidPositionException("Cannot add a right child to a node having one already"); 
		// create new node with e as element and vbtn as parent
		BTNode<E> nNode = new BTNode<E>(e, vbtn); 
		vbtn.setRight(nNode); 
		size++; 
		return nNode; 
	}
	
	public E remove(Position<E> v) 
		throws InvalidPositionException { 
		BTNode<E> vbtn = checkPosition(v); 
		if (vbtn.getLeft() == null)
			replaceNode(vbtn, (BTNode<E>) vbtn.getRight()); 
		else if (vbtn.getRight() == null) 
			replaceNode(vbtn, (BTNode<E>) vbtn.getLeft()); 
		else
			throw new InvalidPositionException("Cannot delete internal node"); 
		
		size--; 
		
		// get element to return and clean node before returning..
		E etr = vbtn.element(); 
		vbtn.clean(); 
		return etr; 
	}
	
	private void replaceNode(BTNode<E> tNode, BTNode<E> rNode) { 
		if (tNode == root) { 
			root = rNode; 
			if (root != null)
				root.setParent(null); 
		}
		else { 
			BTNode<E> parent = (BTNode<E>) tNode.getParent(); 
			if (parent.getLeft() == tNode) 
				parent.setLeft(rNode); 
			else 
				parent.setRight(rNode); 
			if (rNode != null)
				rNode.setParent(parent); 
		}
	}

	public void attach(Position<E> v, BinaryTree<E> t1, BinaryTree<E> t2)
		throws InvalidPositionException { 
		BTNode<E> vbtn = checkPosition(v); 
		if (isInternal(vbtn)) 
			throw new InvalidPositionException("Cannot attach to an internal node"); 
		
		if (t1 != null) { 
			vbtn.setLeft((BTNode<E>) t1.root()); 
			((BTNode<E>) t1.root()).setParent(vbtn); 
			size += t1.size(); 
		} 
		else
			vbtn.setLeft(null); 
		
		if (t2 != null) { 
			vbtn.setRight((BTNode<E>) t2.root()); 
			((BTNode<E>) t1.root()).setParent(vbtn); 
			size += t2.size(); 
		} 
		else
			vbtn.setRight(null); 
		
		// this operation should destroy the connection of object area for 
		// both, t1 and t2, with their respective roots. Otherwise, the 
		// operation is dangerous, since it may cause the incorrect 
		// release of memory areas that are part of a tree. Such thing
		// happens when the tree objects referenced by t1 or t2 are 
		// destroyed elsewhere... Therefore, we need to do something like
		// the following when t1 and t2 are not null. 
		
		if (t1 != null) { 
			((LinkedBinaryTree<E>) t1).root = null; 
			((LinkedBinaryTree<E>) t1).size = 0; 
		} 
		if (t2 != null) { 
			((LinkedBinaryTree<E>) t2).root = null; 
			((LinkedBinaryTree<E>) t2).size = 0; 
		} 

	}
	
	
	private void cleanTree() { 
		if (!isEmpty()) {
			postOrderClean(root()); 
			root = null; 
			size = 0; 
		}
	}

	private void postOrderClean(Position<E> r) {
		TreeNode<E> rbtn = (BTNode<E>) r; 
		for (Position<E> c : rbtn.getChildren())
			postOrderClean(c); 
		rbtn.clean(); 
	}
	
	public void makeEmpty() { 
		cleanTree(); 
	}

}
