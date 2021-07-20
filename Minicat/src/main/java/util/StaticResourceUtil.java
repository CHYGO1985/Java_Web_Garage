package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * The util class for getting path of static resources.
 *
 * @author jingjiejiang
 * @history Jul 20, 2021
 *
 */
public class StaticResourceUtil {

    /**
     *
     * Get the abs path at /target for static resources
     *
     * @param path
     * @return
     */
    public static String getAbsPath(String path) {

        //get the path of /target
        String absPath = StaticResourceUtil.class.getResource("/").getPath();

        return absPath.replaceAll("\\\\", "/") + path;
    }

    /**
     *
     * Read input stream of static files, then ouput via output stream
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream)
        throws IOException {

        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }

        int resSize = count;
        // first write header, then payload
        outputStream.write(HttpProtocalUtil.getHttpHeader200(resSize).getBytes());

        // read content and then output
        long written = 0; // read content length
        int byteSize = 1024; // buffer size
        byte[] bytes = new byte[byteSize];

        while (written < resSize) {

            // means the left unread length is less then 1024, read as exact length
            if (written + byteSize > resSize) {
                byteSize = (int) (resSize - written);
                bytes = new byte[byteSize];
            }

            inputStream.read(bytes);
            outputStream.write(bytes);

            outputStream.flush();
            written += byteSize;
        }
    }
}
