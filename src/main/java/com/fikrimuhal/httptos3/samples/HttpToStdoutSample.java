package com.fikrimuhal.httptos3.samples;

import com.fikrimuhal.httptos3.downloaders.HttpDownloader;
import com.fikrimuhal.httptos3.writers.StdoutWriter;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Sample class for reading from HTTP and writing to Standard Output
 */
public class HttpToStdoutSample {
    public static void main(String[] args) throws IOException {
        String url = "https://wordpress.org/plugins/about/readme.txt";
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        inputStream.connect(outputStream);

        new Thread(new HttpDownloader(url, outputStream)).start();
        new Thread(new StdoutWriter(inputStream)).start();
    }
}
