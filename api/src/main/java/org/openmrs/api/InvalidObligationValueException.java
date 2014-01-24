package org.openmrs.api;

public class InvalidObligationValueException extends APIException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 165011923010561837L;
	
	public InvalidObligationValueException() {
	}
	
	public InvalidObligationValueException(String message) {
		super(message);
	}
	
	public InvalidObligationValueException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidObligationValueException(Throwable cause) {
		super(cause);
	}
	
}
