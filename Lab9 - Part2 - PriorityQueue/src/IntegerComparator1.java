import java.util.Comparator;


public class IntegerComparator1 
implements Comparator<Integer>
{
	public int compare(Integer e1, Integer e2) { 
		return e1.compareTo(e2); 
	}
}
