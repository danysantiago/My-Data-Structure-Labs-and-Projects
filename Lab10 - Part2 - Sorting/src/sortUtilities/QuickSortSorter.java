package sortUtilities;

import java.util.ArrayList;
import java.util.Comparator;

public class QuickSortSorter<E> extends DCSorter<E> {

	private Comparator<E> cmp; 
	
	public QuickSortSorter(Comparator<E> cmp) {
		this.cmp = cmp; 
	}


	@Override
	protected void combine(ArrayListPortion<E> list, 
			ArrayList<ArrayListPortion<E>> partitions) 
	{
		// Nothing needs to be done in this case!!!
	}

	@Override
	protected ArrayList<ArrayListPortion<E>> partition(ArrayListPortion<E> list) 
	{
		int left = 1, right = list.size()-1; 
		int pivot = 0;
		
		while(left <= right){
			
			while(left <= right && cmp.compare(list.get(left), list.get(pivot)) <= 0)
				left++;
			
			while(right >= left && cmp.compare(list.get(right), list.get(pivot)) >= 0)
				right--;
			
			if(left < right)
				list.set(left, list.set(right, list.get(left)));
		}
		
		list.set(pivot, list.set(right, list.get(pivot)));
		
		ArrayList<ArrayListPortion<E>> partions = new ArrayList<ArrayListPortion<E>>();
		partions.add(new ArrayListPortion<E>(list, 0, right-1));
		partions.add(new ArrayListPortion<E>(list, right+1, list.size()-1));
		
		return partions;
		
	}

}
