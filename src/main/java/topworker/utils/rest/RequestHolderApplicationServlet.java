package topworker.utils.rest;

import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by echomil on 06.03.16.
 */
@WebServlet(value = "/*")
public class RequestHolderApplicationServlet extends VaadinServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestHolder.setRequest(request);

        super.service(request, response);

        // We remove the request from the thread local, there's no reason to keep it once the work is done
        RequestHolder.clean();
    }
}