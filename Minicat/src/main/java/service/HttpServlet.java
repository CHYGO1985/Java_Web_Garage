package service;

import controller.Request;
import controller.Response;

/**
 *
 * The abstract Servlet class.
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public abstract class HttpServlet implements Servlet{

    public abstract void doGet(Request request, Response response);
    public abstract void doPost(Request request, Response response);

    @Override
    public void service(Request request, Response response) throws Exception {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
