package server;

import util.HttpProtocalUtil;

import java.io.IOException;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     *
     * Minicat initialization
     */
    public void start() throws IOException {

        /**
         * v1.0: visit localhost:8080, then return "Hello Minicat!"
         */

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("======> Minicat start on port: " + port);

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
     * Main method of minicat.
     *
     */
    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
