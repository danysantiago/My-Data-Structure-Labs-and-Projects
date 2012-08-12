package classes;

/**
 * Block entry class used in the freeblocklist in the File System Component
 * this class is a helper in keeping track of the Linked List of free blocks
 * @author Dany
 *
 */
public class BlockEntry {
	private int index; //Block index
	private int nextIndex; //Next free block index
	
	//Constructor
	public BlockEntry(int index, int nextIndex) {
		super();
		this.index = index;
		this.nextIndex = nextIndex;
	}

	//Getters
	public int getIndex() {
		return index;
	}
	
	public int getNextIndex() {
		return nextIndex;
	}
	

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	//toString so I can debug happily
	@Override
	public String toString() {
		return "<"+index+","+nextIndex+">";
	}
	
	
	
	
	
}
