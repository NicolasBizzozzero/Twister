package exceptions.tailles;

@SuppressWarnings("serial")
public class PseudoTropPetitException extends Exception {
	public PseudoTropPetitException(String message) {
		super(message);
	}

	public PseudoTropPetitException() {
		super();
	}
}
