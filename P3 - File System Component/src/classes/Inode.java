package classes;

import java.nio.ByteBuffer;

/**
 * Class that represents an i node in memory
 * @author Dany
 *
 */
public class Inode {
	//I node size (always the same, and thus it's a constant)
	public static final byte INODESIZE = 9;
	
	//I node type constants
	public static final byte FILEFLAG = 0;
	public static final byte DIRFLAG = 1;
	public static final byte EMPTYFLAG = -1;
	
	//Instance fields
	private byte typeflag; //Type of i node
	private int contentSize; //Size of the file which this i node corresponds
	private int blockPointer; //First block of the file this i node corresponds
	
	//Constructor
	public Inode(byte typeflag, int size, int blockPointer) {
		this.typeflag = typeflag;
		this.contentSize = size;
		this.blockPointer = blockPointer;
	}
	
	//Constructor that parses a Byte buffer into an i node
	public Inode(ByteBuffer bbuffer) {
		bbuffer.position(0);
		this.typeflag = bbuffer.get();
		this.contentSize = bbuffer.getInt();
		this.blockPointer = bbuffer.getInt();	
	}
	
	//Getters
	public int getTypeflag() {
		return typeflag;
	}
	
	public int getContentSize() {
		return contentSize;
	}
	
	public int getBlockPointer() {
		return blockPointer;
	}
	
	//toBBuffer converts this i node into a byte buffer
	public ByteBuffer toBBuffer(){
		ByteBuffer bbuffer = ByteBuffer.allocate(9);
		bbuffer.put((byte)typeflag);
		bbuffer.putInt(contentSize);
		bbuffer.putInt(blockPointer);
		bbuffer.position(0);
		return bbuffer;	
	}
	
	//Create an empty i node
	public static Inode createEmptyINode() {
		Inode inode = new Inode(EMPTYFLAG,0,0);
		return inode;
	}
	
}
