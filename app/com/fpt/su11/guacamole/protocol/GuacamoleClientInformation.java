package com.fpt.su11.guacamole.protocol;


import java.util.ArrayList;
import java.util.List;

/**
 * An abstract representation of Guacamole client information, including all
 * information required by the Guacamole protocol during the preamble.
 *
 * @author Michael Jumper
 */
public class GuacamoleClientInformation {

    /**
     * The optimal screen width requested by the client, in pixels.
     */
    private int optimalScreenWidth  = 1024;

    /**
     * The optimal screen height requested by the client, in pixels.
     */
    private int optimalScreenHeight = 768;

    /**
     * The resolution of the optimal dimensions given, in DPI.
     */
    private int optimalResolution = 96;

    /**
     * The list of audio mimetypes reported by the client to be supported.
     */
    private final List<String> audioMimetypes = new ArrayList<String>();

    /**
     * The list of audio mimetypes reported by the client to be supported.
     */
    private final List<String> videoMimetypes = new ArrayList<String>();

    /**
     * Returns the optimal screen width requested by the client, in pixels.
     * @return The optimal screen width requested by the client, in pixels.
     */
    public int getOptimalScreenWidth() {
        return optimalScreenWidth;
    }

    /**
     * Sets the client's optimal screen width.
     * @param optimalScreenWidth The optimal screen width of the client.
     */
    public void setOptimalScreenWidth(int optimalScreenWidth) {
        this.optimalScreenWidth = optimalScreenWidth;
    }

    /**
     * Returns the optimal screen height requested by the client, in pixels.
     * @return The optimal screen height requested by the client, in pixels.
     */
    public int getOptimalScreenHeight() {
        return optimalScreenHeight;
    }

    /**
     * Sets the client's optimal screen height.
     * @param optimalScreenHeight The optimal screen height of the client.
     */
    public void setOptimalScreenHeight(int optimalScreenHeight) {
        this.optimalScreenHeight = optimalScreenHeight;
    }

    /**
     * Returns the resolution of the screen if the optimal width and height are
     * used, in DPI.
     * 
     * @return The optimal screen resolution.
     */
    public int getOptimalResolution() {
        return optimalResolution;
    }

    /**
     * Sets the resolution of the screen if the optimal width and height are
     * used, in DPI.
     * 
     * @param optimalResolution The optimal screen resolution in DPI.
     */
    public void setOptimalResolution(int optimalResolution) {
        this.optimalResolution = optimalResolution;
    }

    /**
     * Returns the list of audio mimetypes supported by the client. To add or
     * removed supported mimetypes, the list returned by this function can be
     * modified.
     *
     * @return The set of audio mimetypes supported by the client.
     */
    public List<String> getAudioMimetypes() {
        return audioMimetypes;
    }

    /**
     * Returns the list of video mimetypes supported by the client. To add or
     * removed supported mimetypes, the list returned by this function can be
     * modified.
     *
     * @return The set of video mimetypes supported by the client.
     */
    public List<String> getVideoMimetypes() {
        return videoMimetypes;
    }

}

