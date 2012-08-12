package orderedStructures;


import interfaces.OrderedNumberStructure;

import java.security.InvalidParameterException;

import exceptions.InvalidIndexException;

public class Tuple implements interfaces.OrderedNumberStructure {

	private double[] term; 
	
	public Tuple(int length) throws InvalidParameterException { 
		if (length <= 0)
			throw new InvalidParameterException("Tuple constructor: Invalid tuple length = " + length); 
		term = new double[length]; 
	}
	
	public int length() { 
		return term.length; 
	}
	
	public void setTerm(int index, double value) throws InvalidIndexException {
		if (index <= 0 || index > term.length) 
			throw new InvalidIndexException("setTerm: Invalid index = " + index);
		term[index-1] = value; 
	}
	
	public double getTerm(int index) throws InvalidIndexException {
		if (index <= 0 || index > term.length) 
			throw new InvalidIndexException("getTerm: Invalid index = " + index); 
		return term[index-1];
	}
	

	public void printAllTerms(int n) throws InvalidParameterException {
		if (n <= 0 || n > term.length) 
			throw new InvalidParameterException("printAllTerms: Invalid argument value = " + n); 
		
		System.out.println("Index --- Term Value"); 
		for (int i=0; i<n; i++)
			System.out.println((i+1) +  "---" + term[i]); 
	
	}

}
