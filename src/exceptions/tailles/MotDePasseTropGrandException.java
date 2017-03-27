package exceptions.tailles;

@SuppressWarnings("serial")
public class MotDePasseTropGrandException extends Exception {
	public MotDePasseTropGrandException(String message) {
		super(message);
	}
	
	public MotDePasseTropGrandException() {
		super();
	}
}