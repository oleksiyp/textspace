package com.textspace.utils;

import java.io.*;
import java.net.URL;

/**
 * User: captain-protect
 * Date: 2/18/12
 * Time: 12:48 PM
 */
public class Utils {
    public static String readAsString(URL resource) throws IOException {
        StringBuffer result = new StringBuffer();

        InputStream in = resource.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        char []buf = new char[4096];
        int read;
        while ((read = reader.read(buf, 0, buf.length)) != -1) {
            result.append(buf, 0, read);
        }
        reader.close();

        return result.toString();
    }
}
