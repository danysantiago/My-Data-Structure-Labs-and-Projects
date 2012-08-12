package testIterators;

import arrayIterators.ReverseArrayIterable1;
import arrayIterators.ReverseArrayIterable2;

public class ReverseArrayIterable1Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // auto boxing....
		ReverseArrayIterable1<Integer> c = 
			new ReverseArrayIterable1<Integer>(a); 

		showElements(c); 

		String[] b = {"Perro", "Gato", "Casa", "Lomo", "Vaca", "Buho", 
		"Pelagato"}; 
		ReverseArrayIterable1<String> c2 = 
			new ReverseArrayIterable1<String>(b); 

		showElements(c2); 

	}


	private static <E> void showElements(Iterable<E> c) { 
		for (E e : c)
			System.out.println(e); 
	}

}
