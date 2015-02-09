package com.fpt.su11.guacamole;

import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * A generic exception thrown when part of the Guacamole API encounters
 * an unexpected, internal error. An internal error, if correctable, would
 * require correction on the server side, not the client.
 *
 * @author Michael Jumper
 */
public class GuacamoleServerException extends GuacamoleException {

    /**
     * Creates a new GuacamoleServerException with the given message and cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleServerException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleServerException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleServerException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleServerException(Throwable cause) {
        super(cause);
    }

    @Override
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.SERVER_ERROR;
    }

}