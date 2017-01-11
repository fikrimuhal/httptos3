package com.fikrimuhal.httptos3.writers;

import java.io.PipedInputStream;

/**
 * Writer for writing the input data to standard output.
 */
public class StdoutWriter extends AbstractWriter {
    /**
     * Constructor for Stdout Writer.
     * @param in PipedInputStream to read the bytes from.
     */
    public StdoutWriter(PipedInputStream in) {
        this.in = in;
        this.out = System.out;
    }
}
