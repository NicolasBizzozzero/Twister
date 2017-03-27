package exceptions.tailles;

@SuppressWarnings("serial")
public class IDTropPetitException extends Exception {
	public IDTropPetitException(String message) {
		super(message);
	}
	
	public IDTropPetitException() {
		super();
	}
}
