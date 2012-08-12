package sortUtilities;

import java.util.ArrayList;
import java.util.Comparator;

public class SelectionSortSorter<E> extends DCSorter<E> {

	private Comparator<E> cmp; 

	public SelectionSortSorter(Comparator<E> cmp) {
		this.cmp = cmp; 
	}


	@Override
	protected void combine(ArrayListPortion<E> list, 
			ArrayList<ArrayListPortion<E>>partitions) 
	{
		// Nothing needs to be done in this case!!!
	}

	@Override
	protected ArrayList<ArrayListPortion<E>> 
	partition(ArrayListPortion<E> list) 
	{ 

		int minValPos = 0; 
	
		for (int i=1; i<list.size(); i++)
			if (cmp.compare(list.get(i), list.get(minValPos)) < 0)
				minValPos = i; 
		
		if (minValPos != 0) 
			list.set(0, list.set(minValPos, list.get(0)));


		// create the partitions over the undelying ArrayList
		ArrayList<ArrayListPortion<E>> partitions = new ArrayList<ArrayListPortion<E>>(); 

		partitions.add(new ArrayListPortion<E>(list, 0, 0)); 
		partitions.add(new ArrayListPortion<E>(list, 1, list.size()-1)); 

		return  partitions;
	}

}

