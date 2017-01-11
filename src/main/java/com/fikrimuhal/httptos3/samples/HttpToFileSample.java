package com.fikrimuhal.httptos3.samples;

import com.fikrimuhal.httptos3.downloaders.HttpDownloader;
import com.fikrimuhal.httptos3.writers.FileWriter;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Sample class for reading from HTTP and writing to File on local filesystem.
 */
public class HttpToFileSample {
    public static void main(String[] args) throws IOException {
        String url = "http://www.sample-videos.com/video/mp4/360/big_buck_bunny_360p_1mb.mp4";
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        inputStream.connect(outputStream);
        new Thread(new HttpDownloader(url, outputStream)).start();
        new Thread(new FileWriter(inputStream, "/tmp/bunny.mp4")).start();

    }
}
