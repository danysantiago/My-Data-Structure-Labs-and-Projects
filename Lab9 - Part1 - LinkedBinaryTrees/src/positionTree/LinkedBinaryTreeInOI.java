package positionTree;

import positionInterfaces.Position;
import positionListLLDirect.NodePositionList;

public class LinkedBinaryTreeInOI<E> extends LinkedBinaryTree<E> {

	protected void fillIterator(TreeNode<E> r, NodePositionList<Position<E>> list) {
		BTNode<E> rbtn = (BTNode<E>) r; 
		if (rbtn != null) {			
			fillIterator((BTNode<E>) rbtn.getLeft(), list);
			list.addLast(rbtn);
			fillIterator((BTNode<E>) rbtn.getRight(), list); 
			}
	}
	
	public void createFrom(LinkedBinaryTree<E> t){
		this.setRoot(t.getRoot());
	}
}
