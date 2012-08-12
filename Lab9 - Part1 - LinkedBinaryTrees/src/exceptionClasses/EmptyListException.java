/**
 * 
 */
package exceptionClasses;

/**
 * @author Pedro I. Rivera-Vega
 *
 */
public class EmptyListException extends RuntimeException {

	/**
	 * 
	 */
	public EmptyListException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmptyListException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmptyListException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public EmptyListException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
