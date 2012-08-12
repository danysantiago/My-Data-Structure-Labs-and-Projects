package orderedStructures;


public class Arithmetic extends Progression {
	private double commonDifference; 
	
	public Arithmetic(double firstValue, double commonDifference) { 
		super(firstValue); 
		this.commonDifference = commonDifference; 
	}
	
	@Override
	public double nextValue() {
		current = current + commonDifference; 
		return current;
	}
	
	@Override
	public String toString() {
		return "Arith("+super.firstValue()+", "+this.commonDifference+")";
	}

	@Override
	public double getTerm(int n) throws IndexOutOfBoundsException {
		return super.firstValue() + commonDifference*(n-1);
	}
	
	

}
