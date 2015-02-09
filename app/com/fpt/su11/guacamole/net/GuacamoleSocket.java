package com.fpt.su11.guacamole.net;

import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.io.GuacamoleReader;
import com.fpt.su11.guacamole.io.GuacamoleWriter;

public interface GuacamoleSocket {

  /**
   * Returns a GuacamoleReader which can be used to read from the
   * Guacamole instruction stream associated with the connection
   * represented by this GuacamoleSocket.
   *
   * @return A GuacamoleReader which can be used to read from the
   *         Guacamole instruction stream.
   */
  public GuacamoleReader getReader();

  /**
   * Returns a GuacamoleWriter which can be used to write to the
   * Guacamole instruction stream associated with the connection
   * represented by this GuacamoleSocket.
   *
   * @return A GuacamoleWriter which can be used to write to the
   *         Guacamole instruction stream.
   */
  public GuacamoleWriter getWriter();

  /**
   * Releases all resources in use by the connection represented by this
   * GuacamoleSocket.
   *
   * @throws GuacamoleException If an error occurs while releasing resources.
   */
  public void close() throws GuacamoleException;

  /**
   * Returns whether this GuacamoleSocket is open and can be used for reading
   * and writing.
   *
   * @return true if this GuacamoleSocket is open, false otherwise.
   */
  public boolean isOpen();

}
