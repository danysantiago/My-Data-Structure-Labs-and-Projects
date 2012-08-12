package sortingTest;

import java.util.Comparator;

public class PairComparator1 implements Comparator {

	public int compare(Object arg0, Object arg1) {
		Pair p1 = (Pair) arg0; 
		Pair p2 = (Pair) arg1; 
		int a1 = p1.getFirst(); 
		int a2 = p2.getFirst(); 
		int b1 = p1.getSecond(); 
		int b2 = p2.getSecond(); 
		if (a1 == a2) 
			if (b1 == b2) return 0; 
			else if (b1 < b2) return -1; 
			else return 1; 
		else if (a1 < a2) return -1; 
		else return 1; 
	}

}
