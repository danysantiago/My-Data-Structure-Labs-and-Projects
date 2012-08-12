package positionListLLDirect;

import java.util.Iterator;

import positionInterfaces.PositionList;

public class PLFowardIterator<E> implements PositionListIteratorMaker<E>{

	@Override
	public Iterator<E> makeIterator(PositionList<E> pl) {
		return new PositionListElementsIterator<E>(pl);
	}


}
