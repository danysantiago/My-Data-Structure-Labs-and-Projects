package positionTree;

import positionInterfaces.Position;
import positionListLLDirect.NodePositionList;

public class LinkedBinaryTreeBFS<E> extends LinkedBinaryTree<E> {
	
	LLQueue<BTNode<E>> q = new LLQueue<BTNode<E>>();

	protected void fillIterator(TreeNode<E> r, NodePositionList<Position<E>> list) {
		BTNode<E> rbtn = (BTNode<E>) r;
		q.enqueue(rbtn);
		while (!q.isEmpty()) {
			rbtn = q.dequeue();
			list.addLast(rbtn);
			if(rbtn.getLeft() != null)
				q.enqueue((BTNode<E>) rbtn.getLeft());
			if(rbtn.getRight() != null)
				q.enqueue((BTNode<E>) rbtn.getRight());
		}					
	}
}
