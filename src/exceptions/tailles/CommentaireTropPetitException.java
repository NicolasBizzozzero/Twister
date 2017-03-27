package exceptions.tailles;

@SuppressWarnings("serial")
public class CommentaireTropPetitException extends Exception {
	public CommentaireTropPetitException(String message) {
		super(message);
	}
	
	public CommentaireTropPetitException() {
		super();
	}
}
