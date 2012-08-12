package testers;

import positionInterfaces.Tree;
import positionTree.BTNode;
import positionTree.LinkedBinaryTree;

public class LinkedBinaryTreeTester {

	public static void main(String[] args) {
		LinkedBinaryTree<Integer> t1 = new LinkedBinaryTree<Integer>(); 
		BTNode<Integer> v; 
		
		v = (BTNode<Integer>) t1.addRoot(4); 
		v = (BTNode<Integer>) t1.insertLeft(v, 9);
		t1.insertLeft(v, 7);
		t1.insertRight(v, 10);
		v = (BTNode<Integer>) t1.insertRight(v.getParent(), 20);
		v = (BTNode<Integer>) t1.insertLeft(v, 15);
		t1.insertLeft(v, 12);
		v = (BTNode<Integer>) t1.insertRight(v, 17);
		t1.insertLeft(v, 19);
		v = (BTNode<Integer>) t1.insertRight(v.getParent().getParent(), 21);
		v = (BTNode<Integer>) t1.insertRight(v, 40);
		t1.insertLeft(v, 30);
		t1.insertRight(v, 45);
		
		System.out.println("Width: " + t1.width());
		System.out.println("Height: " + t1.height());
		System.out.println("Depth of '" + v.element() + "': " + t1.depth(v));
		System.out.println("PreOrderNext of '" + v.element() + "': " + t1.preorderNext(v).element());
		System.out.println("InOrderNext of '" + v.element() + "': " +t1.inorderNext(v).element());
		System.out.println("PostOrderNext of '" + v.element() + "': " +t1.postorderNext(v).element());
		
		
	}
	
	/**
	 * Shows the elements in a particular tree based on the available
	 * iterator for the particular type of tree....
	 * @param t the tree to traverse...
	 */
	private static void showTreeElements(Tree<Integer> t) { 
		System.out.println("The tree has "+ t.size() + " elements. These are: "); 
		for (Integer e : t)
			System.out.print(e + " ");
		System.out.println(); 
	}

}
