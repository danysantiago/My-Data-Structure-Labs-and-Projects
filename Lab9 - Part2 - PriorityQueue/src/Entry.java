
public interface Entry<K, V> { 
	/** Accesses the key of the entry.
		    @return reference to the key value of the entry. 
	 **/ 
	K getKey(); 

	/** Accesses the value of the entry. 
		    @return reference to the value in the entry. 
	 **/
	V getValue(); 
}
