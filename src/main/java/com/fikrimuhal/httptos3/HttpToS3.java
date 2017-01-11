package com.fikrimuhal.httptos3;

import com.fikrimuhal.httptos3.downloaders.HttpDownloader;
import com.fikrimuhal.httptos3.writers.S3Writer;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Logger;

import static com.fikrimuhal.httptos3.utils.HttpUtils.getContentLength;

/**
 * Created by aemre on 11/01/17.
 */
public class HttpToS3 {
    private static final Logger logger = Logger.getLogger(HttpDownloader.class.getName());

    public static void upload(String url, String bucketName, String savePath) throws IOException {
        int contentLength = getContentLength(url);
        logger.info("ContentLength = " + contentLength);
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        inputStream.connect(outputStream);
        new Thread(new HttpDownloader(url, outputStream)).start();
        new Thread(new S3Writer(inputStream, contentLength, bucketName, savePath)).start();
    }
}
