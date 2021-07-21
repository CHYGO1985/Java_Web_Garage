package pojo;

/**
 *
 * The pojo class for context.
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public class Context {

    private String dir;
    private String url;

    public Context(String url, String dir) {
        this.dir = dir;
        this.url = url;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
