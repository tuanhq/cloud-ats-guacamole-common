package com.fpt.su11.guacamole.net;


import com.fpt.su11.guacamole.io.GuacamoleReader;
import com.fpt.su11.guacamole.io.ReaderGuacamoleReader;
import com.fpt.su11.guacamole.io.WriterGuacamoleWriter;
import com.fpt.su11.guacamole.io.GuacamoleWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.GuacamoleServerException;
import com.fpt.su11.guacamole.GuacamoleUpstreamTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides abstract socket-like access to a Guacamole connection over a given
 * hostname and port.
 *
 * @author Michael Jumper
 */
public class InetGuacamoleSocket implements GuacamoleSocket {

    /**
     * Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(InetGuacamoleSocket.class);

    /**
     * The GuacamoleReader this socket should read from.
     */
    private GuacamoleReader reader;

    /**
     * The GuacamoleWriter this socket should write to.
     */
    private GuacamoleWriter writer;

    /**
     * The number of milliseconds to wait for data on the TCP socket before
     * timing out.
     */
    private static final int SOCKET_TIMEOUT = 15000;

    /**
     * The TCP socket that the GuacamoleReader and GuacamoleWriter exposed
     * by this class should affect.
     */
    private Socket sock;

    /**
     * Creates a new InetGuacamoleSocket which reads and writes instructions
     * to the Guacamole instruction stream of the Guacamole proxy server
     * running at the given hostname and port.
     *
     * @param hostname The hostname of the Guacamole proxy server to connect to.
     * @param port The port of the Guacamole proxy server to connect to.
     * @throws GuacamoleException If an error occurs while connecting to the
     *                            Guacamole proxy server.
     */
    public InetGuacamoleSocket(String hostname, int port) throws GuacamoleException {

        try {

            logger.debug("Connecting to guacd at {}:{}.", hostname, port);

            // Get address
            SocketAddress address = new InetSocketAddress(
                    InetAddress.getByName(hostname),
                    port
            );

            // Connect with timeout
            sock = new Socket();
            sock.connect(address, SOCKET_TIMEOUT);

            // Set read timeout
            sock.setSoTimeout(SOCKET_TIMEOUT);

            // On successful connect, retrieve I/O streams
            reader = new ReaderGuacamoleReader(new InputStreamReader(sock.getInputStream(),   "UTF-8"));
            writer = new WriterGuacamoleWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));

        }
        catch (SocketTimeoutException e) {
            throw new GuacamoleUpstreamTimeoutException("Connection timed out.", e);
        }
        catch (IOException e) {
            throw new GuacamoleServerException(e);
        }

    }

    @Override
    public void close() throws GuacamoleException {
        try {
            logger.debug("Closing socket to guacd.");
            sock.close();
        }
        catch (IOException e) {
            throw new GuacamoleServerException(e);
        }
    }

    @Override
    public GuacamoleReader getReader() {
        return reader;
    }

    @Override
    public GuacamoleWriter getWriter() {
        return writer;
    }

    @Override
    public boolean isOpen() {
        return !sock.isClosed();
    }

}
