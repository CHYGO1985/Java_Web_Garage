package controller;

import util.HttpProtocalUtil;
import util.StaticResourceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * The class for http request.
 *
 * @author jingjiejiang
 * @history Jul 20, 2021
 *
 */
public class Response {

    private OutputStream outputStream;

    public Response() {

    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Use output stream to output certain strings
     */
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    /**
     *
     * @param path get the absolute path of static resources, then read the static resource and then
     *             output via output stream
     * @throws IOException
     */
    public void outputHtml(String path) throws IOException {

        // get abs path for static file
        String absResourcePath = StaticResourceUtil.getAbsPath(path);

        // get static resource file (under /target)
        File file = new File(absResourcePath);
        // file.isFile() is when there is no file namw in url localhost:8080
        if (file.exists() && file.isFile()) {
            // read static resource file and output
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            // outout 404
            output(HttpProtocalUtil.getHttpHeader404());
        }
    }
}
