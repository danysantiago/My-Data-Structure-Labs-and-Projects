package testers;

import indexList.IndexList;
import indexList.SLRIndexList;
import indexList.Student;
import indexList.StudentGPAComparator;

import java.util.Comparator;

//Tester for the sort method
public class LinkedListTester2 {

	public static void main(String[] args) {	
		listTester("Testing the singly linked list (SLList): ",
				new SLRIndexList<Student>());	
	}
		
	private static void listTester(String msg, IndexList<Student>  list) { 
		System.out.println(msg);
		
		list.add(0, new Student("Daniel", 37));
		list.add(0, new Student("Juan", 38)); 
		list.add(1, new Student("Maria", 40));
		list.add(2, new Student("Isaac", 37));
		list.add(2, new Student("Yair", 35));
		list.add(2, new Student("Pedro", 28));
		
		System.out.println("\nList before sort:");
		showList(list);
		
		Comparator cmp = new StudentGPAComparator();
		list.sort(cmp);
		
		System.out.println("\nList after sort:");
		showList(list);
	}
	
	private static void showList(IndexList<Student> list) { 
		System.out.println("\n*** The "+ list.size()+ " elements in the list are: "); 
		int lpindex = list.size(); 
		for (int i=0; i< lpindex; i++)
		    showElement(list,i); 
	}
	
	private static void showElement(IndexList<Student> list, int position) { 
		try { 
			System.out.println(" --Element in position " 
					+ position + " is: "+ list.get(position)); 
		}
		catch (Exception e) { 
			System.out.println(e); 
		}
	}

}
