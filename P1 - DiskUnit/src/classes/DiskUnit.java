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
 * @author Dany
 *
 */
public class DiskUnit {

	private final static int DEFAULT_CAPACITY = 1024;
	private final static int DEFAULT_BLOCK_SIZE = 256;

	private int capacity;
	private int blockSize;

	private RandomAccessFile disk;

	public static void createDiskUnit(String name) 
			throws ExistingDiskException {
		createDiskUnit(name, DEFAULT_CAPACITY, DEFAULT_BLOCK_SIZE);
	}

	private DiskUnit(String name) {
		try {
			disk = new RandomAccessFile(name, "rw");
		}
		catch (IOException e) {
			System.err.println ("Unable to start the disk");
			System.exit(1);
		}
	}

	public static void createDiskUnit(String name, int capacity, int blockSize) 
			throws ExistingDiskException, InvalidParameterException {
		File file=new File(name);
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

	private static void reserveDiskSpace(RandomAccessFile disk, int capacity, int blockSize) {
		try {
			disk.setLength(blockSize * capacity);
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

	public void write(int blockNum, VirtualDiskBlock b)
			throws InvalidBlockNumberException, InvalidBlockException {
		if(blockNum < 0 || blockNum >= capacity)
			throw new InvalidBlockNumberException("Write: Invalid Block Number: " + blockNum);
		if(b == null || b.getCapacity() != blockSize)
			throw new InvalidBlockException("Write: Invalid Block, either null or wrong size");
		try {
			disk.seek(8 + blockNum*blockSize);
			for(int i = 0; i < blockSize; i++){
				disk.writeByte(b.getElement(i));
			}
		} catch (IOException e) {
			System.err.println("read: IO Error");
		}
	}

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
	
	public void lowLevelFormat() {
		try {
			disk.seek(8);
			disk.write(new byte[capacity*blockSize]);
		} catch (IOException e){
			System.err.println("lowLevelFormat: IO Error");
		}

	}

	public void shutdown() {
		try {
			disk.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public int getBlockSize() {
		return blockSize;
	}

}
