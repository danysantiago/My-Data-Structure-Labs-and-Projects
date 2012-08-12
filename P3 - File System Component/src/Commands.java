
/**
 * Commands class used to store commands info
 * @author Dany
 *
 */
public class Commands {

	//Constants representing each command (Used in the Big Switch)
	public static final int CM_CAT = 0;
	public static final int CM_CD = 1;
	public static final int CM_CP = 2;
	public static final int CM_CREATEDISK = 3;
	public static final int CM_DELETEDISK = 4;
	public static final int CM_FORMATDISK = 5;
	public static final int CM_LOADFILE = 6;
	public static final int CM_LS = 7;
	public static final int CM_MKDIR = 8;
	public static final int CM_MOUNT = 9;
	public static final int CM_RM = 10;
	public static final int CM_RMDIR = 11;
	public static final int CM_SHOWDISKS = 12;
	public static final int CM_UNMOUNT = 13;
	public static final int CM_HELP = 14;
	public static final int CM_QUESTIONMARK = 15;
	
	//String list of the commands
	public static final String[] commandList = {
		"cat",
		"cd",
		"cp",
		"createdisk",
		"deletedisk",
		"formatdisk",
		"loadfile",
		"ls",
		"mkdir",
		"mount",
		"rm",
		"rmdir",
		"showdisks",
		"unmount",
		"help",
		"?"
	};

	//String array for the help command;
	public static final String[][] commandOperandsList = {
		{"[file_name]", "Displays the content of the given internal file."},
		{"[dir_name]", "Changes the current directory to the new directory."},
		{"[file_name_1] [file_name_2]", "Copies one internal file to another internal file."},
		{"[disk_name]", "Creates a new disk unit with the given name."},
		{"[disk_name]", "Deletes from the system the given disk, if it exists."},
		{"[disk_name] [nblocks] [bsize]", "Formats the particular disk, as a disk having nblocks blocks, each of size bsize (number of bytes in block)."},
		{"[file_name] [ext_file_name]", "Attempts to read a new file into the current directory in the current working disk unit."},
		{"", "List the names and sizes of all the files and directories that are part of the current directory."},
		{"[dir_name]", "Makes a new directory inside the current directory."},
		{"[disk_name]", "Makes the given disk as the working disk unit."},
		{"[file_name]", "Removes the particular file from the current directory if such data file exists in it."},
		{"[dir_name]", "Removes the given directory from the current directory."},
		{"", "Shows the list of disk units that are active in the system."},
		{"", "Dismounts the current working disk unit, if any."},
		{"", "Displays this menu."}
	};
}
