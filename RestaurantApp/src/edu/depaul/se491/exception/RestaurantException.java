package edu.depaul.se491.exception;

public class RestaurantException extends Exception {
	/**
	 * generated version id
	 */
	private static final long serialVersionUID = -3425787178905636079L;
	
	String message = null;

	/**
	 * The typical constructor.
	 * @param message A message to be displayed to the screen.
	 */
	public RestaurantException(String message) {
		this.message = message;
	}

	/**
	 * For messages which are displayed to the user. Usually, this is a very general message for security
	 * reasons.
	 */
	@Override
	public String getMessage() {
		if (message == null)
			return "An error has occurred. Please see log for details.";
		return message;
	}

	
	/**
	 * Technical detail message, usually delegated to a subclass
	 * 
	 * @return
	 */
	public String getExtendedMessage() {
		return "No extended information.";
	}

}
