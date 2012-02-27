package com.textspace.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/26/12
 * Time: 9:29 PM
 */
public abstract class Page {
    public abstract void output(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
