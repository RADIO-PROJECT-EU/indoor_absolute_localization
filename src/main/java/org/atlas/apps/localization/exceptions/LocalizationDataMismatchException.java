package org.atlas.apps.localization.exceptions;

public class LocalizationDataMismatchException extends Exception{
	private static final long serialVersionUID = 1086815199444334207L;

	public LocalizationDataMismatchException () {}

    public LocalizationDataMismatchException (String message) {
        super (message);
    }

    public LocalizationDataMismatchException (Throwable cause) {
        super (cause);
    }

    public LocalizationDataMismatchException (String message, Throwable cause) {
        super (message, cause);
    }
	
}
