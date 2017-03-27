package exceptions.tailles;

@SuppressWarnings("serial")
public class NomTropGrandException extends Exception {
	public NomTropGrandException(String message) {
		super(message);
	}
	
	public NomTropGrandException() {
		super();
	}
}
