package com.chygo.rpc.config;

/**
 *
 * To store global configuration.
 *
 * @author jingjiejiang
 * @history Aug 23, 2021
 *
 */
public class ConfigKeeper {

    private static volatile ConfigKeeper configKeeper;
    // Zookeeper port num
    private int port;
    // Zookeeper IP address
    private String address;
    // Report interval in the unit of second
    private int interval;
    // Client
    private boolean clintEnd;
    // Server
    private boolean serverEnd;

    private ConfigKeeper() {
    }

    /**
     *
     * Globally single instance.
     *
     * @return
     *
     */
    public static ConfigKeeper getInstance() {

        if (null == configKeeper) {
            synchronized (ConfigKeeper.class) {
                configKeeper = new ConfigKeeper();
            }
        }

        return configKeeper;
    }

    public static ConfigKeeper getConfigKeeper() {
        return configKeeper;
    }

    public static void setConfigKeeper(ConfigKeeper configKeeper) {
        ConfigKeeper.configKeeper = configKeeper;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isClintEnd() {
        return clintEnd;
    }

    public void setClintEnd(boolean clintEnd) {
        this.clintEnd = clintEnd;
        this.serverEnd = !clintEnd;
    }

    public boolean isServerEnd() {
        return serverEnd;
    }

    public void setServerEnd(boolean serverEnd) {
        this.serverEnd = serverEnd;
        this.clintEnd = !serverEnd;
    }
}
