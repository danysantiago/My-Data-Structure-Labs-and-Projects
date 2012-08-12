package testIterators;

import linkedLists.DLDHDTListIterableF2L;


public class DLDHDTListIterableF2LTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // auto boxing....
		DLDHDTListIterableF2L<Integer> list1 = new DLDHDTListIterableF2L<Integer>(); 
		for (int i = a.length-1; i>=0; i--) { 
			linkedLists.Node<Integer> node = list1.createNewNode(); 
			node.setElement(a[i]); 
			list1.addFirstNode(node); 
		}
		showElements(list1); 

		String[] b = {"Perro", "Gato", "Casa", "Lomo", "Vaca", "Buho", 
		"Pelagato"}; 
		DLDHDTListIterableF2L<String> list2 = new DLDHDTListIterableF2L<String>(); 
		for (int i = b.length-1; i>=0; i--) { 
			linkedLists.Node<String> node = list2.createNewNode(); 
			node.setElement(b[i]); 
			list2.addFirstNode(node); 
		}
		showElements(list2); 

	}


	private static <E> void showElements(Iterable<E> c) { 
		for (E e : c)
			System.out.println(e); 
	}


}
