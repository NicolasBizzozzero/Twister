package exceptions.tailles;

@SuppressWarnings("serial")
public class MotDePasseTropPetitException extends Exception {
	public MotDePasseTropPetitException(String message) {
		super(message);
	}
	
	public MotDePasseTropPetitException() {
		super();
	}
}