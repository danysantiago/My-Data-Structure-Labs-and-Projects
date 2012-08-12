import java.util.Comparator;


public class IntegerSorter {
	public void sort(Integer[] arr, Comparator<Integer> c) {
		SortedListPriorityQueue<Integer, Integer> pq =
				new SortedListPriorityQueue<Integer, Integer>(c, new IntegerValidator());
		for(int i = 0; i < arr.length; i++)
			pq.insert(arr[i], arr[i]);
		for(int i = 0; i < arr.length; i++)
			arr[i] = pq.removeMin().getValue();
	}

	// inner class – just to comply with the need for a validator… in the pq
	private static class IntegerValidator implements KeyValidator<Integer> {

		public boolean isValid(Integer key) {
			// just to comply with the prty queue implementation....
			// Assume that the key is always valid for the purpose of these
			// lab exercises.
			return true;
		}

	}

}
