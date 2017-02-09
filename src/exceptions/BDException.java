package exceptions;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class BDException extends SQLException {
	public BDException(String message) {
		super(message);
	}
}
