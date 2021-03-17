package game.util;

/**
This class is used to play sounds in a loop.
Loop starts on demand of user and it is played until close() method is called.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LoopingByteInputStream extends ByteArrayInputStream {

    private boolean closed;

    //creating a new LoopingByteInputStream object with byte array added
    public LoopingByteInputStream(byte[] buffer) {
        super(buffer);
        closed = false;
    }

    /*
     Reads length of bytes from array. If program will reach the end of array
     reading starts all over again. If closed() method is true program returns -1.
     */
    public int read(byte[] buffer, int offset, int length) {
        if (closed) {
            return -1;
        }
        int totalBytesRead = 0;

        while (totalBytesRead < length) {
            int numBytesRead = super.read(buffer, offset + totalBytesRead, length - totalBytesRead);

            if (numBytesRead > 0) {
                totalBytesRead += numBytesRead;
            } else {
                reset();
            }
        }
        return totalBytesRead;
    }

    //Closes the stream. Another calls of read() method will return -1 value
    public void close() throws IOException {
        super.close();
        closed = true;
    }
}
