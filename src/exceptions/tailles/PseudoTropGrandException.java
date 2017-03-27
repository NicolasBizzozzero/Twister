package exceptions.tailles;

@SuppressWarnings("serial")
public class PseudoTropGrandException extends Exception {
	public PseudoTropGrandException(String message) {
		super(message);
	}
	
	public PseudoTropGrandException() {
		super();
	}
}
