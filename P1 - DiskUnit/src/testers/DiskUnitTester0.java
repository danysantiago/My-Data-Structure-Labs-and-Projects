package testers;

import java.security.InvalidParameterException;

import classes.DiskUnit;
import exceptions.ExistingDiskException;

public class DiskUnitTester0 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// DiskUnit.createDiskUnit("disk0");
		try {
			DiskUnit.createDiskUnit("disk1", 128, 16);
			DiskUnit.createDiskUnit("disk2", 128, 32);
			DiskUnit.createDiskUnit("disk3", 128, 64);
			DiskUnit.createDiskUnit("disk4", 128, 128);
			DiskUnit.createDiskUnit("disk5", 128, 256);
		} catch (InvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExistingDiskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
