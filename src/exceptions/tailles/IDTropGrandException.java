package exceptions.tailles;

@SuppressWarnings("serial")
public class IDTropGrandException extends Exception {
	public IDTropGrandException(String message) {
		super(message);
	}
	
	public IDTropGrandException() {
		super();
	}
}
