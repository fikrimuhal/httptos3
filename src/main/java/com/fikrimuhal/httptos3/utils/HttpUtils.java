package com.fikrimuhal.httptos3.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP Utils
 */
public class HttpUtils {
    /**
     * Returns the content length of the file on a given URL.
     * @param url URL
     * @return length of the file in bytes
     * @throws IOException
     */
    public static int getContentLength(String url) throws IOException {
        HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
        return httpConn.getContentLength();
    }
}
