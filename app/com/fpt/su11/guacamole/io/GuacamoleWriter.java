package com.fpt.su11.guacamole.io;


import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.protocol.GuacamoleInstruction;

/**
 * Provides abstract and raw character write access to a stream of Guacamole
 * instructions.
 *
 * @author Michael Jumper
 */
public interface GuacamoleWriter {

    /**
     * Writes a portion of the given array of characters to the Guacamole
     * instruction stream. The portion must contain only complete Guacamole
     * instructions.
     *
     * @param chunk An array of characters containing Guacamole instructions.
     * @param off The start offset of the portion of the array to write.
     * @param len The length of the portion of the array to write.
     * @throws GuacamoleException If an error occurred while writing the
     *                            portion of the array specified.
     */
    public void write(char[] chunk, int off, int len) throws GuacamoleException;

    /**
     * Writes the entire given array of characters to the Guacamole instruction
     * stream. The array must consist only of complete Guacamole instructions.
     *
     * @param chunk An array of characters consisting only of complete
     *              Guacamole instructions.
     * @throws GuacamoleException If an error occurred while writing the
     *                            the specified array.
     */
    public void write(char[] chunk) throws GuacamoleException;

    /**
     * Writes the given fully parsed instruction to the Guacamole instruction
     * stream.
     *
     * @param instruction The Guacamole instruction to write.
     * @throws GuacamoleException If an error occurred while writing the
     *                            instruction.
     */
    public void writeInstruction(GuacamoleInstruction instruction) throws GuacamoleException;

}
