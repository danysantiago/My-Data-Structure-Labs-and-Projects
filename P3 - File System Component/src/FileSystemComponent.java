import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import classes.BlockEntry;
import classes.DirEntry;
import classes.DirFile;
import classes.DiskMetadata;
import classes.DiskUnit;
import classes.Inode;
import classes.VirtualDiskBlock;
import exceptions.ExistingDiskException;
import exceptions.InvalidBlockException;
import exceptions.InvalidBlockNumberException;
import exceptions.NonExistingDiskException;


/**
 * The main class of this program, it handles all things related to the DiskUnits.
 * The execution code of each command is implemented in a method and called by the Big Switch upon
 * entering the command.
 * @author Dany
 *
 */
public class FileSystemComponent {
	
	//A single instace of this class used, used in the Shell.
	private static FileSystemComponent FSC = new FileSystemComponent();
	
	//The directory where this program is running
	private final String executableDir = System.getProperty("user.dir");
	
	//Instance fields used by the class (self-explanatory)
	private DiskUnit mountedDisk;
	private boolean diskMounted;
	private ArrayList<BlockEntry> freeBlockLList;
	private ArrayList<Integer> freeInodesList;
	private Stack<DirEntry> dirStack;
	
	private String curDiskName;
	private String curDirName;
	private String curDirPath;
	private String[] inputArray;
	
	private FileSystemComponent(){}
	
	//Public getters used by the Shell
	public boolean isDiskMounted() {
		return diskMounted;
	}
	
	public String getCurDiskName() {
		return curDiskName;
	}

	public String getCurDirPath() {
		return curDirPath;
	}
	
	public static FileSystemComponent getFSC() {
		return FSC;
	}

	
	/**
	 * The big execute command with the BIG SWITCH.
	 * Commands that enter here are already syntax validated and thus this method only
	 * calls the correct method that corresponds to the command
	 * @param inputLine input arrays with command and operands
	 * @param commandIndex command index
	 */
	public void execute(String inputLine, int commandIndex) {
		//precondition: inputArray != null and is of correct size for executing command
		//because it has gone trough the InputValidator
		inputArray = inputLine.split("\\s+");
		
		//BIG SWITCH
		switch(commandIndex){
		case Commands.CM_CAT:
			cat();
			break;
		case Commands.CM_CD:
			cd();
			break;
		case Commands.CM_CP:
			cp();
			break;
		case Commands.CM_CREATEDISK:
			createDisk();
			break;
		case Commands.CM_DELETEDISK:
			deleteDisk();
			break;
		case Commands.CM_FORMATDISK:
			formatDisk();
			break;
		case Commands.CM_LOADFILE:
			loadFile();
			break;
		case Commands.CM_LS:
			ls();
			break;
		case Commands.CM_MKDIR:
			mkDir();
			break;
		case Commands.CM_MOUNT:
			mount();
			break;
		case Commands.CM_RM:
			rm();
			break;
		case Commands.CM_RMDIR:
			rmDir();
			break;
		case Commands.CM_SHOWDISKS:
			showDisks();
			break;
		case Commands.CM_UNMOUNT:
			unMount();
			break;
		case Commands.CM_HELP:
			printHelp();
			break;
		case Commands.CM_QUESTIONMARK:
			printHelp();
			break;
		default:
		}
		
	}

	/**
	 * CAT command, shows the content of a file
	 */
	private void cat() {
		//Check if a disk is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}

		String fileName = inputArray[1]; //Name of the file
		
		//Check if file exists
		DirFile curDir = dirStack.peek().getDirFile();
		int contentIndex = curDir.getContentIndex(fileName);
		if(contentIndex == -1){
			System.out.println("No such file");
			return;
		}
		
		//Check if file is actually  a file (it can be a directory)
		int fileINodePointer = curDir.getContentINodes().get(contentIndex);
		Inode fileINode = readInode(mountedDisk, fileINodePointer);
		if(fileINode.getTypeflag() != Inode.FILEFLAG){
			System.out.println("No such file");
			return;
		}
		
		//Read
		byte[] fileContent = readFile(fileINodePointer).array();
		
		//Print the content in the console with some fancy header and asterisks
		String sHead = "File: " + fileName + " - " + fileINode.getContentSize() + " bytes";
		System.out.println(sHead);
		for(int i = 0; i < sHead.length(); i++)
			System.out.print("*");
		System.out.print("\n");
		
		
		System.out.println(new String(fileContent));
		
		for(int i = 0; i < sHead.length(); i++)
			System.out.print("*");
		System.out.print("\n");
	}

	/**
	 * Cd command, changes directory
	 */
	private void cd() {
		//Check if a disc is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}

		String pathString = inputArray[1]; //Path to travel to
		
		if(pathString.equals("..")){ //If the path is to go backward
			if(dirStack.size() > 1){ //Check to see if we are not already in root
				dirStack.pop(); //Pop the current directory out of the stack
				//Set the fancy shell path name
				String[] dirNames = curDirPath.split("\\\\");
				curDirName = dirNames[dirNames.length-1];
				curDirPath = curDirPath.replace("\\"+curDirName, "");
			}
		} else { //If path is to go forward
			//Check if dir exists
			int contentIndex = dirStack.peek().getDirFile().getContentIndex(pathString);
			if(contentIndex == -1 ){
				System.out.println("No such directory");
				return;
			}
			//Check if it actually is a dir (it can be a file)
			int dirPointer = dirStack.peek().getDirFile().getContentINodes().get(contentIndex);
			Inode dirINode = readInode(mountedDisk, dirPointer);
			if(dirINode.getTypeflag() != Inode.DIRFLAG){
				System.out.println("No such directory");
				return;
			}
			
			//Push new entry
			dirStack.push(new DirEntry(readDirFile(dirPointer), dirPointer));
			
			//Set fancy shell path name
			curDirName = pathString;
			curDirPath = curDirPath + "\\" + curDirName;
		}

	}

