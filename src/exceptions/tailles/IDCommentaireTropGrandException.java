package exceptions.tailles;

@SuppressWarnings("serial")
public class IDCommentaireTropGrandException extends Exception {
	public IDCommentaireTropGrandException(String message) {
		super(message);
	}
	
	public IDCommentaireTropGrandException() {
		super();
	}
}
