
public class IntegerKeyValidator implements KeyValidator<Integer>
{

	public boolean isValid(Integer key) {
		return key > 0;
	}

}
