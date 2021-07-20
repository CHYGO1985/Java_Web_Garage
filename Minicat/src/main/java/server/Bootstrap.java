package server;

import controller.Request;
import controller.Response;
import util.HttpProtocalUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * The main class for minicat.
 *
 * @author jingjiejiang
 * @history Jul 20, 2021
 *
 */
public class Bootstrap {

    /** socket listening port */
    private int port = 8080;
    private ServerSocket serverSocket;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void initServerSocket () throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     *
     * Minicat initialization
     * v1.0: visit localhost:8080, then return "Hello Minicat!"
     *
     */
    public void startV1() throws IOException {

        initServerSocket();
        System.out.println("======> Minicat v1 start on port: " + port);

        while(true) {
            Socket socket = serverSocket.accept();
            // once we have the socket, we use it to accept the request and then get the output stream
            OutputStream outputStream = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocalUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }
    }


    /**
     *
     * Minicat initialization
     * v2.0: wrap Request and Response object, and return html static resource file
     *
     */
    public void startV2() throws IOException {

        initServerSocket();
        System.out.println("======> Minicat v2 start on port: " + port);

        while (true) {

            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());
            socket.close();
        }
    }


    /**
     *
     * Main method of minicat.
     *
     */
    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.startV2();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
