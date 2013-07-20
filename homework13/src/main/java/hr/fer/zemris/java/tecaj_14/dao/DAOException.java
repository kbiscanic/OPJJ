package hr.fer.zemris.java.tecaj_14.dao;

/**
 * Exception thrown when error occurs while accessing data.
 * 
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}
}