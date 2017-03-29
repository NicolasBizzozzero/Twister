package exceptions.tailles;

@SuppressWarnings("serial")
public class IDMessageTropGrandException extends Exception {
	public IDMessageTropGrandException(String message) {
		super(message);
	}
	
	public IDMessageTropGrandException() {
		super();
	}
}
