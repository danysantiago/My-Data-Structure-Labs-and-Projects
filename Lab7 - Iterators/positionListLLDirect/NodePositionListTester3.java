package positionListLLDirect;

import positionInterfaces.Position;

public class NodePositionListTester3 {

	public static void main(String[] args) {
		
		NodePositionList3<Integer> w = new NodePositionList3<Integer> ();
		NodePositionList3<Integer> q = new NodePositionList3<Integer> (new PLBackwardIterator<Integer>()); 
		
		w.addFirst(5); 
		w.addFirst(3);
		q.addFirst(5); 
		q.addFirst(3);
		
		w.addLast(10); 
		w.addLast(13); 
		q.addLast(10); 
		q.addLast(13); 
		
		Position<Integer> p;
		
		p = w.first();
		w.addAfter(p, 2); 
		p = w.next(p); 
		w.addAfter(p, 34); 
		w.addBefore(p, 40);
		
		p = q.first();
		q.addAfter(p, 2); 
		p = q.next(p); 
		q.addAfter(p, 34); 
		q.addBefore(p, 40); 
		
		System.out.println("Foward Iterator:");
		showElements(w);
		System.out.println("Backward Iterator:");
		showElements(q);

	}

	private static <E> void showElements(NodePositionList3<E> w) {
		for(E p : w) 
			System.out.println(p); 
	}
	
	

}
