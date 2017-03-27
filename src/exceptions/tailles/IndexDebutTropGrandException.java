package exceptions.tailles;

@SuppressWarnings("serial")
public class IndexDebutTropGrandException extends Exception {
	public IndexDebutTropGrandException(String message) {
		super(message);
	}
	
	public IndexDebutTropGrandException() {
		super();
	}
}
