
public class KeyedMessage {
	private Integer number; 
	private String message; 
	
	public KeyedMessage(Integer number, String numeral) { 
		this.number = number; 
		this.message = numeral; 
	}

	public Integer getNumber() {
		return number;
	}

	public String getMessage() {
		return message;
	}	

	public String toString() { 
		return "Key = " + number + "  Value = " + message; 
	}
}
