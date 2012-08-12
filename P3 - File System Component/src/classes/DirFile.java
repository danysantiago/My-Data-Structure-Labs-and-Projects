package classes;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Class that represents a DirFile in the memory.
 * @author Dany
 *
 */
public class DirFile {
	private ArrayList<String> contentNames; //List of the directory's content names
	private ArrayList<Integer> contentINodes; //List of the directory's content i node index
	private int contentAmount;
	
	//Empty dir file constructor
	public DirFile(){
		contentAmount = 0;
		contentNames = new ArrayList<String>();
		contentINodes = new ArrayList<Integer>();
	}
	
	//Constructor that parses a ByteBuffer into a Dir File
	public DirFile(ByteBuffer bbuffer) {
		//Initialize instance fields
		bbuffer.position(0);
		contentAmount = 0;
		contentNames = new ArrayList<String>();
		contentINodes = new ArrayList<Integer>();
		
		//Parse ByteBuffer
		while(bbuffer.hasRemaining()){
			byte[] tempName = new byte[Utils.MAX_NAME_SIZE];
			bbuffer.get(tempName);
			contentNames.add((new String(tempName)).trim());
			contentINodes.add(bbuffer.getInt());
			contentAmount++;
		}
	}
	
	//Getters
	public ArrayList<String> getContentNames() {
		return contentNames;
	}

	public ArrayList<Integer> getContentINodes() {
		return contentINodes;
	}

	public int getContentAmount() {
		return contentAmount;
	}
	
	//Size calculation is done as follow:
	//Every char = 1 byte
	//Integer = 4 byte (i node pointers)
	//So max length of a name + 4 = 1 content size
	//amount of content times a content size equals total size
	public int size(){
		return (Utils.MAX_NAME_SIZE+4)*contentAmount;
	}


	/**
	 * Add content to the dir file
	 * @param name content name
	 * @param pointer content i node point
	 */
	public void addFile(String name, int pointer){
		contentNames.add(name);
		contentINodes.add(pointer);
		contentAmount++;
	}
	
	/**
	 * Get content from the dir file
	 * @param name of content
	 * @return content index, if no content found -1 is returned
	 */
	public int getContentIndex(String name){
		for(int i = 0; i < contentAmount; i++){
			if(name.equals(contentNames.get(i)))
					return i;
		}
		
		return -1;
	}


	/**
	 * Remove content from dir file
	 * @param i content index
	 */
	public void remove(int i) {
		contentNames.remove(i);
		contentINodes.remove(i);
		contentAmount--;	
	}
	
	/**
	 * toBBUffer converts the content of this dir file into a bytebuffer
	 * @return ByteBuffer representing this dir file
	 */
	public ByteBuffer toBBuffer() {
		ByteBuffer bbuffer = ByteBuffer.allocate(this.size());
		for(int i = 0; i < contentAmount; i++){
			bbuffer.put(Utils.stringToNameByteArray(contentNames.get(i)));
			bbuffer.putInt(contentINodes.get(i));
		}
		return bbuffer;
	}

	
}
