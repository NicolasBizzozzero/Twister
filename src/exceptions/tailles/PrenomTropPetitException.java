package exceptions.tailles;

@SuppressWarnings("serial")
public class PrenomTropPetitException extends Exception {
	public PrenomTropPetitException(String message) {
		super(message);
	}
	
	public PrenomTropPetitException() {
		super();
	}
}
