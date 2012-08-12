import java.util.Comparator;


public class IntegerComparator2
implements Comparator<Integer>
{
	public int compare(Integer e1, Integer e2) { 
		return e2.compareTo(e1); 
	}
}
