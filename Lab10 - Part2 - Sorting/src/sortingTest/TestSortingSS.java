package sortingTest;

import java.util.ArrayList;
import java.util.Random;

import sortUtilities.ArrayListPortion;
import sortUtilities.SelectionSortSorter;

public class TestSortingSS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Selection Sort Tester\n "); 
		final int N = 100;
		Random rnd = new Random(); 
		ArrayList<Pair> p = new ArrayList<Pair>(); 
		for (int i=0; i<N; i++) { 
			p.add(new Pair(rnd.nextInt(100), rnd.nextInt(100))); 
		}
		
		System.out.println("Original Array is: "); 
		show(p); 
		
		SelectionSortSorter<Pair> qs = new SelectionSortSorter<Pair>(new PairComparator1()); 
		qs.sort(new ArrayListPortion<Pair>(p, 0, p.size()-1)); 
		
		System.out.println("\n\nSorting using PairComparator1..."); 
		System.out.println("Sorted Array in ORDER is: "); 
		show(p); 

	
	    qs = new SelectionSortSorter<Pair>(new PairComparator2()); 
		qs.sort(new ArrayListPortion<Pair>(p, 0, p.size()-1)); 
		
		System.out.println("\n\nSorting using PairComparator2..."); 
		System.out.println("Sorted Array in ORDER is: "); 
		show(p); 


} 
	
	public static void show(ArrayList<Pair> p) { 
		int i=0; 
		for(Pair e : p)
			System.out.println("POS "+ (i++) +" = " + e); 
	}

}
