import java.util.Comparator;


public class IntegerSorter { 
	
	public void sort(Integer[] arr, Comparator<Integer> c) { 
		MinHeapPriorityQueue<Integer, Integer> pq = 
			new MinHeapPriorityQueue<Integer, Integer>(c, new IntegerValidator()); 
		
		for (Integer e : arr)
			pq.insert(e, e); 
		
		for (int i=0; i<arr.length; i++) 
			arr[i] = pq.removeMin().getValue(); 

	}
	
	private static class IntegerValidator implements KeyValidator<Integer> {

		public boolean isValid(Integer key) {
			// just to comply with the prty queue implementation....
			return true;
		} 
		
	}
}