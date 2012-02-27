package com.textspace;

import com.textspace.web.TextSpaceServlet;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Http app.
 *
 */
public class App extends AbstractHandler {
    public static File DEV_WEB_PATH = null;

    public static void main( String[] args )
    {
        if (args.length > 0) {
            DEV_WEB_PATH = new File(args[0]);
        }
        try {
            App.startJetty();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void startJetty() throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(TextSpaceServlet.class, "/*");
        server.setHandler(context);

        server.start();
        server.join();
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
    }
}