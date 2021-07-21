package util;

/**
 *
 * The util calss of HTTP protocal.
 *
 * @author jingjiejiang
 * @history Jul 20, 2021
 *
 */
public class HttpProtocolUtil {

    /**
     *
     * The method for generating header for code 200.
     *
     */
    public static String getHttpHeader200(long contentLength) {

        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }

    /**
     *
     * The method for generating header for code 404
     *
     */
    public static String getHttpHeader404() {

        String str404 = "<h1>404 not found</h1>";

        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;
    }
}
