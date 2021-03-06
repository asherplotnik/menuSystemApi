package app.core.exceptions;

public class MenuException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public MenuException() {
		super();
	}

	public MenuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MenuException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuException(String message) {
		super(message);
	}

	public MenuException(Throwable cause) {
		super(cause);
	}
	
	

}
