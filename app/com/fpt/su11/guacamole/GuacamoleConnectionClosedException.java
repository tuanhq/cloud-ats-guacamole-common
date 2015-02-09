package com.fpt.su11.guacamole;

import com.fpt.su11.guacamole.GuacamoleServerException;
import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * An exception which is thrown when an operation cannot be performed because
 * its corresponding connection is closed.
 *
 * @author Michael Jumper
 */
public class GuacamoleConnectionClosedException extends GuacamoleServerException {

    /**
     * Creates a new GuacamoleConnectionClosedException with the given message
     * and cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleConnectionClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleConnectionClosedException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleConnectionClosedException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleConnectionClosedException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleConnectionClosedException(Throwable cause) {
        super(cause);
    }

    @Override
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.SERVER_ERROR;
    }

}
