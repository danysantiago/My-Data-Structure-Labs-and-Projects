
import java.util.Comparator;

public class SortedListPriorityQueue<K, V> implements PriorityQueue<K, V> 
{

	private KeyValidator<K> kv; 
	private Comparator<K> cmp; 
	private int size; 
	private Node<Entry<K, V>> first; 
	
	public SortedListPriorityQueue(Comparator<K> cmp, KeyValidator<K> kv) 
	{ 
		this.kv = kv; 
		this.cmp = cmp; 
		first = null; 
		size = 0; 
	}
	
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (!kv.isValid(key))
			throw new InvalidKeyException("Invalid key to insert."); 
		PQEntry<K,V> entry = new PQEntry<K,V>(key, value); 
	
		first = recInsert(first, entry); 
		size++; 
		return entry; 
	}

	private Node<Entry<K,V>> recInsert(
			Node<Entry<K, V>> first, PQEntry<K,V> entry) {
		if (first == null) 
			return new Node<Entry<K,V>>(entry, null); 
		else if (cmp.compare(entry.getKey(), first.getElement().getKey()) < 0) 
			return new Node<Entry<K,V>>(entry, first); 
		else { 
			 first.setNext( recInsert(first.getNext(), entry) ); 
			 return first; 
		}
				
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (size == 0)
			throw new EmptyPriorityQueueException("The queue is empty"); 
		else
			return first.getElement(); 
	}
	
	public int size() {
		return size; 
	}

	/// need to implement this method
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(size == 0)
			throw new EmptyPriorityQueueException("The queue is empty");
		
		Entry<K,V> etr = first.getElement();
		Node<Entry<K,V>> ntd = first;
		first = first.getNext();
		ntd.clean();
		
		return etr;
	}
	
	
	// a toString method... for the purpose of testing....
	public String toString() { 
		String qc = ""; 
		Node<Entry<K,V>> current = first; 
		while (current != null)  { 
			qc = qc + current.getElement().getValue() + "\n"; 
			current = current.getNext(); 
		}
		return qc; 
	}

	///////////////////////////////////////////////////////////////////
	///////////  internal classes
	///////////////////////////////////////////////////////////////////
	private static class PQEntry<K,V> implements Entry<K, V> { 
		private K key; 
		private V value; 
		public PQEntry(K key, V value) { 
			this.key = key; 
			this.value = value; 
		}
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
	}
	
	private static class Node<E>  {
		private E element; 
		private Node<E> next; 
		public Node() { 
			element = null; 
			next = null; 
		}
		public Node(E data, Node<E> next) { 
			this.element = data; 
			this.next = next; 
		}
		public Node(E data)  { 
			this.element = data; 
			next = null; 
		}
		public E getElement() {
			return element;
		}
		public void setElement(E data) {
			this.element = data;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		public void clean() { 
			element = null; 
			next = null; 
		}
	}


}
