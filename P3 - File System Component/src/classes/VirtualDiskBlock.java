package classes;


/**
 * Virtual Disk Block representing a chunk in the memory as used in P1
 * @author Dany
 *
 */
public class VirtualDiskBlock{
	//Byte array containing this blocks information
	private byte[] elements;

	//Default constructor
	public VirtualDiskBlock() {
		this(256);
	}
	
	//Constructor with a specified block size
	public VirtualDiskBlock(int blockCapacity) {
		elements = new byte[blockCapacity];
	}
	
	//Getters and Setters
	public int getCapacity() {
		return elements.length;
	}


	public void setElement(int index, byte nuevo) {
		elements[index] = nuevo;
	}

	public byte getElement(int index) {
		return elements[index];
	}
	

}
