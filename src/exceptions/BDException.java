package exceptions;

import java.sql.SQLException;

public class BDException extends SQLException {
	public BDException(String message) {
		super(message);
	}
}
