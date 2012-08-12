package testIterators;

import arrayIterators.ReverseArrayIterator;

public class ReverseArrayIteratorTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // auto boxing....
		ReverseArrayIterator<Integer> iter = 
			new ReverseArrayIterator<Integer>(a); 
		
		while (iter.hasNext()) { 
			Integer e = iter.next(); 
			System.out.println(e); 
		}
	}

}
