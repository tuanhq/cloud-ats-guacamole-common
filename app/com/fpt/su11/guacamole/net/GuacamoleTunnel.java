package com.fpt.su11.guacamole.net;


import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.io.GuacamoleReader;
import com.fpt.su11.guacamole.io.GuacamoleWriter;

/**
 * Provides a unique identifier and synchronized access to the GuacamoleReader
 * and GuacamoleWriter associated with a GuacamoleSocket.
 *
 * @author Michael Jumper
 */
public class GuacamoleTunnel implements Serializable{

    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    /**
     * The UUID associated with this tunnel. Every tunnel must have a
     * corresponding UUID such that tunnel read/write requests can be
     * directed to the proper tunnel.
     */
    private UUID uuid;

    /**
     * The GuacamoleSocket that tunnel should use for communication on
     * behalf of the connecting user.
     */
    private GuacamoleSocket socket;

    /**
     * Lock acquired when a read operation is in progress.
     */
    private ReentrantLock readerLock;

    /**
     * Lock acquired when a write operation is in progress.
     */
    private ReentrantLock writerLock;

    /**
     * Creates a new GuacamoleTunnel which synchronizes access to the
     * Guacamole instruction stream associated with the given GuacamoleSocket.
     *
     * @param socket The GuacamoleSocket to provide synchronized access for.
     */
    public GuacamoleTunnel(GuacamoleSocket socket) {

        this.socket = socket;
        uuid = UUID.randomUUID();

        readerLock = new ReentrantLock();
        writerLock = new ReentrantLock();

    }

    /**
     * Acquires exclusive read access to the Guacamole instruction stream
     * and returns a GuacamoleReader for reading from that stream.
     *
     * @return A GuacamoleReader for reading from the Guacamole instruction
     *         stream.
     */
    public GuacamoleReader acquireReader() {
        readerLock.lock();
        return socket.getReader();
    }

    /**
     * Relinquishes exclusive read access to the Guacamole instruction
     * stream. This function should be called whenever a thread finishes using
     * a GuacamoleTunnel's GuacamoleReader.
     */
    public void releaseReader() {
        readerLock.unlock();
    }

    /**
     * Returns whether there are threads waiting for read access to the
     * Guacamole instruction stream.
     *
     * @return true if threads are waiting for read access the Guacamole
     *         instruction stream, false otherwise.
     */
    public boolean hasQueuedReaderThreads() {
        return readerLock.hasQueuedThreads();
    }

    /**
     * Acquires exclusive write access to the Guacamole instruction stream
     * and returns a GuacamoleWriter for writing to that stream.
     *
     * @return A GuacamoleWriter for writing to the Guacamole instruction
     *         stream.
     */
    public GuacamoleWriter acquireWriter() {
        writerLock.lock();
        return socket.getWriter();
    }

    /**
     * Relinquishes exclusive write access to the Guacamole instruction
     * stream. This function should be called whenever a thread finishes using
     * a GuacamoleTunnel's GuacamoleWriter.
     */
    public void releaseWriter() {
        writerLock.unlock();
    }

    /**
     * Returns whether there are threads waiting for write access to the
     * Guacamole instruction stream.
     *
     * @return true if threads are waiting for write access the Guacamole
     *         instruction stream, false otherwise.
     */
    public boolean hasQueuedWriterThreads() {
        return writerLock.hasQueuedThreads();
    }

    /**
     * Returns the unique identifier associated with this GuacamoleTunnel.
     *
     * @return The unique identifier associated with this GuacamoleTunnel.
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Returns the GuacamoleSocket used by this GuacamoleTunnel for reading
     * and writing.
     *
     * @return The GuacamoleSocket used by this GuacamoleTunnel.
     */
    public GuacamoleSocket getSocket() {
        return socket;
    }

    /**
     * Release all resources allocated to this GuacamoleTunnel.
     *
     * @throws GuacamoleException if an error occurs while releasing
     *                            resources.
     */
    public void close() throws GuacamoleException {
        socket.close();
    }

    /**
     * Returns whether this GuacamoleTunnel is open, or has been closed.
     *
     * @return true if this GuacamoleTunnel is open, false if it is closed.
     */
    public boolean isOpen() {
        return socket.isOpen();
    }

}

