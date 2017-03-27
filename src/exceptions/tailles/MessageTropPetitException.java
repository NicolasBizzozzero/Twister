package exceptions.tailles;

@SuppressWarnings("serial")
public class MessageTropPetitException extends Exception {
	public MessageTropPetitException(String message) {
		super(message);
	}
	
	public MessageTropPetitException() {
		super();
	}
}
