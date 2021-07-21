package server;

import controller.Request;
import controller.Response;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import service.HttpServlet;
import util.HttpProtocolUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, HttpServlet> servletMap = new HashMap<String, HttpServlet>();

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
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
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
     * Minicat initialization
     * v3.0: deliver dynamic resource (servlet)
     *
     */
    public void startV3() throws Exception {

        // load config from web.xml
        loadServlet();

        initServerSocket();
        System.out.println("======> Minicat v2 start on port: " + port);

        while (true) {

            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            String url = request.getUrl();

            if (servletMap.get(url) == null) {
                // static resource
                response.outputHtml(url);
            } else {
                // servlet request
                HttpServlet httpServlet = servletMap.get(url);
                httpServlet.service(request, response);
            }

            socket.close();
        }
    }

    /**
     *
     * Load and interpret web.xml, init Servlet (build url and servlet class instance mapping)
     *
     */
    private void loadServlet() {

        InputStream resAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();

        try {

            Document document = saxReader.read(resAsStream);
            Element rootElement = document.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");

            for (int idx = 0; idx < selectNodes.size(); idx ++) {
                Element element = selectNodes.get(idx);
                // <servlet-name>lagou</servlet-name>
                Element servletNameEle = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletNameEle.getStringValue();
                // <servlet-class>server.LagouServlet</servlet-class>
                Element servletClassEle = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassEle.getStringValue();

                // according to servlet-name and find url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode(
                        "/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                // url: /lagou
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            bootstrap.startV3();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
