package com.fikrimuhal.httptos3.writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PipedInputStream;

/**
 * Writer for writing the input data to a file.
 */
public class FileWriter extends AbstractWriter {
    /**
     * Constructor for FileWriter
     * @param in PipedInputStream to read the bytes from.
     * @param filePath The file path to write the data on. Example: /tmp/bunny.mp4
     * @throws FileNotFoundException
     */
    public FileWriter(PipedInputStream in, String filePath) throws FileNotFoundException {
        this.in = in;
        this.out = new FileOutputStream(filePath);
    }
}
