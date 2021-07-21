package service;

import controller.Request;
import controller.Response;
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

        String content = "<h1>LagouServlet getß</h1>";

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
