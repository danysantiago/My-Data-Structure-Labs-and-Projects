package positionInterfaces;

public interface BTPosition<E> extends Position<E> {
	public void setElement(E nuevo); 
	public BTPosition<E> getLeft(); 
	public void setLeft(BTPosition<E> v); 
	public BTPosition<E> getRight(); 
	public void setRight(BTPosition<E> v); 
	public BTPosition<E> getParent(); 
	public void setParent(BTPosition<E> v); 
}
