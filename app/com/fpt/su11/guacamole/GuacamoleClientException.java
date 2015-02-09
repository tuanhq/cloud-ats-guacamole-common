package com.fpt.su11.guacamole;


import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * A generic exception thrown when part of the Guacamole API encounters
 * an error in the client's request. Such an error, if correctable, usually
 * requires correction on the client side, not the server.
 *
 * @author Michael Jumper
 */
public class GuacamoleClientException extends GuacamoleException {

    /**
     * Creates a new GuacamoleException with the given message and cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleClientException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleClientException(Throwable cause) {
        super(cause);
    }

    @Override
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.CLIENT_BAD_REQUEST;
    }

}
