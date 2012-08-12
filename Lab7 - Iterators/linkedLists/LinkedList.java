package linkedLists;

public interface LinkedList<E> {
	int length(); 

	/**
	 * 
	 * @param target
	 * @return
	 * @throws NodeOutOfBoundsException
	 */
	Node<E> getNodeBefore(Node<E> target) throws NodeOutOfBoundsException; 
	
	/**
	 * 
	 * @param target
	 * @return
	 * @throws INodeOutOfBoundsException
	 */
	Node<E> getNodeAfter(Node<E> target) throws NodeOutOfBoundsException; 
	
	/**
	 *  @return reference to the first node of the linked list
	 *  @throws INodeOutOfBoundsException if the linked list is empty
	 */
	Node<E> getFirstNode() throws NodeOutOfBoundsException; 
	
	/**
	 * 
	 * @return
	 * @throws INodeOutOfBoundsException
	 */
	Node<E> getLastNode() throws NodeOutOfBoundsException; 
	
	/**
	 * 
	 * @param nuevo
	 */
	void addFirstNode(Node<E> nuevo); 
	
	/**
	 * 
	 * @param target
	 * @param nuevo
	 */
	void addNodeAfter(Node<E> target, Node<E> nuevo); 
	
	/**
	 * 
	 * @param target
	 * @param nuevo
	 */
	void addNodeBefore(Node<E> target, Node<E> nuevo); 
			
	/**
	 * 
	 * @param target
	 */
	void removeNode(Node<E> target); 
	
	
	/**
	 * Creates a new node instance of the type of nodes that the linked list
	 * uses. The new node will have all its instance fields initialized to
	 * null. The new node is not linked to the list in any way.
	 * @return reference to the new node instance. 
	 */
	Node<E> createNewNode(); 

}
