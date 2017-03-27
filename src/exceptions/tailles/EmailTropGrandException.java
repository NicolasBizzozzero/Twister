package exceptions.tailles;

@SuppressWarnings("serial")
public class EmailTropGrandException extends Exception {
	public EmailTropGrandException(String message) {
		super(message);
	}
	
	public EmailTropGrandException() {
		super();
	}
}
