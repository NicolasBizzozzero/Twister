package exceptions.tailles;

@SuppressWarnings("serial")
public class CommentaireTropGrandException extends Exception {
	public CommentaireTropGrandException(String message) {
		super(message);
	}
	
	public CommentaireTropGrandException() {
		super();
	}
}
