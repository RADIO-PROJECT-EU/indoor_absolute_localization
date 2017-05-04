package org.atlas.apps.localization.exceptions;

public class RoomMapException extends Exception{
	private static final long serialVersionUID = 1086815199444334207L;

	public RoomMapException () {}

    public RoomMapException (String message) {
        super (message);
    }

    public RoomMapException (Throwable cause) {
        super (cause);
    }

    public RoomMapException (String message, Throwable cause) {
        super (message, cause);
    }
	
}
