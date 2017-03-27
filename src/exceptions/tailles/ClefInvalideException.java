package exceptions.tailles;

@SuppressWarnings("serial")
public class ClefInvalideException extends Exception {
	public ClefInvalideException(String message) {
		super(message);
	}
	
	public ClefInvalideException() {
		super();
	}
}
