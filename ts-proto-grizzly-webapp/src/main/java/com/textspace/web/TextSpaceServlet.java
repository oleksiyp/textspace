package com.textspace.web;

import com.sun.grizzly.websockets.WebSocketEngine;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/28/12
 * Time: 4:40 PM
 */
public class TextSpaceServlet extends javax.servlet.http.HttpServlet {
    private final TextSpaceAppliction app = new TextSpaceAppliction();

    @Override
    public void init(ServletConfig config) throws ServletException {
        WebSocketEngine.getEngine().register(app);
        super.init(config);
    }
}
