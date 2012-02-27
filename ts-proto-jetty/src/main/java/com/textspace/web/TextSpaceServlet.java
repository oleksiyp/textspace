package com.textspace.web;

import com.textspace.App;
import com.textspace.space.TextSpaceCenter;
import com.textspace.utils.DynamicFilePage;
import com.textspace.utils.Page;
import com.textspace.utils.StaticPage;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TextSpaceServlet extends WebSocketServlet
{
    private static final String TEXT_SPACE_CENTER = "com.textspace.space.TextSpaceCenter";

    private static final File DATA_FILE = new File("data.obj");

    private final Set _members = new CopyOnWriteArraySet();

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private Page indexHtml = null;

    @Override
    public void init() throws ServletException {
        super.init();
        initIndexHtml();
        initDataFile();
    }

    private void initIndexHtml() throws ServletException {
        try {
            if (App.DEV_WEB_PATH != null) {
                indexHtml = new DynamicFilePage(new File(App.DEV_WEB_PATH, "index.html"));
            } else {
                URL resource = TextSpaceServlet.class.getResource("index.html");
                if (resource == null) {
                    throw new IOException("no resource");
                }
                indexHtml = new StaticPage(resource);
            }
        } catch (IOException e) {
            throw new ServletException("failed to find 'index.html' resource");
        }
    }

    private void initDataFile() throws ServletException {
        if (DATA_FILE.exists()) {
            try {
                getTextSpaceCenter(getServletContext())
                        .getTextSpace().loadFrom(DATA_FILE);
            } catch (IOException e) {
                throw new ServletException("failed to load from '" + DATA_FILE + "': " + e);
            }
        }

        executor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    getTextSpaceCenter(getServletContext())
                            .getTextSpace().saveTo(DATA_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException
    {
        indexHtml.output(request, response);
        response.getWriter().flush();
    }
    
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol)
    {
        TextSpaceCenter center = getTextSpaceCenter(request.getServletContext());
        TextSpaceWebSocket socket = new TextSpaceWebSocket(center);
        return socket;
    }

    private static TextSpaceCenter getTextSpaceCenter(ServletContext ctx) {
        TextSpaceCenter center = (TextSpaceCenter) ctx.getAttribute(TEXT_SPACE_CENTER);
        if (center == null) {
            center = new TextSpaceCenter();
            ctx.setAttribute(TEXT_SPACE_CENTER, center);
        }
        return center;
    }

}