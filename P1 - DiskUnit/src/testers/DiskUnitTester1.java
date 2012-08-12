package testers;

import classes.DiskUnit;
import classes.VirtualDiskBlock;
import exceptions.InvalidBlockException;
import exceptions.InvalidBlockNumberException;
import exceptions.NonExistingDiskException;

public class DiskUnitTester1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s ="La revista especializada en viajes Travel & Leisure acaba de " + 
				" publicar que San Juan, Puerto Rico, es la ciudad que alberga gentes " +
				" más atractivas, según arrojó el sondeo America's Favorite Cities que "+
				" realizan cada año entre sus lectores este año.  " +
				" El año pasado (2010) la mayoría de los lectores viajeros de la " +
				" prestigiosa revista elevaron a Charleston (South Carolina) a la "+
				" primera posición. No obstante este año la capital de Puerto Rico "+
				" alcanzó el número uno en la categoría de People (o Gentes), renglón"+
				" importante que evalúa la personalidad de los locales, característica "+
				" que resulta en el bienestar, o no, del turista en determinado destino. "+
				" De San Juan dicen Son extraordinarios, visten fabulosamente bien, y"+ 
				" a ellos sencillamente uno no los puede detestar: los locales del"+
				" soleado Puerto Rico midieron muy alto en la categoría de amistad así"+
				" como de hermosura. Ellos también saben cómo halagar al visitante con"+
				" placeres sencillos. San Juan alcanzó casi la medida más alta como el"+
				" lugar donde se sirve el mejor café con leche, y ganó en la categoría"+
				" de comida callejera. En el desglose que aparece en la edición de"+
				" octubre de Travel & Leisure, firmado por Katrina Brown Hunt, señala"+
				" que a San Juan le siguen en orden descendente las ciudades de San"+
				" Diego, Miami, Los Angeles, Denver, Honolulu, Charleston, Savannah,"+
				" San Francisco y Salt Lake City.";

		DiskUnit d;
		try {
			d = DiskUnit.mount("disk3");
			splitAndWriteToDisk(s, d); 

			showDiskContent(d); 

			showFileInDiskContent(d); 
			d.shutdown(); 
		} catch (NonExistingDiskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // edit the name of the disk to mount


	}


	private static void splitAndWriteToDisk(String s, DiskUnit d) {
		// The following is the list of blocks from the virtual
		// disk that are assigned to the file. The first block
		// for the file is block 1. Each block has a "next block"
		// corresponding the number of the next disk block assigned
		// the the file. The file may require less blocks that the 
		// number of blocks in the following array. If more, then
		// an error explodes somewhere. But that's ok as for this
		// test. These is just a sequence of different block numbers
		// arbitrarily chosen.
		int[] fileBlocks = {1, 20, 3, 30, 23, 2, 28, 48, 51, 63, 
				37, 81, 92, 5, 71, 123, 50, 41, 19, 17, 29, 4, 7, 8, 
				9, 21, 22, 66, 33, 34, 35, 36, 57, 38, 39, 42, 13, 
				14, 15, 42, 45, 43, 122, 111, 110, 109, 101, 116, 
				6, 117, 121, 120, 124}; 
		int bsize = d.getBlockSize(); 
		VirtualDiskBlock vdb = new VirtualDiskBlock(bsize); 
		int chunkSize = bsize - 4; 

		boolean done = false; 
		int chn = 0, bn = 0; 
		byte[] barr = new byte[bsize]; 
		while (!done) { 
			int b = 0; 
			for (int i=0; i<chunkSize && chn < s.length(); i++) { 
				barr[i] = (byte) s.charAt(chn); 
				chn++; 
				b++; 
			}
			// fill with zeroes if needed
			for (int i=b; i<chunkSize; i++)
				barr[i] = (byte) 0; 

			// copy the content of barr to block number fileBlocks[bn-1]
			// in the virtual disk unit
			for (int i=0; i<chunkSize; i++)  
				vdb.setElement(i, barr[i]);  			

			copyNextBNToBlock(vdb, fileBlocks[bn+1]); 
			bn++;
			if ((chn >= s.length() || bn >= fileBlocks.length)) { 
				copyNextBNToBlock(vdb, 0); 
				done = true; 
			}

			try {
				d.write(fileBlocks[bn-1], vdb);
			} catch (InvalidBlockNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}

	}


	private static void showFileInDiskContent(DiskUnit d) { 
		VirtualDiskBlock vdb = new VirtualDiskBlock(d.getBlockSize()); 

		System.out.println("\nContent of the file begining at block 1"); 
		int bn = 1; 
		while (bn != 0) { 
			try {
				d.read(bn, vdb);

				showVirtualDiskBlock(bn, vdb);
				bn = getNextBNFromBlock(vdb);
			} catch (InvalidBlockNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	}


	private static void showDiskContent(DiskUnit d) { 

		System.out.println("Capacity of disk is: " + d.getCapacity()); 
		System.out.println("Size of blocks in the disk is: " + d.getBlockSize()); 

		VirtualDiskBlock block = new VirtualDiskBlock(d.getBlockSize()); 
		for (int b = 0; b < d.getCapacity(); b++) { 
			try {
				d.read(b, block);

				showVirtualDiskBlock(b, block); 
			} catch (InvalidBlockNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidBlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	}

	private static void showVirtualDiskBlock(int b, VirtualDiskBlock block) {
		System.out.print(" Block "+ b + "\t"); 
		for (int i=0; i<block.getCapacity(); i++) {
			char c = (char) block.getElement(i); 
			if (Character.isLetterOrDigit(c))
				System.out.print(c); 
			else
				System.out.print('-'); 
		}
		System.out.println(); 
	}


	public static void copyNextBNToBlock(VirtualDiskBlock vdb, int value) { 
		int lastPos = vdb.getCapacity()-1;

		for (int index = 0; index < 4; index++) { 
			vdb.setElement(lastPos - index, (byte) (value & 0x000000ff)); 	
			value = value >> 8; 
		}

	}

	private static int getNextBNFromBlock(VirtualDiskBlock vdb) { 
		int bsize = vdb.getCapacity(); 
		int value = 0; 
		int lSB; 
		for (int index = 3; index >= 0; index--) { 
			value = value << 8; 
			lSB = 0x000000ff & vdb.getElement(bsize-1-index);
			value = value | lSB; 
		}
		return value; 

	}

}
