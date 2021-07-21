package webapps.demo1.server;

import controller.Request;
import controller.Response;
import service.HttpServlet;
import util.HttpProtocolUtil;

import java.io.IOException;

/**
 *
 * The servlet class.
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public class LagouServlet extends HttpServlet {


    @Override
    public void doGet(Request request, Response response) {

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String content = "<h1>LagouServlet v1 get</h1>";

        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length)) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {

        String content = "<h1>LagouServlet post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
