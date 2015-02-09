package com.fpt.su11.guacamole.io;

import java.io.IOException;
import java.io.Writer;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import com.fpt.su11.guacamole.GuacamoleConnectionClosedException;
import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.GuacamoleServerException;
import com.fpt.su11.guacamole.GuacamoleUpstreamTimeoutException;
import com.fpt.su11.guacamole.protocol.GuacamoleInstruction;

/**
 * A GuacamoleWriter which wraps a standard Java Writer, using that Writer as
 * the Guacamole instruction stream.
 *
 * @author Michael Jumper
 */
public class WriterGuacamoleWriter implements GuacamoleWriter {

    /**
     * Wrapped Writer to be used for all output.
     */
    private Writer output;

    /**
     * Creates a new WriterGuacamoleWriter which will use the given Writer as
     * the Guacamole instruction stream.
     *
     * @param output The Writer to use as the Guacamole instruction stream.
     */
    public WriterGuacamoleWriter(Writer output) {
        this.output = output;
    }

    @Override
    public void write(char[] chunk, int off, int len) throws GuacamoleException {
        try {
            output.write(chunk, off, len);
            output.flush();
        }
        catch (SocketTimeoutException e) {
            throw new GuacamoleUpstreamTimeoutException("Connection to guacd timed out.", e);
        }
        catch (SocketException e) {
            throw new GuacamoleConnectionClosedException("Connection to guacd is closed.", e);
        }
        catch (IOException e) {
            throw new GuacamoleServerException(e);
        }
    }

    @Override
    public void write(char[] chunk) throws GuacamoleException {
        write(chunk, 0, chunk.length);
    }

    @Override
    public void writeInstruction(GuacamoleInstruction instruction) throws GuacamoleException {
        write(instruction.toString().toCharArray());
    }

}

