package hr.tvz.bole.exceptions;

//Runtime excetption ili pratit..
public class UserExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UserExistsException (String message) {
		super(message);
	}
	
	public UserExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
