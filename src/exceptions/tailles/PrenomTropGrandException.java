package exceptions.tailles;

@SuppressWarnings("serial")
public class PrenomTropGrandException extends Exception {
	public PrenomTropGrandException(String message) {
		super(message);
	}
	
	public PrenomTropGrandException() {
		super();
	}
}
