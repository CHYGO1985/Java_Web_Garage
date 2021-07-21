package pojo;

/**
 *
 * The pojo class for host.
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public class Host {

    // like localhost
    private String hostName;
    // webapps dir
    private String appDir;

    public Host(String hostName, String appDir) {

        this.hostName = hostName;
        this.appDir = appDir;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getAppDir() {
        return appDir;
    }

    public void setAppDir(String appDir) {
        this.appDir = appDir;
    }
}
