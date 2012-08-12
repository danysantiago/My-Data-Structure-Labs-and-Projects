package testers;

import arrayIndexList.ArrayIndexList;
import indexList.IndexList;

public class IndexListTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		listTester("Testing my array-based IndexList: ",
				new ArrayIndexList<Integer>());	
	}
		
	private static void listTester(String msg, IndexList<Integer>  list) { 
		System.out.println(msg);

		try { 
			list.add(1, 400); 
		} 
		catch (Exception e) { 
			System.out.println(e); 
		}
		showListAfterAdding(list, 0,  435); 
		showListAfterAdding(list, 1,  200); 
		list.add(0, 435); 
	    list.add(1, 200); 
		
		for (int i=20, j=1; i< 50; i+=5, j++) { 
			list.add(i); 
		}
		
		showList(list); 

		showListAfterAdding(list, 90, 100); 
		showListAfterAdding(list, 90, -1); 
		showListAfterDeleting(list, 100); 
		showListAfterDeleting(list, -1);
		showListAfterReplacing(list, 100, 0); 
		showListAfterReplacing(list, -1, 0); 
		
		showListAfterDeleting(list, 4); 
		showListAfterDeleting(list, 2); 
		showListAfterDeleting(list, 30); 
		showListAfterReplacing(list, 3,  400); 
		showListAfterReplacing(list, 0, 30); 
		showListAfterAdding(list, 3,  700); 

		while (!list.isEmpty()) { 
			showListAfterDeleting(list, list.size()-1); 
			showListAfterDeleting(list, 0); 
		}
		
		showListAfterAdding(list, 0,  700); 
		showListAfterAdding(list, 1,  800); 
		showListAfterAdding(list, 2,  900); 
		showListAfterAdding(list, 2,  1000); 
		showListAfterAdding(list, 1,  1001); 
		showListAfterAdding(list, 3,  1002); 
		showListAfterAdding(list, 3,  700); 
		showListAfterAdding(list, 1,  800); 
		showListAfterAdding(list, 2,  900); 
		showListAfterAdding(list, 2,  1000); 
		showListAfterAdding(list, 1,  1001); 
		showListAfterAdding(list, 3,  1002); 

		showListAfterDeleting(list, 2); 
		showListAfterDeleting(list, 2); 
		showListAfterDeleting(list, 2); 
		showListAfterDeleting(list, 2); 
		showListAfterDeleting(list, 2); 

	}
		
	private static void showElement(IndexList<Integer> list, int position) { 
		try { 
			System.out.println(" --Element in position " 
					+ position + " is: "+ list.get(position)); 
		}
		catch (Exception e) { 
			System.out.println(e); 
		}
	}
	
	private static void showList(IndexList<Integer> list) { 
		System.out.println("\n*** The "+ list.size()+ " elements in the list are: "); 
		int lpindex = list.size(); 
		for (int i=0; i< lpindex; i++)
		    showElement(list,i); 
		

		System.out.println("\n The capacity of the list is " + list.capacity() + "\n"); 
	}
	
	private static void showListAfterDeleting(IndexList<Integer> list, int pos) { 
		System.out.println("\n -- deleting element at position "+pos); 
		try { 
			Integer etr = list.remove(pos); 
			System.out.println(" -- value of deleted element was "+ etr);
			showList(list); 
		}
		catch (Exception e) { 
			System.out.println(e);
		}
	}
	
	private static void showListAfterAdding(IndexList<Integer> list, int pos, Integer element) { 
		System.out.println("\n -- adding value " + element+" at position "+pos); 
		try { 
			list.add(pos, element);  
			showList(list); 
		}
		catch (Exception e) { 
			System.out.println(e); 
		}
	}
	
	private static void showListAfterReplacing(IndexList<Integer> list, int pos, Integer element) { 
		System.out.println("\n -- replacing value at position "+pos+" by "+element); 
		try { 
			Integer etr = list.set(pos, element);  
			System.out.println(" -- value of replaced element was "+ etr);
			showList(list); 
		}
		catch (Exception e) { 
			System.out.println(e); 
		}
	}
	
}
