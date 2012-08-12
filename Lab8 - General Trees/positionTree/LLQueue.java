package positionTree;

import exceptionClasses.EmptyQueueException;


public class LLQueue<E> implements Queue<E>
{
	private SNode<E> front, rear; 
	private int size; 
	
	public LLQueue() {
		front = rear = null; 
		size = 0; 
	}
	
	public E dequeue() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException("dequeue: Queue is empty."); 
		E etr = front.getElement(); 

		front = front.getNext();
		size--;
		return etr;
	}

	public void enqueue(E e) {
		SNode<E> nn = new SNode<E>(e); 
		if (size == 0)
			front = nn; 
		else 
			rear.setNext(nn); 
		rear = nn; 
		size++; 
	}

	public E front() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException("front: Queue is empty."); 
		return front.getElement();
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	// just for testing and grading....
    public void showReverse() { 
	    if (size == 0)
		   System.out.println("Queue is empty."); 
		else
		   recSR(front);
    } 
    private void recSR(SNode<E> f) { 
		if (f != null) { 
		   recSR(f.getNext()); 
		   System.out.println(f.getElement()); 
	     } 
    } 


}
