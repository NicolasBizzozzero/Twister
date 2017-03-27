package exceptions.tailles;

@SuppressWarnings("serial")
public class EmailTropPetitException extends Exception {
	public EmailTropPetitException(String message) {
		super(message);
	}
	
	public EmailTropPetitException() {
		super();
	}
}
