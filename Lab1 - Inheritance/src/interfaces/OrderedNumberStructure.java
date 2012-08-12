package interfaces;


import exceptions.InvalidIndexException;


public interface OrderedNumberStructure {
	double getTerm(int n) throws InvalidIndexException; 
	void printAllTerms(int n); 
}
