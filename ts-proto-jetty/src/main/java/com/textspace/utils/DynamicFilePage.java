package com.textspace.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * User: captain-protect
 * Date: 2/26/12
 * Time: 9:35 PM
 */
public class DynamicFilePage extends Page {
    private File file;

    public DynamicFilePage(File file) {
        super();
        this.file = file;
    }

    @Override
    public void output(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        PrintWriter writer = response.getWriter();

        char []buf = new char[4096];
        int read;
        while ((read = reader.read(buf, 0, buf.length)) != -1) {
            writer.write(buf, 0, read);
        }
        reader.close();
    }
}
