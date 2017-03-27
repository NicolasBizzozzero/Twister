package exceptions.tailles;

@SuppressWarnings("serial")
public class NouveauMotDePasseTropGrandException extends Exception {
	public NouveauMotDePasseTropGrandException(String message) {
		super(message);
	}
	
	public NouveauMotDePasseTropGrandException() {
		super();
	}
}
