package positionListLLDirect;

import java.util.Iterator;

import positionInterfaces.PositionList;

public interface PositionListIteratorMaker<E> {
	Iterator<E> makeIterator(PositionList<E> pl);
}
