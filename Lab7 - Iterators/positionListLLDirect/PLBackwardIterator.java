package positionListLLDirect;

import java.util.Iterator;

import positionInterfaces.PositionList;

public class PLBackwardIterator<E> implements PositionListIteratorMaker<E> {

	@Override
	public Iterator<E> makeIterator(PositionList<E> pl) {
		return new PositionListElementsBackwardIterator<E>(pl);
	}


}

