package br.com.newbesources.pfu.core.exception;

/**
 * @author gabriel
 *
 * Oct 11, 2013
 */
public class InvalidTypeException extends RuntimeException {
	private static final long serialVersionUID = 7767541954021901426L;

	public InvalidTypeException() {
		super();
	}

	public InvalidTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTypeException(String message) {
		super(message);
	}

	public InvalidTypeException(Throwable cause) {
		super(cause);
	}
}
