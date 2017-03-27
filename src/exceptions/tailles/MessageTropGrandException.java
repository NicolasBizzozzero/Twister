package exceptions.tailles;

@SuppressWarnings("serial")
public class MessageTropGrandException extends Exception {
	public MessageTropGrandException(String message) {
		super(message);
	}
	
	public MessageTropGrandException() {
		super();
	}
}
