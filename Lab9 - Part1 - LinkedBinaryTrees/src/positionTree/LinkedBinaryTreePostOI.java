package positionTree;

import positionInterfaces.Position;
import positionListLLDirect.NodePositionList;

public class LinkedBinaryTreePostOI<E> extends LinkedBinaryTree<E> {

	protected void fillIterator(TreeNode<E> r, NodePositionList<Position<E>> list) {
		BTNode<E> rbtn = (BTNode<E>) r; 
		if (rbtn != null) { 
			fillIterator((BTNode<E>) rbtn.getLeft(), list); 
			fillIterator((BTNode<E>) rbtn.getRight(), list); 
			list.addLast(rbtn);			}
	}
	
	public void createFrom(LinkedBinaryTree<E> t){
		this.setRoot(t.getRoot());
	}
}
