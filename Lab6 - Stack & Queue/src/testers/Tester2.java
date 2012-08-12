package testers;

import classes.EmptyQueueException;
import classes.EmptyStackException;
import classes.LLQueue;
import classes.LLStack;

public class Tester2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LLQueue<Integer> s = new LLQueue<Integer>(); 
		int[] a = { 3, 2, 4, 55, 5, 1, 1, 17, 9, 3, 0, 4, 5, 7, 17, 28, 0, 9, 10, 40, 21, 22, 5, 6, -1}; 
		for (int n : a) { 
			try { 
				if (n == 0) 
					s.showReverse(); 
				else if (n % 2 == 1)
					s.enqueue(n); 
				else
					System.out.println("Extracting element: " + s.dequeue()); 
			}
			catch (EmptyQueueException e) { 
				System.out.println(e); 
			}
		}
		s.showReverse(); 
	}


}
