package com.fpt.su11.guacamole;

import com.fpt.su11.guacamole.GuacamoleClientException;
import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * A security-related exception thrown when parts of the Guacamole API is
 * denying access to a resource.
 *
 * @author Michael Jumper
 */
public class GuacamoleSecurityException extends GuacamoleClientException {

    /**
     * Creates a new GuacamoleSecurityException with the given message and cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleSecurityException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleSecurityException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleSecurityException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleSecurityException(Throwable cause) {
        super(cause);
    }

    @Override
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.CLIENT_FORBIDDEN;
    }

}
