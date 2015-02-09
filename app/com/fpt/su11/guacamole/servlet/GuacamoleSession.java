package com.fpt.su11.guacamole.servlet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.fpt.su11.guacamole.GuacamoleException;
import com.fpt.su11.guacamole.GuacamoleSecurityException;
import com.fpt.su11.guacamole.net.GuacamoleTunnel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides abstract access to the tunnels associated with a Guacamole session.
 *
 * @author Michael Jumper
 */
public class GuacamoleSession {

    /**
     * Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(GuacamoleSession.class);

    /**
     * Map of all currently attached tunnels, indexed by tunnel UUID.
     */
    private ConcurrentMap<String, GuacamoleTunnel> tunnels;

    /**
     * Creates a new GuacamoleSession, storing and retrieving tunnels from the
     * given HttpSession. Note that the true Guacamole session is tied to the
     * HttpSession provided, thus creating a new GuacamoleSession does not
     * create a new Guacamole session; it merely creates a new object for
     * accessing the tunnels of an existing Guacamole session represented by
     * the provided HttpSession.
     *
     * @param session The HttpSession to use as tunnel storage.
     * @throws GuacamoleException If session is null.
     */
    @SuppressWarnings("unchecked")
    public GuacamoleSession(String key) throws GuacamoleException {        
      
        if (play.cache.Cache.get(key) == null)
            throw new GuacamoleSecurityException("User has no session.");    
        synchronized (key) {
            tunnels = (ConcurrentMap<String, GuacamoleTunnel>) play.cache.Cache.get("GUAC_TUNNELS" + key);
            if (tunnels == null) {
                tunnels = new ConcurrentHashMap<String, GuacamoleTunnel>();
                play.cache.Cache.set("GUAC_TUNNELS" + key, tunnels);
            }

        }

    }

    /**
     * Attaches the given tunnel to this GuacamoleSession.
     * @param tunnel The tunnel to attach to this GucacamoleSession.
     */
    public void attachTunnel(GuacamoleTunnel tunnel) {
        tunnels.put(tunnel.getUUID().toString(), tunnel);
        logger.debug("Attached tunnel {}.", tunnel.getUUID());
    }

    /**
     * Detaches the given tunnel to this GuacamoleSession.
     * @param tunnel The tunnel to detach to this GucacamoleSession.
     */
    public void detachTunnel(GuacamoleTunnel tunnel) {
        tunnels.remove(tunnel.getUUID().toString());
        logger.debug("Detached tunnel {}.", tunnel.getUUID());
    }

    /**
     * Returns the tunnel with the given UUID attached to this GuacamoleSession,
     * if any.
     *
     * @param tunnelUUID The UUID of an attached tunnel.
     * @return The tunnel corresponding to the given UUID, if attached, or null
     *         if no such tunnel is attached.
     */
    public GuacamoleTunnel getTunnel(String tunnelUUID) {
        return tunnels.get(tunnelUUID);
    }

}
