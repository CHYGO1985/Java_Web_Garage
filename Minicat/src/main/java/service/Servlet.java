package service;

import controller.Request;
import controller.Response;

/**
 *
 * The interface for servlet
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public interface Servlet {

    void init() throws Exception;
    void destroy() throws Exception;
    void service(Request request, Response response) throws Exception;
}
