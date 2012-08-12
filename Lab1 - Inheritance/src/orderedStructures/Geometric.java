package orderedStructures;


public class Geometric extends Progression {

	private double commonFactor; 
	
	public Geometric(double firstValue, double commonFactor) { 
		super(firstValue); 
		this.commonFactor = commonFactor; 
	}
	
	@Override
	public double nextValue() {
		current = current * commonFactor; 
		return current;
	}

	@Override
	public String toString() {
		return "Geom("+super.firstValue()+", "+this.commonFactor+")";
	}

	@Override
	public double getTerm(int n) throws IndexOutOfBoundsException {
		return super.firstValue()*Math.pow(commonFactor, n-1);
	}
	
	

}
