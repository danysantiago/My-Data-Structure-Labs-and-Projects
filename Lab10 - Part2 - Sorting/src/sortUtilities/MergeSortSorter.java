package sortUtilities;
import java.util.ArrayList;
import java.util.Comparator;



public class MergeSortSorter<E> extends DCSorter<E> {

	private Comparator<E> cmp; 
	
	public MergeSortSorter(Comparator<E> cmp) {
		this.cmp = cmp; 
	}


	@Override
	protected void combine(ArrayListPortion<E> list, 
			ArrayList<ArrayListPortion<E>> partitions) 
	{
		ArrayList<E> sortedList = new ArrayList<E>(); 
		ArrayListPortion<E> list1 =  partitions.get(0); 
		ArrayListPortion<E> list2 =  partitions.get(1); 
		int indexL1 = 0, indexL2 = 0;
		
		while(indexL1 < list1.size() && indexL2 < list2.size()){
			if(cmp.compare(list1.get(indexL1), list2.get(indexL2)) < 0)
				sortedList.add(list1.get(indexL1++));
			else
				sortedList.add(list2.get(indexL2++));
		}
		
		while(indexL1 < list1.size())
			sortedList.add(list1.get(indexL1++));
		while(indexL1 < list2.size())
			sortedList.add(list2.get(indexL2++));
		
		for(int i = 0; i < sortedList.size(); i++)
			list.set(i, sortedList.get(i));
	}

	@Override
	protected ArrayList<ArrayListPortion<E>> partition(ArrayListPortion<E> list) {
		// create the partitions over the underlying ArrayList
		// divide more or less in two halves...
		int mid = (list.size()-1) / 2;   // (0+list.size()-1)/2
		ArrayList<ArrayListPortion<E>> partitions = new ArrayList<ArrayListPortion<E>>(); 
		partitions.add(new ArrayListPortion<E>(list, 0, mid)); 
		partitions.add(new ArrayListPortion<E>(list, mid+1, list.size()-1)); 
		return  partitions;
	}

}
