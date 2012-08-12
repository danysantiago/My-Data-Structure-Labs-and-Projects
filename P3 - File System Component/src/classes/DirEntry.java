package classes;


/**
 * DirEntry is a helper class for the stack of directories used in the File System Component
 * it holds the actual directory file ands the iNodeIndex of such file
 * @author Dany
 *
 */
public class DirEntry {
	private DirFile dirFile; //The dir file
	private int iNodeIndex; //The i node index corresponding to the dir file
	
	//Constructor
	public DirEntry(DirFile dirFile, int iNodeIndex) {
		super();
		this.dirFile = dirFile;
		this.iNodeIndex = iNodeIndex;
	}
	
	//Getters
	public DirFile getDirFile() {
		return dirFile;
	}
	
	public int getiNodeIndex() {
		return iNodeIndex;
	}
	
	
}
