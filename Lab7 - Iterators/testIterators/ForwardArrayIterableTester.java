package testIterators;

import arrayIterators.ForwardArrayIterable;
import arrayIterators.ReverseArrayIterable2;

public class ForwardArrayIterableTester {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // auto boxing....
		ForwardArrayIterable<Integer> c = 
			new ForwardArrayIterable<Integer>(a); 

		showElements(c); 

		String[] b = {"Perro", "Gato", "Casa", "Lomo", "Vaca", "Buho", 
		"Pelagato"}; 
		ForwardArrayIterable<String> c2 = 
			new ForwardArrayIterable<String>(b); 

		showElements(c2); 

	}


	private static <E> void showElements(Iterable<E> c) { 
		for (E e : c)
			System.out.println(e); 
	}

}
