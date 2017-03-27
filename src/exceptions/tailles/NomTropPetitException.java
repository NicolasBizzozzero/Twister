package exceptions.tailles;

@SuppressWarnings("serial")
public class NomTropPetitException extends Exception {
	public NomTropPetitException(String message) {
		super(message);
	}
	
	public NomTropPetitException() {
		super();
	}
}
