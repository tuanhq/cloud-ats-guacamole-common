package com.fpt.su11.guacamole.io;


import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.protocol.GuacamoleInstruction;

/**
 * Provides abstract and raw character read access to a stream of Guacamole
 * instructions.
 *
 * @author Michael Jumper
 */
public interface GuacamoleReader {

    /**
     * Returns whether instruction data is available for reading. Note that
     * this does not guarantee an entire instruction is available. If a full
     * instruction is not available, this function can return true, and a call
     * to read() will still block.
     *
     * @return true if instruction data is available for reading, false
     *         otherwise.
     * @throws GuacamoleException If an error occurs while checking for
     *                            available data.
     */
    public boolean available() throws GuacamoleException;

    /**
     * Reads at least one complete Guacamole instruction, returning a buffer
     * containing one or more complete Guacamole instructions and no
     * incomplete Guacamole instructions. This function will block until at
     * least one complete instruction is available.
     *
     * @return A buffer containing at least one complete Guacamole instruction,
     *         or null if no more instructions are available for reading.
     * @throws GuacamoleException If an error occurs while reading from the
     *                            stream.
     */
    public char[] read() throws GuacamoleException;

    /**
     * Reads exactly one complete Guacamole instruction and returns the fully
     * parsed instruction.
     *
     * @return The next complete instruction from the stream, fully parsed, or
     *         null if no more instructions are available for reading.
     * @throws GuacamoleException If an error occurs while reading from the
     *                            stream, or if the instruction cannot be
     *                            parsed.
     */
    public GuacamoleInstruction readInstruction() throws GuacamoleException;

}

