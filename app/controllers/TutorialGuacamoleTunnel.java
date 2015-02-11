package controllers;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import play.mvc.Controller;
import play.mvc.Result;

import com.fpt.su11.guacamole.GuacamoleClientException;
import com.fpt.su11.guacamole.GuacamoleConnectionClosedException;
import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.GuacamoleResourceNotFoundException;
import com.fpt.su11.guacamole.GuacamoleServerException;
import com.fpt.su11.guacamole.io.GuacamoleReader;
import com.fpt.su11.guacamole.io.GuacamoleWriter;
import com.fpt.su11.guacamole.net.GuacamoleSocket;
import com.fpt.su11.guacamole.net.GuacamoleTunnel;
import com.fpt.su11.guacamole.net.InetGuacamoleSocket;
import com.fpt.su11.guacamole.protocol.ConfiguredGuacamoleSocket;
import com.fpt.su11.guacamole.protocol.GuacamoleConfiguration;
import com.fpt.su11.guacamole.protocol.GuacamoleStatus;
import com.fpt.su11.guacamole.servlet.GuacamoleSession;

public class TutorialGuacamoleTunnel  extends Controller {
  
   
  
  /**
   * Logger for this class.
   */
  private static Logger logger = LoggerFactory.getLogger(TutorialGuacamoleTunnel.class);

  /**
   * The prefix of the query string which denotes a tunnel read operation.
   */
  private static final String READ_PREFIX  = "read:";

  /**
   * The prefix of the query string which denotes a tunnel write operation.
   */
  private static final String WRITE_PREFIX = "write:";

  /**
   * The length of the read prefix, in characters.
   */
  private static final int READ_PREFIX_LENGTH = READ_PREFIX.length();

  /**
   * The length of the write prefix, in characters.
   */
  private static final int WRITE_PREFIX_LENGTH = WRITE_PREFIX.length();

  /**
   * The length of every tunnel UUID, in characters.
   */
  private static final int UUID_LENGTH = 36;


  /**
   * Sends an error on the given HTTP response using the information within
   * the given GuacamoleStatus.
   *
   * @param response The HTTP response to use to send the error.
   * @param guac_status The status to send
   * @param message A human-readable message that can be presented to the
   *                user.
   * @throws ServletException If an error prevents sending of the error
   *                          code.
   */
  public static void sendError(GuacamoleStatus guac_status, String message)
          {
    
     /* try {

//          // If response not committed, send error code and message
          if (!response.isCommitted()) {
              response.addHeader("Guacamole-Status-Code", Integer.toString(guac_status.getGuacamoleStatusCode()));
              response.addHeader("Guacamole-Error-Message", message);
              response.sendError(guac_status.getHttpStatusCode());
          }

      }
      catch (IOException ioe) {

          // If unable to send error at all due to I/O problems,
          // rethrow as servlet exception
          throw new ServletException(ioe);

      }*/

  }

