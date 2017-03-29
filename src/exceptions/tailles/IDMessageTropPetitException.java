package exceptions.tailles;

@SuppressWarnings("serial")
public class IDMessageTropPetitException extends Exception {
	public IDMessageTropPetitException(String message) {
		super(message);
	}
	
	public IDMessageTropPetitException() {
		super();
	}
}
