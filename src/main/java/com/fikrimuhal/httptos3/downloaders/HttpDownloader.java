package com.fikrimuhal.httptos3.downloaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by aemre on 10/01/17.
 */
public class HttpDownloader implements Runnable {
    private static final int BUFFER_SIZE = 4096;
    private static final Logger logger = Logger.getLogger(HttpDownloader.class.getName());
    private PipedOutputStream outputStream;
    private InputStream inputStream;
    private HttpURLConnection httpConn;
    private int responseCode;
    private String contentType;
    private URL url;


    public HttpDownloader(String fileUrl, PipedOutputStream outputStream) throws IOException {
        this.url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        this.responseCode = httpConn.getResponseCode();
        this.contentType = httpConn.getContentType();
        logger.fine("ContentType: " + contentType);
        // opens input stream from the HTTP connection
        this.httpConn = httpConn;
        this.inputStream = httpConn.getInputStream();
        ;
        this.outputStream = outputStream;
    }

    public void run() {
        logger.info("Download starts: " + url);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try {
                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    logger.fine("Writing to OS. BytesRead = " + bytesRead);
                    outputStream.write(buffer, 0, bytesRead);
                }

            } catch (Exception e) {
                System.err.println(e);
            } finally {
                logger.info("File downloaded: " + url);
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                httpConn.disconnect();
            }
        } else {
            logger.severe("Response code for HTTP request is: " + responseCode);
        }
    }
}