	/**
	 * Cp command, copies one file to another file, if the other file didn't exists it is created, if it did then it is overwritten
	 */
	private void cp() {
		//Check if a disc is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}
		
		String oldfileName = inputArray[1];
		String newfileName = inputArray[2];
		boolean overwrittenFlag = false;
		
		//Get cur working dir
		DirFile curDir = dirStack.peek().getDirFile();
		int curDirPointer = dirStack.peek().getiNodeIndex();
		
		//If file dosen't exists
		int oldContentIndex = curDir.getContentIndex(oldfileName);
		if(oldContentIndex == -1){
			System.out.println("No such file");
			return;
		}

		//If file is something else than an a file (it can be a directory)
		int oldFileInodePointer = curDir.getContentINodes().get(oldContentIndex);
		Inode oldFileINode = readInode(mountedDisk, oldFileInodePointer);
		if(oldFileINode.getTypeflag() != Inode.FILEFLAG){
			System.out.println("No such file");
			return;
		}
		
		ArrayList<Integer> blockUsedByOldFile = getBlocksUsedByFile(oldFileINode.getBlockPointer());
		
		//Check to see if we have enough inode & blocks
		if(freeBlockLList.size() <  blockUsedByOldFile.size() + 1 || freeInodesList.size() < 1){
			System.out.println("Unable to execute command: Not enough free i-nodes or blocks");
			return;
		}
		
		//Look if the second file exists so we can decide to overwrite
		int newContentIndex = curDir.getContentIndex(newfileName);
		if(newContentIndex != -1){
			int fileToOverwriteInodePoint = curDir.getContentINodes().get(newContentIndex);
			Inode fileToOverwriteInode = readInode(mountedDisk, fileToOverwriteInodePoint);
			//Check if file being overwritten is a file (it can be a directory)
			if(fileToOverwriteInode.getTypeflag() == Inode.FILEFLAG){
				//Overwriting is actually deleting and creating a new file
				removeFile(fileToOverwriteInodePoint);
				curDir.remove(newContentIndex);
				overwrittenFlag = true;
			} else {
				System.out.println("Unable to execute command: Saveing name is already used by a directory");
				return;
			}	
		}
		
		//Create inode
		Inode newfileINode = new Inode(Inode.FILEFLAG, (int) oldFileINode.getContentSize(), freeBlockLList.get(0).getIndex());
		int newFileINodePointer = freeInodesList.remove(0);
		writeInode(mountedDisk, newFileINodePointer, newfileINode);
		
		//Copy blocks
		int blocksNeeded = blockUsedByOldFile.size();
		int[] blockToWrite = new int[blocksNeeded];

		for(int i = 0; i < blockToWrite.length; i++)
			blockToWrite[i] = freeBlockLList.remove(0).getIndex();

		for(int i = 0; i < blockToWrite.length; i++){
			try {
				VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
				mountedDisk.read(blockUsedByOldFile.get(i), vdb);
				
				if(i < blockToWrite.length - 1)
					setNextBlockPointer(vdb, blockToWrite[i+1]);
				else
					setNextBlockPointer(vdb, 0);

				mountedDisk.write(blockToWrite[i], vdb);
			} catch (InvalidBlockNumberException e) {
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				e.printStackTrace();
			}
		}
		setFirstFreeBlock(freeBlockLList.get(0).getIndex());
		
		//Update cur dir
		curDir.addFile(newfileName, newFileINodePointer);
		updateDir(curDirPointer, curDir);
		
		if(overwrittenFlag)
			System.out.println("'"+newfileName+"' was overwritten");
	}

