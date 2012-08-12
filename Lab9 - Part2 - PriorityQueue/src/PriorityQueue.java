
public interface PriorityQueue<K, V> { 
	/** The size of the collection…. **/
	int size(); 

	/** Returns true if empty; false, otherwise. **/ 
	boolean isEmpty(); 

	/** Add a new entry (key, value) to the queue. 
		    @param key the key for the entry
		    @param value the value for the entry
		    @return Reference to the Entry object creater for 
				the pair key-value. 
		    @throw InvalidKeyException if key is invalid. 
	 **/
	Entry<K, V> insert(K key, V value) throws InvalidKeyException; 

	/** Accesses entry in the collection having minimum value 
		    of the key according to a particular relation order.
		    @return Reference to an entry with min key value. 
		    @throw EmptyPriorityQueueException if queue is empty. 
	 **/
	Entry<K, V> min() throws EmptyPriorityQueueException; 


	/** Removes entry in the collection having minimum value 
		    of the key according to a particular relation order.
		    @return Reference to an entry removed. 
		    @throw EmptyPriorityQueueException if queue is empty. 
	 **/
	Entry<K, V> removeMin()throws EmptyPriorityQueueException; 
} 
