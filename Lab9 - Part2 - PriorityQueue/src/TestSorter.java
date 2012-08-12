
public class TestSorter {

	public static void main(String[] args) {
		Integer[] a = {34, 3, 3, 45, 90, 18, 13, 10, 7, 40, 41, 33, 100, 9, 1, 0, 17, 32, 23}; 
		IntegerSorter as = new IntegerSorter(); 

		System.out.println("Array elements initially: "); 
		showElements(a);

		as.sort(a, new IntegerComparator1()); 
		System.out.println("Array elements after sorting using order as in IntegerComparator1"); 
		showElements(a);
		
		as.sort(a, new IntegerComparator2()); 
		System.out.println("Array elements after sorting using order as in IntegerComparator2"); 
		showElements(a);
		
	}

	private static void showElements(Integer[] a) {
		System.out.println("Elements in the array are: "); 
		for (Integer e : a) 
			System.out.print(e + " "); 
		
		System.out.println("\n\n"); 
	}

}
