package topworker.utils.rest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by echomil on 06.03.16.
 */
public class RequestHolder {

    private static final ThreadLocal<HttpServletRequest> THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getRequest() {

        return THREAD_LOCAL.get();
    }

    static void setRequest(HttpServletRequest request) {

        THREAD_LOCAL.set(request);
    }

    static void clean() {

        THREAD_LOCAL.remove();
    }
}