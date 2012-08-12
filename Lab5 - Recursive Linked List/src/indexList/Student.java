package indexList;

public class Student {
	private String name;
	private int GPA;
	
	public Student(String name, int GPA) {
		this.name = name;
		this.GPA = GPA;
	}

	public String getName() {
		return name;
	}

	public int getGPA() {
		return GPA;
	}

	@Override
	public String toString() {
		return name + ", " + GPA/10 + "." + GPA%10; //nice print of GPA with point =]
	}
	
	
	
	

}
