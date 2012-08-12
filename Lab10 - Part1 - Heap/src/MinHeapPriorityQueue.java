import java.util.ArrayList;
import java.util.Comparator;


public class MinHeapPriorityQueue<K, V> implements PriorityQueue<K, V>
{
	private KeyValidator<K> kv; 
	private Comparator<K> cmp; 
	private ArrayList<Entry<K, V>> h; 
	
	public MinHeapPriorityQueue(Comparator<K> cmp, KeyValidator<K> kv) 
	{ 
		this.kv = kv; 
		this.cmp = cmp; 
		h = new ArrayList<Entry<K, V>>(); 
	}
	
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (!kv.isValid(key))
			throw new InvalidKeyException("Invalid key to insert."); 
		
		PQEntry<K,V> entry = new PQEntry<K,V>(key, value); 
		
		h.add(entry); 
		int current = h.size()-1; 
		
		boolean treeIsMinHeap = false; 
		while (!treeIsMinHeap && current != 0) { 
			int parent = (current-1) / 2; 
			
			K pk = h.get(parent).getKey(); 
		   	if (cmp.compare(pk, key) > 0) { 
			   h.set(parent, h.set(current, h.get(parent))); 
		   	   current = parent;
		   	} 
		   	else 
		   		treeIsMinHeap = true;
		}
	
		return entry; 
	}

	public boolean isEmpty() {
		return h.isEmpty();
	}

	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (isEmpty())
			throw new EmptyPriorityQueueException("The queue is empty"); 
			
		return h.get(0); 
	}
	
	public int size() {
		return h.size(); 
	}

	/// need to implement this method
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if (isEmpty())
			throw new EmptyPriorityQueueException("The queue is empty"); 

		if (h.size() == 1) 
			return h.remove(0); 
		
		// size is at least 2
		Entry<K,V> etr = h.get(0); 
		
		// remove last leaf and place its element in root
		h.set(0, h.remove(h.size()-1));     

		downHeap(0);
		
		return etr; 
		
	}
	
	
	private void downHeap(int current) {
		int lci = 2*current + 1;   // left child index
		int rci = lci+1;      	   // right child index
		int mci = lci;       	   // min child index
		
		if(lci < h.size()){
			mci = lci;
			if(rci < h.size())
				mci = getMinElementIndex(rci , lci);
			if(cmp.compare(h.get(current).getKey(), h.get(mci).getKey()) > 0){
				h.set(mci, h.set(current, h.get(mci)));
				downHeap(mci);
			}
		}
		
	}

	//Private method used in downHeap to get the min index between two child elements
	private int getMinElementIndex(int rci, int lci) {
		K rck = h.get(rci).getKey();
		K lck = h.get(lci).getKey();
		
		if(cmp.compare(rck, lck) < 0)
			return rci;
		else
			return lci;
	}

	// a toString methhod... for the purpose of testing....
	public String toString() { 
		String qc = ""; 
		int index = 0; 
		for (Entry<K,V> e : h) { 
			qc = qc + "h[" +index+++ "] has entry with " + e.getValue() + "\n"; 

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
	
	
}
