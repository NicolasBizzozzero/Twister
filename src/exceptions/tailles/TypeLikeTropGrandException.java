package exceptions.tailles;

@SuppressWarnings("serial")
public class TypeLikeTropGrandException extends Exception {
	public TypeLikeTropGrandException(String message) {
		super(message);
	}
	
	public TypeLikeTropGrandException() {
		super();
	}
}
