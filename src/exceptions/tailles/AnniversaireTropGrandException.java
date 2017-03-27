package exceptions.tailles;

@SuppressWarnings("serial")
public class AnniversaireTropGrandException extends Exception {
	public AnniversaireTropGrandException(String message) {
		super(message);
	}
	
	public AnniversaireTropGrandException() {
		super();
	}
}
