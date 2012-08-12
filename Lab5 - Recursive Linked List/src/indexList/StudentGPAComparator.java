package indexList;

import java.util.Comparator;

public class StudentGPAComparator implements Comparator<Student>{
	public int compare(Student s1, Student s2){
		return s1.getGPA() - s2.getGPA();
	}
}
