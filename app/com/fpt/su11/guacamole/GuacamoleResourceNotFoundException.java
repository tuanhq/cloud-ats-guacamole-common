package com.fpt.su11.guacamole;

import com.fpt.su11.guacamole.GuacamoleClientException;
import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * A generic exception thrown when part of the Guacamole API fails to find
 * a requested resource, such as a configuration or tunnel.
 *
 * @author Michael Jumper
 */
public class GuacamoleResourceNotFoundException extends GuacamoleClientException {

    /**
     * Creates a new GuacamoleResourceNotFoundException with the given message
     * and cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleResourceNotFoundException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleResourceNotFoundException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.RESOURCE_NOT_FOUND;
    }

}

