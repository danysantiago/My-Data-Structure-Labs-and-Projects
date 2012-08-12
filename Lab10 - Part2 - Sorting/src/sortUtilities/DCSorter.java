package sortUtilities;
import java.util.ArrayList;

public abstract class DCSorter<E> {
		
	public void sort(ArrayListPortion<E> list) { 
		if (list.size() > 1) { 

			// partition the list to sort in two lists
			ArrayList<ArrayListPortion<E>> partitions = partition(list ); 
			
			// sort each list in the partition
			sort(partitions.get(0));     // sort the first list in place
			sort(partitions.get(1));     // sort the second list in place

			combine(list, partitions);      // combine back the two sorted lists
		}
		// nothing to do if size of the list is <= 1 since already 
		// sorted...
	}

	protected abstract void combine(ArrayListPortion<E> list, 
			ArrayList<ArrayListPortion<E>> partitions);
	protected abstract ArrayList<ArrayListPortion<E>> 
	partition(ArrayListPortion<E> list);
	
}