	/**
	 * createDisk command, creates a new unformatted DiskUnit
	 */
	private void createDisk() {
		String diskName = inputArray[1].concat(".disk");
		File file = new File(diskName);
		
		if (file.exists()){
			System.out.println("Disk with such name already exists: " + diskName.replace(".disk", ""));
			return;
		}
		
		try {
			FileOutputStream fout = new FileOutputStream(file);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Disk " + diskName.replace(".disk", "") + " sucessfully created");
		return;
	}

	/**
	 * deleteDisk command, deletes a DiskUnit
	 */
	private void deleteDisk() {
		String diskName = inputArray[1].concat(".disk");
		
		//Check that disk being delete is not the one mounted
		if(diskName.equals(curDiskName)){
			System.out.println("Cannot delete currently mounted disk.\nPlease first execute: 'unmount'.");
			return;
		}
		
		File file = new File(diskName);
		
		if (!file.exists()){
			System.out.println("Disk with such name does not exists: " + diskName.replace(".disk", ""));
			return;
		}
		
		//Delete disk
		if(!file.delete()){
			System.out.println("Unable to delete file being used by another process.");
			return;
		}
		
		System.out.println("Disk sucessfully deleted.");
	}

	/**
	 * formatDisk command, formats an exists DiskUnit with the desired block size and amount of blocks
	 */
	private void formatDisk() {
		String diskName = inputArray[1].concat(".disk");
		int amountOfBlocks = Integer.parseInt(inputArray[2]);
		int blockSize = Integer.parseInt(inputArray[3]);
		
		if(amountOfBlocks < 16){
			System.out.println("Unable to format disk. Amount of blocks cannot be less than 16");
			return;
		}
		
		if(blockSize < 16){
			System.out.println("Unable to format disk. Block size cannot be less than 16");
			return;
		}

		//Do some math for the disk parameters
		int capacity = amountOfBlocks*blockSize; //total disk size
		int amoutOfInodes = (int) (capacity*0.04)/9; //amount of i-nodes according to 4% of disk size
		int amountOfInodesPerBlock = blockSize/9; //amount of nodes per block
		int amountOfInodesBlocks = (int) Math.ceil(1.0*amoutOfInodes/amountOfInodesPerBlock); //amount of blocks for i-nodes
		int amountOfDataBlocks = amountOfBlocks - amountOfInodesBlocks - 1;

		//Check that disk being formated is unmounted
		if(diskName.equals(curDiskName)){
			System.out.println("Cannot format currently mounted disk.\nPlease first execute: 'unmount'.");
			return;
		}
		
		File file = new File(diskName);
		
		if (!file.exists()){
			System.out.println("Disk with such name does not exists: " + diskName.replace(".disk", ""));
			return;
		}
		
		//Create DiskUnit
		DiskUnit disk = null;
		try {
			if(!file.delete()){
				System.out.println("Cannot format disk.\nDisk being used by another process.");
				return;
			}
			DiskUnit.createDiskUnit(diskName, capacity, blockSize);
			disk = DiskUnit.mount(diskName);
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (ExistingDiskException e) {
			e.printStackTrace();
		} catch (NonExistingDiskException e) {
			e.printStackTrace();
		}
		
		//Write first block of meta-data
		try {
			//Get 1st block
			VirtualDiskBlock vdb = new VirtualDiskBlock(blockSize);
			disk.read(0, vdb);
			
			//Write amount of i nodes
			ByteBuffer bbuffer = ByteBuffer.allocate(4);
			bbuffer.putInt(amoutOfInodes);
			bbuffer.position(0);
			for(int i = 8; i < 12; i++)
				vdb.setElement(i, bbuffer.get());
			
			//Write first free block pointer
			//(The following block after the root folder)
			bbuffer.position(0);
			bbuffer.putInt(amountOfInodesBlocks +1 + 1);
			bbuffer.position(0);
			for(int i = 12; i < 16; i++)
				vdb.setElement(i, bbuffer.get());
			
			disk.write(0, vdb);			
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
		
		//Write empty i-nodes
		Inode inode = Inode.createEmptyINode();
		for(int i = 1; i <= amoutOfInodes; i++){
			writeInode(disk, i, inode);
		}
		
		//Write empty blocks linked list
		//(Skips 1st data block used for root folder)
		try {
			for(int i = 1 + 1; i < amountOfDataBlocks; i++){
				VirtualDiskBlock vdb = new VirtualDiskBlock(disk.getBlockSize());
				setNextBlockPointer(vdb, amountOfInodesBlocks + i + 1);
				disk.write(amountOfInodesBlocks + i, vdb);
			}
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
		
		//Write root i-node
		writeInode(disk, 1, new Inode(Inode.DIRFLAG, 0, amountOfInodesBlocks + 1));
		
		disk.shutdown();
		
		System.out.println("Formatting sucessful.");
	}

	/**
	 * loadFile command, loads and external file in the directory the program is running into the current working DiskUnit directory,
	 * if another internal file already existed it gets overwritten
	 */
	private void loadFile() {
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}
		
		String fileName = inputArray[1];
		String externalFile = inputArray[2];
		boolean overwrittenFlag = false;
		
		File file = new File(externalFile);
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			System.out.println("Unable to execute command: External file was not found");
			return;
		}
		
		int aproxBlockNeeded = (int) (file.length()/(mountedDisk.getBlockSize()-4));
		
		//Check if there are enough i-nodes/blocks
		if(freeBlockLList.size() < aproxBlockNeeded || freeInodesList.size() < 1){
			System.out.println("Unable to execute command: Not enough free i-nodes or blocks");
			return;
		}
		
		DirFile curDir = dirStack.peek().getDirFile();
		int curDirPointer = dirStack.peek().getiNodeIndex();
		
		//If file already exists
		int contentIndex = curDir.getContentIndex(fileName);
		if(contentIndex != -1){
			Inode inode = readInode(mountedDisk, curDir.getContentINodes().get(contentIndex));
			if(inode.getTypeflag() == Inode.FILEFLAG){
				rm();
				overwrittenFlag = true;
			} else {
				System.out.println("Unable to execute command: Saveing name is already used by a directory");
				return;
			}
		}
		
		//Create i node
		Inode fileINode = new Inode(Inode.FILEFLAG, (int) file.length(), freeBlockLList.get(0).getIndex());
		int fileINodePointer = freeInodesList.remove(0);
		writeInode(mountedDisk, fileINodePointer, fileINode);
		
		//Generate an array of chunks with the file's content
		ArrayList<VirtualDiskBlock> fileChunks = new ArrayList<VirtualDiskBlock>();
		try {
			while(fin.available() > 0){
				VirtualDiskBlock chunk = new VirtualDiskBlock(mountedDisk.getBlockSize());
				for(int i=0; i < chunk.getCapacity() - 4; i++){
					if(fin.available() > 0)
						chunk.setElement(i, (byte) fin.read());
					else
						break;
				}
				fileChunks.add(chunk);
			}
		} catch (IOException e) {
			System.err.println("Unable to read external file.");
			System.exit(0);
		}

		int blocksNeeded = fileChunks.size();
		int[] blockToWrite = new int[blocksNeeded];
		int blocksToWriteIndex = 0;
		while(blocksToWriteIndex < blockToWrite.length)
			blockToWrite[blocksToWriteIndex++] = freeBlockLList.remove(0).getIndex();

		for(int i = 0; i < blockToWrite.length; i++){
			VirtualDiskBlock vdb = fileChunks.get(i);
			if(i < blockToWrite.length - 1)
				setNextBlockPointer(vdb, blockToWrite[i+1]);
			try {
				mountedDisk.write(blockToWrite[i], vdb);
			} catch (InvalidBlockNumberException e) {
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				e.printStackTrace();
			}
		}
		setFirstFreeBlock(freeBlockLList.get(0).getIndex());
		
		curDir.addFile(fileName, fileINodePointer);
		updateDir(curDirPointer, curDir);
		
		System.out.print("Loading of file sucessful");
		if(overwrittenFlag)
			System.out.print(", previous file was overwritten");
		System.out.print("\n");
		
	}

	/**
	 * ls command, list the content of the current directory
	 */
	private void ls() {
		//Check if a disk is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}
		
		//Get cur directory and print it's list
		DirFile curDir = dirStack.peek().getDirFile();
		for(int i = 0; i < curDir.getContentAmount(); i++){
			String contentName = curDir.getContentNames().get(i);
			if(readInode(mountedDisk, curDir.getContentINodes().get(i)).getTypeflag() == Inode.DIRFLAG)
				System.out.printf("%8s","[DIR]  ");
			else
				System.out.printf("%8s","");
			System.out.println(contentName);
		}

	}

	/**
	 * mkDir command, makes a new directory in the current working directory
	 */
	private void mkDir() {
		//Check if a disk is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}
		
		String dirName = inputArray[1];
		
		DirFile curDir = dirStack.peek().getDirFile();
		
		//If another file dosen't have same name
		int contentIndex = curDir.getContentIndex(dirName);
		if(contentIndex != -1){
			System.out.println("Unable to execute command: Saveing name is already used");
			return;
		}
		
		//Check if there are enough i-nodes/blocks
		if(freeBlockLList.size() < 2 || freeInodesList.size() < 1){
			System.out.println("Unable to execute command: Not enough free i-nodes or blocks");
			return;
		}
		
		//Mark 1 block as used for the new dir and write it's i-node
		
		BlockEntry blockForNewDir = freeBlockLList.remove(0);
		int iNodeForNewDir = freeInodesList.remove(0);
		try {
			mountedDisk.write(blockForNewDir.getIndex(), new VirtualDiskBlock(mountedDisk.getBlockSize()));
			setFirstFreeBlock(blockForNewDir.getNextIndex());
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
		Inode newDirINode = new Inode(Inode.DIRFLAG, 0, blockForNewDir.getIndex());
		writeInode(mountedDisk, iNodeForNewDir, newDirINode);
		
		curDir.addFile(dirName, iNodeForNewDir);
		updateDir(dirStack.peek().getiNodeIndex(), curDir);
		
		
	}

	/**
	 * mount command, mounts a formatted DiskUnit as the current working DiskUnit,
	 */
	private void mount() {
		String diskName = inputArray[1].concat(".disk");
		//Mount only if no disk mounted or disk being mounted is not the same
		if(!diskName.equals(curDiskName) || !diskMounted){ 
			try {
				//Check if disk is formatted
				DiskMetadata diskMetadata = getDiskMetaData(diskName);
				if(diskMetadata.getCapacity() == -1){
					System.out.println("Unable to mount disk: Disk is unformatted");
					return;
				}
				mountedDisk = DiskUnit.mount(diskName);
				freeInodesList = generateFreeINodesList();
				freeBlockLList = generateFreeBlocksLList();
				curDiskName = mountedDisk.getName().replace(".disk", "");
				curDirName = "";
				curDirPath = "";
				dirStack = new Stack<DirEntry>();
				dirStack.push(new DirEntry(readDirFile(1), 1));
				diskMounted = true;
			} catch (NonExistingDiskException e) {
				System.out.println("Disk with such name does not exists: " + diskName.replace(".disk", ""));
				return;
			} 
		}
		
	}

	/**
	 * rm command, remove a file from the current working directory
	 */
	private void rm() {
		//Check if a disk is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}

		String fileName = inputArray[1];
		
		//Get current directory
		DirFile curDir = dirStack.peek().getDirFile();
		int curDirPointer = dirStack.peek().getiNodeIndex();
		
		//If file already dosen't exists
		int contentIndex = curDir.getContentIndex(fileName);
		if(contentIndex == -1){
			System.out.println("No such file");
			return;
		}
		
		//If file is something else than an a file (it can be a directory)
		int fileInodePointer = curDir.getContentINodes().get(contentIndex);
		Inode fileINode = readInode(mountedDisk, fileInodePointer);
		if(fileINode.getTypeflag() != Inode.FILEFLAG){
			System.out.println("No such file");
			return;
		}
		
		
		//Get file to delete iNode index
		int fileInodeIndex = curDir.getContentINodes().get(contentIndex);
		
		//The actual method to remove the file
		removeFile(fileInodeIndex);
		
		//Remove file from curDir file
		curDir.remove(contentIndex);
		
		//Update curDir
		updateDir(curDirPointer, curDir);
		
	}
	
	/**
	 * rmDir command, remove a directory and it's sub content from the current working directory
	 */
	private void rmDir() {
		//Check if a disk is mounted
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.\nPlease execute mount [disk_name]");
			return;
		}
		
		String dirName = inputArray[1];
		DirFile curDir = dirStack.peek().getDirFile();
		int curDirPointer = dirStack.peek().getiNodeIndex();

		//If file already dosen't exists
		int contentIndex = curDir.getContentIndex(dirName);
		if(contentIndex == -1){
			System.out.println("No such directory");
			return;
		}
		
		//Check if it's actually a dir (it can be a file)
		int dirPointer = curDir.getContentINodes().get(contentIndex);
		Inode dirINode = readInode(mountedDisk, dirPointer);
		if(dirINode.getTypeflag() != Inode.DIRFLAG){
			System.out.println("No such directory");
			return;
		}
		
		
		int dirInodeIndex = curDir.getContentINodes().get(contentIndex);
		
		//Calls the recursive remove to delete folder and sub content
		recRemove(dirInodeIndex);
		
		//Remove dir from curDir file
		curDir.remove(contentIndex);

		//Update curDir
		updateDir(curDirPointer, curDir);
	}

	/**
	 * showDisks command, list the programs DiskUnits and some basic information
	 */
	private void showDisks() {
		File exeDir = new File(executableDir); //Get the program execution directory
		//Create a filter to get only files with the disk extension
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(".disk");
			}
		};
		
		//Get the list of files from the directory with the given filter
		String[] list = exeDir.list(filter);
		if(list.length > 0){ //Lists disks if there are 1 or more
			
			//Print section:
			ArrayList<Integer> nameLengthList = new ArrayList<Integer>();
			ArrayList<String> capacityList = new ArrayList<String>();
			ArrayList<String> nBlocksList = new ArrayList<String>();
			ArrayList<String> blockSizeList = new ArrayList<String>();
			
			//Get the information
			for(String s : list){
				nameLengthList.add((s.replace(".disk", "")).length());
				DiskMetadata info = getDiskMetaData(s);
				capacityList.add(""+info.getCapacity());
				nBlocksList.add(""+info.getAmountOfBlocks());
				blockSizeList.add(""+info.getBlockSize());
			}
			
			//Add Header Length for formatting
			nameLengthList.add(8);
			capacityList.add("Capacity");
			nBlocksList.add("BlocksAmount");
			blockSizeList.add("BlocksSize");
			
			//Formatters for the table
			String[] formater = new String[4];
			Comparator<String> stringLengthComparator = new Comparator<String>(){
				@Override
				public int compare(String s1, String s2) {
					return s1.length() - s2.length();
				}
			};
			formater[0] = "%" + (Collections.max(nameLengthList)+1) + "s";
			formater[1] = "%" + (Collections.max(capacityList, stringLengthComparator).length()+1) + "s";
			formater[2] = "%" + (Collections.max(nBlocksList, stringLengthComparator).length()+1) + "s";
			formater[3] = "%" + (Collections.max(blockSizeList, stringLengthComparator).length()+1) + "s";
			
			//Print table
			int i = 0;
			System.out.printf(formater[0]+formater[1]+formater[2]+formater[3]+"\n","DiskName", "Capacity", "BlocksAmount", "BlocksSize"); 
			for(String s : list){
				if(!capacityList.get(i).equals("-1")){
					System.out.printf(formater[0]+formater[1]+formater[2]+formater[3],s.replace(".disk", ""), capacityList.get(i), nBlocksList.get(i), blockSizeList.get(i));
				} else {
					System.out.printf(formater[0]+"\tUnformatted Disk",s.replace(".disk", ""));
				}
				System.out.print("\n");
				i++;
			}
		} else {
			System.out.println("No disks found in the system.");
		}
		
	}

