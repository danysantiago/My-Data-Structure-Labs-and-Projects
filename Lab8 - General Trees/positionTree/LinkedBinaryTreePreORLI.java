package positionTree;

import java.util.Stack;

import positionInterfaces.Position;
import positionListLLDirect.NodePositionList;

public class LinkedBinaryTreePreORLI<E> extends LinkedBinaryTree<E> {
	
	Stack<BTNode<E>> s = new Stack<BTNode<E>>();
	//Pre-Order from Right to Left Iterator
	protected void fillIterator(TreeNode<E> r, NodePositionList<Position<E>> list) {
		BTNode<E> rbtn = (BTNode<E>) r;
		s.push(rbtn);
		while (!s.isEmpty()) {
			rbtn = s.pop();
			list.addLast(rbtn);
			if(rbtn.getLeft() != null)
				s.push((BTNode<E>) rbtn.getLeft());
			if(rbtn.getRight() != null)
				s.push((BTNode<E>) rbtn.getRight());
		}					
	}
}
