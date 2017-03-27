package exceptions.tailles;

@SuppressWarnings("serial")
public class NouveauMotDePasseTropPetitException extends Exception {
	public NouveauMotDePasseTropPetitException(String message) {
		super(message);
	}
	
	public NouveauMotDePasseTropPetitException() {
		super();
	}
}