	/**
	 * unmount command, unmounts the current DiskUnit, it also writes marked free block with 0s
	 */
	public void unMount() {
		if(!diskMounted){
			System.out.println("Cannot execute command: No disk has been mounted.");
			return;
		}
		
		//Before shutdown wipe marked free blocks with 0s
		for(int i = 0; i < freeBlockLList.size(); i++){
			VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
			setNextBlockPointer(vdb, freeBlockLList.get(i).getNextIndex());
			try {
				mountedDisk.write(freeBlockLList.get(i).getIndex(), vdb);
			} catch (InvalidBlockNumberException e) {
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				e.printStackTrace();
			}
		}
		
		mountedDisk.shutdown();
		mountedDisk = null;
		freeInodesList = null;
		freeBlockLList = null;
		curDiskName = null;
		curDirName = null;
		curDirPath = null;
		dirStack = null;
		diskMounted = false;

	}

	/**
	 * Get disk meta data reads the meta data of a disk unit and stores it in an object
	 * @param diskName name of the disk
	 * @return the meta data object
	 */
	private DiskMetadata getDiskMetaData(String diskName) {
		//precondition: The disk exists
		DiskUnit disk = null;
		
		//If the length of the file is 0, then it's unformatted and a meta data object with and
		//indicator is returned
		File file = new File(diskName);
		if(file.length() == 0)
			return new DiskMetadata(-1, -1, -1, -1);
	
		//Mount DiskUunit
		try {
			disk = DiskUnit.mount(diskName);
		} catch (NonExistingDiskException e1) {
			e1.printStackTrace();
		}
	
		//Read meta data block (block 0)
		VirtualDiskBlock vdb = new VirtualDiskBlock(disk.getBlockSize());
		try {
			disk.read(0, vdb);
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
		
		//Transfer virtual disk block into byte buffer
		ByteBuffer bbuffer = ByteBuffer.allocate(16);
		for(int i = 0; i < 16; i++)
			bbuffer.put(vdb.getElement(i));
		//Parse byte buffer into the meta data object
		bbuffer.position(0);
		int capacity = bbuffer.getInt();
		int blockSize = bbuffer.getInt();
		int amountOfInodes = bbuffer.getInt();
		int firstFreeBlockPoint = bbuffer.getInt();
		DiskMetadata metaData = new DiskMetadata(capacity, blockSize, amountOfInodes, firstFreeBlockPoint);
		
		//Shutdown disk ater being done
		disk.shutdown();
		
		return metaData;
	}

	/**
	 * Removes a file and it's i node from the disk, also marks the blocks used as free
	 * @param iNodeIndex i node index of the file ebing removed
	 */
	private void removeFile(int iNodeIndex){
		Inode fileInode = readInode(mountedDisk, iNodeIndex);
		
		//Get blocks used by file
		ArrayList<Integer> blocksUsed = getBlocksUsedByFile(fileInode.getBlockPointer());
		
		//Write last free to block that the next free block is the file's first block
		VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
		try {
			int lastFreeBlock = freeBlockLList.get(freeBlockLList.size()-1).getIndex();
			mountedDisk.read(lastFreeBlock, vdb);
			setNextBlockPointer(vdb, blocksUsed.get(0));
			mountedDisk.write(lastFreeBlock, vdb);
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
		
		//Add the used blocks to the freeBlockList
		freeBlockLList.get(freeBlockLList.size()-1).setNextIndex(blocksUsed.get(0));
		int i = 0;
		while(i < blocksUsed.size() - 1)
			freeBlockLList.add(new BlockEntry(blocksUsed.get(i++), blocksUsed.get(i)));
		freeBlockLList.add(new BlockEntry(blocksUsed.get(i), 0));
		
		//Delete node by writting an empty node in it's location
		writeInode(mountedDisk, iNodeIndex, Inode.createEmptyINode());
	}

	/**
	 * Recursive remove used by in the removing of a directory's sub content
	 * @param iNodeIndex i node index of the file being removed
	 */
	private void recRemove(int iNodeIndex) {
		//Read iNode
		Inode fileInode = readInode(mountedDisk, iNodeIndex);
		
		//If file being removed is a directory first remove it's subcontent
		if(fileInode.getTypeflag() == Inode.DIRFLAG){
			DirFile dirFile = readDirFile(iNodeIndex);
			for(int contentIndex : dirFile.getContentINodes())
				recRemove(contentIndex);
		} 
		
		//The actual remove of the dir/file
		removeFile(iNodeIndex);
	}

	/**
	 * Update directory is a method that expands or shrinks a directory based on it's previous and it's new content
	 * @param nodeIndex index of i node corresponding to directory
	 * @param dirFile new dir file
	 */
	private void updateDir(int nodeIndex, DirFile dirFile) {
		try {
			//Read Inode
			Inode iNode = readInode(mountedDisk, nodeIndex);
			//write updated i-node
			writeInode(mountedDisk,nodeIndex, new Inode(Inode.DIRFLAG, dirFile.size(), iNode.getBlockPointer()));
	
			//Get blocks used by file
			ArrayList<Integer> blocksUsed = getBlocksUsedByFile(iNode.getBlockPointer());
			
			//Blocks needed by new file
			//int blocksNeeded = iNode.getContentSize()/(disk.getBlockSize()-4);
			
			//Two cases:
			//1. New dir needs more blocks
			//2. New dir needs less blocks
			ArrayList<VirtualDiskBlock> fileChunks =  splitFile(dirFile.toBBuffer().array(), mountedDisk.getBlockSize());
			int blocksNeeded = fileChunks.size();
			int[] blockToWrite = new int[blocksNeeded];
			if(blocksNeeded >= blocksUsed.size()){
				//Get the blocks to be write from those already used and from the free blocks available
				int blocksToWriteIndex = 0;
				for(int i = 0; i < blocksUsed.size(); i++)
					blockToWrite[blocksToWriteIndex++] = blocksUsed.get(i);
				while(blocksToWriteIndex < blockToWrite.length)
					blockToWrite[blocksToWriteIndex++] = freeBlockLList.remove(0).getIndex();
				
				//Sets the new first free block
				setFirstFreeBlock(freeBlockLList.get(0).getIndex());
			} else {
				//Get the blocks to write from those already used
				for(int i = 0; i < blockToWrite.length; i++)
					blockToWrite[i] = blocksUsed.remove(0);
				
				//Write last free to block that the next free block is the file's first block
				VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
				
				//Write the last free block to point to the first no longer used block of the file.
				int lastFreeBlock = freeBlockLList.get(freeBlockLList.size()-1).getIndex();
				mountedDisk.read(lastFreeBlock, vdb);
				setNextBlockPointer(vdb, blocksUsed.get(0));
				mountedDisk.write(lastFreeBlock, vdb);
	
				//Add the previously used but not free blocks to the free block list
				freeBlockLList.get(freeBlockLList.size()-1).setNextIndex(blocksUsed.get(0));
				int i = 0;
				while(i < blocksUsed.size() - 1)
					freeBlockLList.add(new BlockEntry(blocksUsed.get(i++), blocksUsed.get(i)));
				freeBlockLList.add(new BlockEntry(blocksUsed.get(i), 0));
			}
			
			//Write the file
			for(int i = 0; i < blockToWrite.length; i++){
				VirtualDiskBlock vdb = fileChunks.get(i);
				if(i < blockToWrite.length - 1)
					setNextBlockPointer(vdb, blockToWrite[i+1]);
				mountedDisk.write(blockToWrite[i], vdb);
			}
			
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates an array list of virtual disk blocks with the content of a byte array
	 * @param fileByteArray byte array to split
	 * @param blockSize block size to to make chunks
	 * @return array of virtual disk blocks
	 */
	private ArrayList<VirtualDiskBlock> splitFile(byte[] fileByteArray, int blockSize) {
		ArrayList<VirtualDiskBlock> fileChunks = new ArrayList<VirtualDiskBlock>(); //ArrayList of chunks
		
		//If given file is empty then return the chunks array with a single empty chunk
		if(fileByteArray.length == 0){
			fileChunks.add(new VirtualDiskBlock(blockSize));
			return fileChunks;
		}
		
		int fileSize = fileByteArray.length; //the file size is the actual size of the hex Array
		int curbyte = 0; //index of the current byte being worked on
		while (curbyte != fileSize){
			VirtualDiskBlock chunk = new VirtualDiskBlock(blockSize); //create chunk of length of a block size
			int i = 0;
			//Write byte to chunk while there are bytes to write and while chunk hasen't been fill
			while(i < blockSize - 4 && curbyte < fileSize){
				chunk.setElement(i++, fileByteArray[curbyte++]);
			}
			//add create chunk to the ArrayList
			fileChunks.add(chunk);
		}
		return fileChunks;
	}

	/**
	 * Creates an array list with the indexes of the blocks used by a file
	 * @param firstBlock the first block used by such file
	 * @return the array list of integers (which are the indexes)
	 */
	private ArrayList<Integer> getBlocksUsedByFile(int firstBlock) {
		ArrayList<Integer> blocksUsed = new ArrayList<Integer>();
		blocksUsed.add(firstBlock); //Add first block
		
		//Keep reading file and adding blocks used
		while(blocksUsed.get(blocksUsed.size()-1) != 0){
			VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
			try {
				mountedDisk.read(blocksUsed.get(blocksUsed.size()-1), vdb);
			} catch (InvalidBlockNumberException e) {
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				e.printStackTrace();
			}
			blocksUsed.add(readNextBlockPointer(vdb));
		}
		blocksUsed.remove(blocksUsed.size()-1);
		
		return blocksUsed;
	}

	/**
	 * Sets the current DiskUnit first free block
	 * @param pointer index of block being set
	 */
	private void setFirstFreeBlock(int pointer) {
		VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
		//Read first block, set integer and then write it back
		try {
			mountedDisk.read(0, vdb);
			ByteBuffer bbuffer = ByteBuffer.allocate(4);
			bbuffer.putInt(pointer);
			bbuffer.position(0);
			for(int i = 12; i < 16; i++)
				vdb.setElement(i, bbuffer.get());
			mountedDisk.write(0, vdb);
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the next block in a virtual disk block
	 * (It's located at the last 4 bytes of the block)
	 * @param vdb virtual disk block to read from
	 * @return the index of the next block
	 */
	private int readNextBlockPointer(VirtualDiskBlock vdb) {
		ByteBuffer bbuffer = ByteBuffer.allocate(4);
		for(int i = vdb.getCapacity() - 4; i < vdb.getCapacity(); i++)
			bbuffer.put(vdb.getElement(i));
		bbuffer.position(0);
		return bbuffer.getInt();
	}

	/**
	 * Sets the next block index in a virtual disk block
	 * (It's written in the last 4 bytes of the block)
	 * @param vdb virtual disk block to set the value
	 * @param blockPoint index of the next block to set
	 */
	private void setNextBlockPointer(VirtualDiskBlock vdb, int blockPoint) {
		ByteBuffer bbuffer = ByteBuffer.allocate(4);
		bbuffer.putInt(blockPoint);
		bbuffer.position(0);
		for(int i = vdb.getCapacity() - 4; i < vdb.getCapacity(); i++)
			vdb.setElement(i, bbuffer.get());
		
	}

	/**
	 * Reads and parses and i node in the given disk at the given index
	 * @param disk to read i node from
	 * @param index of i node
	 * @return Inode object
	 */
	private Inode readInode(DiskUnit disk, int index) {
		try {
			
			//Calculate location
			int amountOfInodesPerBlock = disk.getBlockSize()/9;
			int blockPoint = (int) Math.ceil(1.0*index/amountOfInodesPerBlock);
			int iNodePointAtBlock;
			if(index > amountOfInodesPerBlock)
				iNodePointAtBlock = (index-1)%amountOfInodesPerBlock;
			else
				iNodePointAtBlock = index - 1;
			
			//Read and parse
			ByteBuffer iNodeBBuffer = ByteBuffer.allocate(9);
			VirtualDiskBlock vdb = new VirtualDiskBlock(disk.getBlockSize());
			disk.read(blockPoint, vdb);
			for(int i = 0; i < 9; i++){
				iNodeBBuffer.put(vdb.getElement(iNodePointAtBlock*9 + i));
			}
			
			return new Inode(iNodeBBuffer);
			
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Write i node at the given disk in the given index
	 * @param disk to write i node
	 * @param index of i node
	 * @param inode to write
	 */
	private void writeInode(DiskUnit disk, int index, Inode inode) {
		try {
			//Calculate location
			int amountOfInodesPerBlock = disk.getBlockSize()/9;
			int blockPoint = (int) Math.ceil(1.0*index/amountOfInodesPerBlock);
			int iNodePointAtBlock;
			if(index > amountOfInodesPerBlock)
				iNodePointAtBlock = (index-1)%amountOfInodesPerBlock;
			else
				iNodePointAtBlock = index - 1;
			
			//Write
			ByteBuffer iNodeBBuffer = inode.toBBuffer();
			VirtualDiskBlock vdb = new VirtualDiskBlock(disk.getBlockSize());
			disk.read(blockPoint, vdb);
			for(int i = 0; i < 9; i++){
				vdb.setElement(iNodePointAtBlock*9 + i, iNodeBBuffer.get());
			}
			disk.write(blockPoint, vdb);
			
			
		} catch (InvalidBlockNumberException e) {
			e.printStackTrace();
		} catch (InvalidBlockException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Reads a dir file, 
	 * Tt's just calling the read file method and parsing the content into a dir file
	 * @param index of dir file i node
	 * @return DirFile object
	 */
	private DirFile readDirFile(int index) {
		return new DirFile(readFile(index));
	}

	/**
	 * Reads a file from the DiskUnit
	 * @param iNodeIndex file's i node index
	 * @return byte buffer containing the file in byte form
	 */
	private ByteBuffer readFile(int iNodeIndex){
		Inode dirNode = readInode(mountedDisk, iNodeIndex);
		int fileSize = dirNode.getContentSize();
		ByteBuffer bbuffer = ByteBuffer.allocate(fileSize);
		
		int curBlock = dirNode.getBlockPointer();
		int curByte = 0;
		do {
			VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
			try {
				mountedDisk.read(curBlock, vdb);
			} catch (InvalidBlockNumberException e) {
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				e.printStackTrace();
			}
			
			int i = 0;
			while(curByte < fileSize && i < vdb.getCapacity() - 4){
				bbuffer.put(vdb.getElement(i++));
				curByte++;
			}
			
			curBlock = readNextBlockPointer(vdb);
			
		} while(curBlock != 0);
		
		return bbuffer;
	}

	/**
	 * Generate a kinds linked list of the free blocks in the mounted disk
	 * @return the list with block entries of the free blocks
	 */
	private ArrayList<BlockEntry> generateFreeBlocksLList() {
		ArrayList<BlockEntry> list = new ArrayList<BlockEntry>();
		int curBlock = getDiskMetaData(mountedDisk.getName()).getFirstFreeBlockPoint();
		int nextBlock;
		if(curBlock == -1)
			return list;
		
		do{
			VirtualDiskBlock vdb = new VirtualDiskBlock(mountedDisk.getBlockSize());
			try {
				mountedDisk.read(curBlock, vdb);
			} catch (InvalidBlockNumberException e) {
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				e.printStackTrace();
			}
			nextBlock = readNextBlockPointer(vdb);
			list.add(new BlockEntry(curBlock, nextBlock));
			curBlock = nextBlock;
		} while(nextBlock != 0);
		return list;
	}

	/**
	 * Generate a list of the indexes of the free i node in the mounted disk
	 * @return list with indexes
	 */
	private ArrayList<Integer> generateFreeINodesList() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int amoutOfInodes = getDiskMetaData(mountedDisk.getName()).getAmountOfInodes();
		for(int i = 1; i <= amoutOfInodes; i++){
			Inode inode = readInode(mountedDisk, i);
			if(inode.getTypeflag() == Inode.EMPTYFLAG)
				list.add(i);
		}
		return list;
	}

	/**
	 * Method to print the help string
	 */
	private static void printHelp() {
		System.out.println("***Available commands:***");
		for(int i = 0; i < Commands.commandList.length - 1; i++){
			System.out.print(Commands.commandList[i] + " " + Commands.commandOperandsList[i][0]);
			System.out.println(" - "+Commands.commandOperandsList[i][1]);
		}
	}

}
