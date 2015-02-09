package com.fpt.su11.guacamole;

import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * A generic exception thrown when parts of the Guacamole API encounter
 * errors.
 *
 * @author Michael Jumper
 */
public class GuacamoleException extends Exception {
    
    /**
     * Creates a new GuacamoleException with the given message and cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns the Guacamole status associated with this exception. This status
     * can then be easily translated into an HTTP error code or Guacamole
     * protocol error code.
     * 
     * @return The corresponding Guacamole status.
     */
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.SERVER_ERROR;
    }
    
}