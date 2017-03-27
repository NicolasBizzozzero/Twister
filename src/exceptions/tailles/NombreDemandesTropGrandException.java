package exceptions.tailles;

@SuppressWarnings("serial")
public class NombreDemandesTropGrandException extends Exception {
	public NombreDemandesTropGrandException(String message) {
		super(message);
	}
	
	public NombreDemandesTropGrandException() {
		super();
	}
}
