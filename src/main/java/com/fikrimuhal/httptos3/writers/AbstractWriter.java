package com.fikrimuhal.httptos3.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;

/**
 * Created by aemre on 10/01/17.
 *
 * An Abstract Writer class that handles reading from PipedInputStream to given OutputStream.
 * Each subclass should define an OutputStream and special parameters that may be needed
 */
public abstract class AbstractWriter implements Runnable {
    private static final int BUFFER_SIZE = 1024;
    protected PipedInputStream in;
    protected OutputStream out;

    public void run() {
        int len;
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
