package org.openmrs.api;

public class InvalidObligationConfigException extends APIException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 165011923010561837L;
	
	public InvalidObligationConfigException() {
	}
	
	public InvalidObligationConfigException(String message) {
		super(message);
	}
	
	public InvalidObligationConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidObligationConfigException(Throwable cause) {
		super(cause);
	}
	
}