  /**
   * Dispatches every HTTP GET and POST request to the appropriate handler
   * function based on the query string.
   *
   * @param request The HttpServletRequest associated with the GET or POST
   *                request received.
   * @param response The HttpServletResponse associated with the GET or POST
   *                 request received.
   * @throws ServletException If an error occurs while servicing the request.
   */
//  protected void handleTunnelRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    public static Result handleTunnelRequest() throws Exception {
      
    
      try {

          Map<String, String[]> querys = request().queryString();
          
//          if (querys == null)
//              throw new GuacamoleClientException("No query string provided.");

          // If connect operation, call doConnect() and return tunnel UUID
          // in response.         
         
          
          if (request().uri().startsWith(request().path() + "?" + "connect")) {          
              GuacamoleTunnel tunnel = doConnect();
              if (tunnel != null) {

                  // Get session                    
                  String uuidKey=session("uuid");
                  System.out.println("137 Tutorial handle request get uuid from session:" + uuidKey);
                  if(uuidKey==null) {
                    uuidKey=java.util.UUID.randomUUID().toString();
                    System.out.println("140 Tutorial handle generate uuid whennull" + uuidKey);
                    session("uuid", uuidKey);
                  }
                  GuacamoleSession session = new GuacamoleSession(uuidKey);

                  // Attach tunnel to session
                  session.attachTunnel(tunnel, uuidKey);

                  try {
                      // Ensure buggy browsers do not cache response
                      response().setHeader("Cache-Control", "no-cache");

                      // Send UUID to client
                      return ok(tunnel.getUUID().toString());
                  }
                  catch (Exception e) {
                      throw new GuacamoleServerException(e);
                  }

              }

              // Failed to connect
              else
                  throw new GuacamoleResourceNotFoundException("No tunnel created.");

          }

          // If read operation, call doRead() with tunnel UUID, ignoring any
          // characters following the tunnel UUID.
          else if(request().uri().startsWith(request().path() + "?" + READ_PREFIX)) {
              System.out.println("start do read socket");
              String uuidKey=session("uuid");
              String query = request().uri().substring(request().path().length() + 1);
              System.out.println("query is :" + query);
              System.out.println("substring query is :" + query.substring(
                      READ_PREFIX_LENGTH,
                      READ_PREFIX_LENGTH + UUID_LENGTH));
              doRead(uuidKey, query.substring(
                      READ_PREFIX_LENGTH,
                      READ_PREFIX_LENGTH + UUID_LENGTH));
              return ok("finish");
          }
          // If write operation, call doWrite() with tunnel UUID, ignoring any
          // characters following the tunnel UUID.
          else if(request().uri().startsWith(request().path() + "?" + WRITE_PREFIX)) {
             System.out.println("start do write socket");
              String uuidKey=session("uuid");
              String query = request().uri().substring(request().path().length() + 1);
              System.out.println("query is write :" + query);
              System.out.println("substring query write is :" + query.substring(
                  WRITE_PREFIX_LENGTH,
                  WRITE_PREFIX_LENGTH + UUID_LENGTH));
              doWrite(uuidKey, query.substring(
                      WRITE_PREFIX_LENGTH,
                      WRITE_PREFIX_LENGTH + UUID_LENGTH));
              return ok("finish");

          // Otherwise, invalid operation
          }else{
              throw new GuacamoleClientException("Invalid tunnel operation: " + request().uri());
              
          }
      }

      // Catch any thrown guacamole exception and attempt to pass within the
      // HTTP response, logging each error appropriately.
      catch (GuacamoleClientException e) {
          logger.warn("HTTP tunnel request rejected: {}", e.getMessage());
          return ok("finish");
       //   sendError(response, e.getStatus(), e.getMessage());
      }
      catch (GuacamoleException e) {
          logger.error("HTTP tunnel request failed: {}", e.getMessage());
          logger.debug("Internal error in HTTP tunnel.", e);
          return ok("finish");
        //  sendError(response, e.getStatus(), "Internal server error.");
      }
      
      
  }

  /**
   * Called whenever the JavaScript Guacamole client makes a connection
   * request. It it up to the implementor of this function to define what
   * conditions must be met for a tunnel to be configured and returned as a
   * result of this connection request (whether some sort of credentials must
   * be specified, for example).
   *
   * @param request The HttpServletRequest associated with the connection
   *                request received. Any parameters specified along with
   *                the connection request can be read from this object.
   * @return A newly constructed GuacamoleTunnel if successful,
   *         null otherwise.
   * @throws GuacamoleException If an error occurs while constructing the
   *                            GuacamoleTunnel, or if the conditions
   *                            required for connection are not met.
   */
 

  /**
   * Called whenever the JavaScript Guacamole client makes a read request.
   * This function should in general not be overridden, as it already
   * contains a proper implementation of the read operation.
   *
   * @param request The HttpServletRequest associated with the read request
   *                received.
   * @param response The HttpServletResponse associated with the write request
   *                 received. Any data to be sent to the client in response
   *                 to the write request should be written to the response
   *                 body of this HttpServletResponse.
   * @param tunnelUUID The UUID of the tunnel to read from, as specified in
   *                   the write request. This tunnel must be attached to
   *                   the Guacamole session.
   * @throws GuacamoleException If an error occurs while handling the read
   *                            request.
   */
