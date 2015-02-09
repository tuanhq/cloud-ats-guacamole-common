package com.fpt.su11.guacamole;

import com.fpt.su11.guacamole.GuacamoleUpstreamException;
import com.fpt.su11.guacamole.protocol.GuacamoleStatus;


/**
 * An exception which indicates than an upstream server (such as the remote
 * desktop) is taking too long to respond.
 * 
 * @author Michael Jumper
 */
public class GuacamoleUpstreamTimeoutException extends GuacamoleUpstreamException {

    /**
     * Creates a new GuacamoleUpstreamException with the given message and
     * cause.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     * @param cause The cause of this exception.
     */
    public GuacamoleUpstreamTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GuacamoleUpstreamException with the given message.
     *
     * @param message A human readable description of the exception that
     *                occurred.
     */
    public GuacamoleUpstreamTimeoutException(String message) {
        super(message);
    }

    /**
     * Creates a new GuacamoleUpstreamException with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public GuacamoleUpstreamTimeoutException(Throwable cause) {
        super(cause);
    }

    @Override
    public GuacamoleStatus getStatus() {
        return GuacamoleStatus.UPSTREAM_TIMEOUT;
    }

}
