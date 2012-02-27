package com.textspace.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * User: captain-protect
 * Date: 2/26/12
 * Time: 9:30 PM
 */
public class StaticPage extends Page {
    private String text;

    public StaticPage(URL resource) throws IOException {
        text = Utils.readAsString(resource);
    }

    @Override
    public void output(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(text);
    }
}
