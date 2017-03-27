package exceptions.tailles;

@SuppressWarnings("serial")
public class IndexDebutTropPetitException extends Exception {
	public IndexDebutTropPetitException(String message) {
		super(message);
	}
	
	public IndexDebutTropPetitException() {
		super();
	}
}
