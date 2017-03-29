package exceptions.tailles;

@SuppressWarnings("serial")
public class IDCommentaireTropPetitException extends Exception {
	public IDCommentaireTropPetitException(String message) {
		super(message);
	}
	
	public IDCommentaireTropPetitException() {
		super();
	}
}
