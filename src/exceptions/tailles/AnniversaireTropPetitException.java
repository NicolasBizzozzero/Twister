package exceptions.tailles;

@SuppressWarnings("serial")
public class AnniversaireTropPetitException extends Exception {
	public AnniversaireTropPetitException(String message) {
		super(message);
	}
	
	public AnniversaireTropPetitException() {
		super();
	}
}
