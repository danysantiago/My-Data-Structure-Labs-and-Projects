
public class PriorityQueueTester {

	/**
	 * 
	 * @author Pedro I. Rivera-Vega
	 *
	 */
		public static void main(String[] args) {
			SortedListPriorityQueue<Integer, KeyedMessage> prtQueue = 
				new SortedListPriorityQueue<Integer, KeyedMessage>(
						new IntegerComparator1(), new IntegerKeyValidator()); 
			
			testInsert(prtQueue, new KeyedMessage(20, "Sarah")); 
			testInsert(prtQueue, new KeyedMessage(33, "Miriam")); 
			testInsert(prtQueue, new KeyedMessage(13, "Madeline")); 
			testInsert(prtQueue, new KeyedMessage(4, "Yajaira")); 
			testInsert(prtQueue, new KeyedMessage(24, "Pepe")); 
			testInsert(prtQueue, new KeyedMessage(37, "Gloria")); 
			testInsert(prtQueue, new KeyedMessage(7, "Maira")); 
			testInsert(prtQueue, new KeyedMessage(108, "Yara")); 
			testInsert(prtQueue, new KeyedMessage(334, "Rosaa")); 
			testInsert(prtQueue, new KeyedMessage(40, "Pepita")); 
			testInsert(prtQueue, new KeyedMessage(23, "Tara")); 
			testInsert(prtQueue, new KeyedMessage(15, "Julia")); 
			testInsert(prtQueue, new KeyedMessage(33, "Ivelucha")); 
			testInsert(prtQueue, new KeyedMessage(224, "Jasmine"));
			testRemoveMin(prtQueue);
			testRemoveMin(prtQueue);
										
		}

		private static void testInsert(SortedListPriorityQueue<Integer, KeyedMessage> prtQueue, KeyedMessage p) 
		{
			prtQueue.insert(p.getNumber(), p);
			 
			System.out.println("Priority Queue after inserting <"+p+">: \n" + prtQueue); 
		}
		
		private static void testRemoveMin(SortedListPriorityQueue<Integer, KeyedMessage> prtQueue) 
		{
		System.out.println("Priority Queue after removeing min: <"+prtQueue.removeMin().getValue()+">: \n" + prtQueue); 
		}
		
	}


