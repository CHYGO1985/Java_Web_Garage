package controller;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * The class for http request.
 *
 * @author jingjiejiang
 * @history Jul 20, 2021
 *
 */
public class Request {

    // request method, like GET/POST
    private String method;
    // like /, /index.html
    private String url;

    private InputStream inputStream;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Request() {
    }

    public Request(InputStream inputStream) throws IOException {

        String[] params = getReqFirstLineParams(inputStream);

        this.method = params[0];
        this.url = params[1];

        System.out.println(" ====>> method: " + method);
        System.out.println(" ====>> url: " + url);
    }

    /**
     *
     * Get the params in the first like of request. "GET / HTTP/1.1"
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String[] getReqFirstLineParams(InputStream inputStream) throws IOException {

        this.inputStream = inputStream;

        // Get request info from input stream
        int count = 0;
        while (count == 0) {
            try {
                count = inputStream.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] bytes = new byte[count];
        inputStream.read(bytes);

        String inputStr = new String(bytes);
        // get the first line in the request header "GET / HTTP/1.1"
        String fstLineStr = inputStr.split("\\n")[0];
        String[] params = fstLineStr.split(" ");

        return params;
    }
}
