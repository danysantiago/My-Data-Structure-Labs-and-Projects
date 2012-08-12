package classes;


/**
 * @author Dany
 *
 */
public class VirtualDiskBlock{
	/**
	 * 
	 */
	private byte[] elements;

	/**
	 * 
	 */
	public VirtualDiskBlock() {
		this(256);
	}

	/**
	 * @param blockCapacity
	 */
	public VirtualDiskBlock(int blockCapacity) {
		elements = new byte[blockCapacity];
	}

	/**
	 * @return
	 */
	public int getCapacity() {
		return elements.length;
	}

	/**
	 * @param index
	 * @param nuevo
	 */
	public void setElement(int index, byte nuevo) {
		elements[index] = nuevo;
	}

	/**
	 * @param index
	 * @return
	 */
	public byte getElement(int index) {
		return elements[index];
	}
	
	

}
