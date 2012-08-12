package classes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidParameterException;

import exceptions.ExistingDiskException;
import exceptions.InvalidBlockException;
import exceptions.InvalidBlockNumberException;
import exceptions.NonExistingDiskException;

/**
 * DiskUnit class as used in the P1
 * @author Dany
 *
 */
public class DiskUnit {

	//Default constants
	private final static int DEFAULT_CAPACITY = 1024;
	private final static int DEFAULT_BLOCK_SIZE = 256;

	private int capacity;
	private int blockSize;
	private String name;

	//File used to represent a DiskUnit, also known as RAF for short
	private RandomAccessFile disk;
	
	//Create disk with default constants
	public static void createDiskUnit(String name) 
			throws ExistingDiskException {
		createDiskUnit(name, DEFAULT_CAPACITY, DEFAULT_BLOCK_SIZE);
	}

	//Private constructor
	private DiskUnit(String name) {
		try {
			disk = new RandomAccessFile(name, "rw");
			this.name = name;
		}
		catch (IOException e) {
			System.err.println ("Unable to start the disk");
			System.exit(1);
		}
	}

	/**
	 * Creates a DiskUnit with the given parameters
	 * @param name
	 * @param capacity
	 * @param blockSize
	 * @throws ExistingDiskException another disk already exists with given name
	 * @throws InvalidParameterException one of the parameters is invalid (ie blockSize != powerOf2)
	 */
	public static void createDiskUnit(String name, int capacity, int blockSize) 
			throws ExistingDiskException, InvalidParameterException {
		File file = new File(name);
		if (file.exists())
			throw new ExistingDiskException("Disk name is already used: " + name);

		RandomAccessFile disk = null;
		if (capacity < 0 || blockSize < 0 || !Utils.powerOf2(capacity) || !Utils.powerOf2(blockSize))
			throw new InvalidParameterException("Invalid values: " + " capacity = " + capacity + " block size = " + blockSize);
		// disk parameters are valid... hence create the file to represent the
		// disk unit.
		try {
			disk = new RandomAccessFile(name, "rw");
		}
		catch (IOException e) {
			System.err.println ("Unable to start the disk");
			System.exit(1);
		}
		reserveDiskSpace(disk, capacity, blockSize);

		// after creation, just leave it in shutdown mode ­ just
		// close the corresponding file
		try {
			disk.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Reserves the disk space in the RAF
	 * @param disk RAF for the disk unit
	 * @param capacity
	 * @param blockSize
	 */
	private static void reserveDiskSpace(RandomAccessFile disk, int capacity, int blockSize) {
		try {
			disk.setLength(capacity);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// write disk parameters (number of blocks, bytes per block) in block 0 of disk space
		try {
			disk.seek(0);
			disk.writeInt(capacity);  
			disk.writeInt(blockSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Mounts DiskUnit
	 * @param name of disk unit
	 * @return an instance of this class representing such disk
	 * @throws NonExistingDiskException disk dose not exists
	 */
	public static DiskUnit mount(String name) 
			throws NonExistingDiskException {
		File file = new File(name);
		if (!file.exists())
			throw new NonExistingDiskException("No disk has name : " + name);
		DiskUnit dUnit = new DiskUnit(name);

		// get the capacity and the block size of the disk from the file
		// representing the disk
		try {
			dUnit.disk.seek(0);
			dUnit.capacity = dUnit.disk.readInt();
			dUnit.blockSize = dUnit.disk.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dUnit;         
	}

	/**
	 * Write
	 * @param blockNum block being written
	 * @param b block of memory with information
	 * @throws InvalidBlockNumberException block is outside of bounds
	 * @throws InvalidBlockException block is null or not the correct size
	 */
	public void write(int blockNum, VirtualDiskBlock b)
			throws InvalidBlockNumberException, InvalidBlockException {
		if(blockNum < 0 || blockNum >= capacity)
			throw new InvalidBlockNumberException("Write: Invalid Block Number: " + blockNum);
		if(b == null || b.getCapacity() != blockSize)
			throw new InvalidBlockException("Write: Invalid Block, either null or wrong size");
		try {
			disk.seek(blockNum*blockSize);
			for(int i = 0; i < blockSize; i++){
				disk.writeByte(b.getElement(i));
			}
		} catch (IOException e) {
			System.err.println("read: IO Error");
		}
	}

	/**
	 * Read
	 * @param blockNum block being read
	 * @param b block of memory being stored
	 * @throws InvalidBlockNumberException block is outside of bounds
	 * @throws InvalidBlockException block is null or not the correct size
	 */
	public void read(int blockNum, VirtualDiskBlock b)
			throws InvalidBlockNumberException, InvalidBlockException {
		if(blockNum < 0 || blockNum >= capacity)
			throw new InvalidBlockNumberException("Read: Invalid Block Number: " + blockNum);
		if(b == null || b.getCapacity() != blockSize)
			throw new InvalidBlockException("Read: Invalid Block, either null or wrong size");
		try {
			disk.seek(blockNum*blockSize);
			for(int i = 0; i < blockSize; i++){
				b.setElement(i, disk.readByte());
			}
		} catch (IOException e) {
			System.err.println("read: IO Error");
		}
	}
	
	//Low level format writes 0s in all the disk, except in it's meta data part
	public void lowLevelFormat() {
		try {
			disk.write(new byte[capacity*blockSize]);
		} catch (IOException e){
			System.err.println("lowLevelFormat: IO Error");
		}

	}
	
	//Closes the RAF
	public void shutdown() {
		try {
			disk.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Getters
	public int getCapacity() {
		return capacity;
	}

	public int getBlockSize() {
		return blockSize;
	}
	
	public String getName(){
		return name;
	}

}
