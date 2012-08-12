package treeTesters;

import positionInterfaces.Tree;
import positionTree.BTNode;
import positionTree.LinkedBinaryTree;
import positionTree.LinkedBinaryTreeBFS;
import positionTree.LinkedBinaryTreeInOI;
import positionTree.LinkedBinaryTreePostOI;
import positionTree.LinkedBinaryTreePreORLI;

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
		
		showTreeElements(t1); 
		
		t1.makeEmpty(); 
		showTreeElements(t1); 
		
		LinkedBinaryTreePostOI<Integer> t2 = new LinkedBinaryTreePostOI<Integer>(); 
		v = (BTNode<Integer>) t2.addRoot(4); 
		v = (BTNode<Integer>) t2.insertLeft(v, 9);
		t2.insertLeft(v, 7);
		t2.insertRight(v, 10);
		v = (BTNode<Integer>) t2.insertRight(v.getParent(), 20);
		v = (BTNode<Integer>) t2.insertLeft(v, 15);
		t2.insertLeft(v, 12);
		v = (BTNode<Integer>) t2.insertRight(v, 17);
		t2.insertLeft(v, 19);
		v = (BTNode<Integer>) t2.insertRight(v.getParent().getParent(), 21);
		v = (BTNode<Integer>) t2.insertRight(v, 40);
		t2.insertLeft(v, 30);
		t2.insertRight(v, 45);

		showTreeElements(t2);
		
		LinkedBinaryTreeInOI<Integer> t3 = new LinkedBinaryTreeInOI<Integer>(); 
		v = (BTNode<Integer>) t3.addRoot(4); 
		v = (BTNode<Integer>) t3.insertLeft(v, 9);
		t3.insertLeft(v, 7);
		t3.insertRight(v, 10);
		v = (BTNode<Integer>) t3.insertRight(v.getParent(), 20);
		v = (BTNode<Integer>) t3.insertLeft(v, 15);
		t3.insertLeft(v, 12);
		v = (BTNode<Integer>) t3.insertRight(v, 17);
		t3.insertLeft(v, 19);
		v = (BTNode<Integer>) t3.insertRight(v.getParent().getParent(), 21);
		v = (BTNode<Integer>) t3.insertRight(v, 40);
		t3.insertLeft(v, 30);
		t3.insertRight(v, 45);

		showTreeElements(t3);
		
		LinkedBinaryTreeBFS<Integer> t4 = new LinkedBinaryTreeBFS<Integer>(); 
		v = (BTNode<Integer>) t4.addRoot(4); 
		v = (BTNode<Integer>) t4.insertLeft(v, 9);
		t4.insertLeft(v, 7);
		t4.insertRight(v, 10);
		v = (BTNode<Integer>) t4.insertRight(v.getParent(), 20);
		v = (BTNode<Integer>) t4.insertLeft(v, 15);
		t4.insertLeft(v, 12);
		v = (BTNode<Integer>) t4.insertRight(v, 17);
		t4.insertLeft(v, 19);
		v = (BTNode<Integer>) t4.insertRight(v.getParent().getParent(), 21);
		v = (BTNode<Integer>) t4.insertRight(v, 40);
		t4.insertLeft(v, 30);
		t4.insertRight(v, 45);

		showTreeElements(t4);
		
		LinkedBinaryTreePreORLI<Integer> t5 = new LinkedBinaryTreePreORLI<Integer>(); 
		v = (BTNode<Integer>) t5.addRoot(4); 
		v = (BTNode<Integer>) t5.insertLeft(v, 9);
		t5.insertLeft(v, 7);
		t5.insertRight(v, 10);
		v = (BTNode<Integer>) t5.insertRight(v.getParent(), 20);
		v = (BTNode<Integer>) t5.insertLeft(v, 15);
		t5.insertLeft(v, 12);
		v = (BTNode<Integer>) t5.insertRight(v, 17);
		t5.insertLeft(v, 19);
		v = (BTNode<Integer>) t5.insertRight(v.getParent().getParent(), 21);
		v = (BTNode<Integer>) t5.insertRight(v, 40);
		t5.insertLeft(v, 30);
		t5.insertRight(v, 45);

		showTreeElements(t5); 
		
		
		
		
		

		
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
