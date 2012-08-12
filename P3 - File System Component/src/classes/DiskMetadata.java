package classes;

/**
 * Storage class used to store the Meta Data of a Disk Unit
 * @author Dany
 *
 */
public class DiskMetadata {
	//Instance fields
	int capacity;
	int blockSize;
	int amountOfInodes;
	int firstFreeBlockPint;
	int amountOfBlocks;
	
	//Constructor
	public DiskMetadata(int capacity, int blockSize, int amountOfInodes,
			int firstFreeBlockPint) {
		super();
		this.capacity = capacity;
		this.blockSize = blockSize;
		this.amountOfInodes = amountOfInodes;
		this.firstFreeBlockPint = firstFreeBlockPint;
		amountOfBlocks = capacity/blockSize;
	}

	//Getters
	public int getCapacity() {
		return capacity;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public int getAmountOfInodes() {
		return amountOfInodes;
	}

	public int getFirstFreeBlockPoint() {
		return firstFreeBlockPint;
	}

	public int getAmountOfBlocks() {
		return amountOfBlocks;
	}
	
	
	
	
}
