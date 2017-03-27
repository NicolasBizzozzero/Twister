package exceptions.tailles;

@SuppressWarnings("serial")
public class NombreDemandesTropPetitException extends Exception {
	public NombreDemandesTropPetitException(String message) {
		super(message);
	}
	
	public NombreDemandesTropPetitException() {
		super();
	}
}