//  protected void doRead(HttpServletRequest request, HttpServletResponse response, String tunnelUUID) throws GuacamoleException {
  protected static void doRead(String uuidKey, String tunnelUUID) throws GuacamoleException {
      
      GuacamoleSession session = new GuacamoleSession(uuidKey);

      // Get tunnel, ensure tunnel exists
      GuacamoleTunnel tunnel = session.getTunnel(tunnelUUID);
      if (tunnel == null){
          System.out.println("Tunnel is NULLLLLL");
          throw new GuacamoleResourceNotFoundException("No such tunnel.");
      }

      // Ensure tunnel is open
      if (!tunnel.isOpen())
          throw new GuacamoleResourceNotFoundException("Tunnel is closed.");

      // Obtain exclusive read access
      GuacamoleReader reader = tunnel.acquireReader();

      try {

          // Note that although we are sending text, Webkit browsers will
          // buffer 1024 bytes before starting a normal stream if we use
          // anything but application/octet-stream.
          response().setContentType("application/octet-stream");
          response().setHeader("Cache-Control", "no-cache");
          response().setHeader("Transfer-Encoding", "chunked");

          // Get writer for response
      //   Writer out = new BufferedWriter(new OutputStreamWriter(
//                  response.getOutputStream(), "UTF-8"));

          // Stream data to response, ensuring output stream is closed
          try {

              // Detach tunnel and throw error if EOF (and we haven't sent any
              // data yet.
              char[] message = reader.read();
              
              if (message == null)
                  throw new GuacamoleConnectionClosedException("Tunnel reached end of stream.");

              // For all messages, until another stream is ready (we send at least one message)
              int i=0;
              do {

                  // Get message output bytes
                i++;
                System.out.println("Message read from guac server :" + i + " ****" + new String(message));                  
                  ok(new String(message));

                  // Flush if we expect to wait
                  if (!reader.available()) {
//                      out.flush();
//                      response.flushBuffer();
                  }

                  // No more messages another stream can take over
                  if (tunnel.hasQueuedReaderThreads())
                      break;

              } while (tunnel.isOpen() && (message = reader.read()) != null);

              // Close tunnel immediately upon EOF
              if (message == null)
                  tunnel.close();

              // End-of-instructions marker
              ok("0.;".getBytes());                
          }

          // Send end-of-stream marker if connection is closed
          catch (GuacamoleConnectionClosedException e) {
            ok("0.;".getBytes());
          }

          catch (GuacamoleException e) {

              // Detach and close
              session.detachTunnel(tunnel, uuidKey);
              tunnel.close();

              throw e;
          }

          // Always close output stream
          finally {
//              out.close();
          }

      }
      catch (Exception e) {

          // Log typically frequent I/O error if desired
          logger.debug("Error writing to servlet output stream", e);

          // Detach and close
          session.detachTunnel(tunnel, uuidKey);
          tunnel.close();

      }
      finally {
          tunnel.releaseReader();
      }

  }

  /**
   * Called whenever the JavaScript Guacamole client makes a write request.
   * This function should in general not be overridden, as it already
   * contains a proper implementation of the write operation.
   *
   * @param request The HttpServletRequest associated with the write request
   *                received. Any data to be written will be specified within
   *                the body of this request.
   * @param response The HttpServletResponse associated with the write request
   *                 received.
   * @param tunnelUUID The UUID of the tunnel to write to, as specified in
   *                   the write request. This tunnel must be attached to
   *                   the Guacamole session.
   * @throws GuacamoleException If an error occurs while handling the write
   *                            request.
   */
  protected static void doWrite(String uuidKey, String tunnelUUID) throws GuacamoleException {
    
      
      System.out.println("session id :" + uuidKey);
      System.out.println("tunnelUuid :" + tunnelUUID);
      GuacamoleSession session = new GuacamoleSession(uuidKey);

      GuacamoleTunnel tunnel = session.getTunnel(tunnelUUID);
      if (tunnel == null)
          throw new GuacamoleResourceNotFoundException("No such tunnel.");

      // We still need to set the content type to avoid the default of
      // text/html, as such a content type would cause some browsers to
      // attempt to parse the result, even though the JavaScript client
      // does not explicitly request such parsing.
      response().setContentType("application/octet-stream");
      response().setHeader("Cache-Control", "no-cache");
    //  response().setContentLength(0);

      // Send data
      try {

          // Get writer from tunnel
          GuacamoleWriter writer = tunnel.acquireWriter();

          // Get input reader for HTTP stream
          
          System.out.println("request body as form url endcode");
          for (Entry<String, String[]> entry: request().body().asFormUrlEncoded().entrySet()){
            System.out.println("key :" + entry.getKey());
            System.out.println("value : " + entry.getValue());
          }
          
          Reader input = new InputStreamReader(new ByteArrayInputStream(request().body().asFormUrlEncoded().toString().getBytes()),"UTF-8");           
          
          // Transfer data from input stream to tunnel output, ensuring
          // input is always closed
          try {

              // Buffer
              int length;
              char[] buffer = new char[8192];

              // Transfer data using buffer
              while (tunnel.isOpen() &&
                      (length = input.read(buffer, 0, buffer.length)) != -1)
                  writer.write(buffer, 0, length);

          }

          // Close input stream in all cases
          finally {
              input.close();
          }

      }
      catch (GuacamoleConnectionClosedException e) {
          logger.debug("Connection to guacd closed.", e);
      }
      catch (IOException e) {

          // Detach and close
          session.detachTunnel(tunnel, uuidKey);
          tunnel.close();

          throw new GuacamoleServerException("I/O Error sending data to server: " + e.getMessage(), e);
      }
      finally {
          tunnel.releaseWriter();
      }

  }
  

    protected static GuacamoleTunnel doConnect() 
        throws GuacamoleException {
      try {
        // Create our configuration
        GuacamoleConfiguration config = new GuacamoleConfiguration();
        config.setProtocol("vnc");
        config.setParameter("hostname", "localhost");
        config.setParameter("port", "5900");
        config.setParameter("password", "123456");

        // Connect to guacd - everything is hard-coded here.
        
          System.out.println("start connect guac server");
          GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                  new InetGuacamoleSocket("localhost", 4822),
                  config
          );
          
          System.out.println("end connect guac server");
        
        

        // Establish the tunnel using the connected socket
        GuacamoleTunnel tunnel = new GuacamoleTunnel(socket);
        System.out.println("establish the tunnel have uuid:" + tunnel.getUUID());
        // Attach tunnel to session    
        
        String uuidKey=session("uuid");   
        System.out.println(" 458 doconnectg uuid get from session:" + uuidKey);
        if(uuidKey==null) {
          uuidKey=java.util.UUID.randomUUID().toString();
          System.out.println("461 uuid auto generate:" + uuidKey);
          session("uuid", uuidKey);
        }
        GuacamoleSession session = new GuacamoleSession(uuidKey);
        
        System.out.println("attach the tunnel have uuid:" + tunnel.getUUID() + "to session:" + uuidKey);
        session.attachTunnel(tunnel, uuidKey);

        // Return pre-attached tunnel
        return tunnel;
        } catch (Exception e) {
          e.printStackTrace();
        }
      return null;
      

    }

}