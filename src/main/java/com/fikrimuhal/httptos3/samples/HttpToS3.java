package com.fikrimuhal.httptos3.samples;

import com.fikrimuhal.httptos3.downloaders.HttpDownloader;
import com.fikrimuhal.httptos3.writers.S3Writer;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.fikrimuhal.httptos3.utils.HttpUtils.getContentLength;

/**
 *  Sample class for reading from HTTP and writing to Amazon S3 bucket.
 */
public class HttpToS3 {
    private static final Logger logger = Logger.getLogger(HttpDownloader.class.getName());

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter URL ( http://www.sample-videos.com/video/mp4/360/big_buck_bunny_360p_1mb.mp4 ):");
        String url = scan.next();
        System.out.println("Enter S3 bucket name: ");
        String bucketName = scan.next();
        System.out.println("Enter S3 save path: ");
        String savePath = scan.next();

        int contentLength = getContentLength(url);
        logger.info("ContentLength = " + contentLength);

        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        inputStream.connect(outputStream);
        new Thread(new HttpDownloader(url, outputStream)).start();
        new Thread(new S3Writer(inputStream, contentLength, bucketName, savePath)).start();
    }
}
