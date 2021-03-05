package game.sound;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/*
Class used to play sound in a loop until the close() method is used.
Loop can be defined how many times it has to run.
 */
public class LoopingByteInputStream extends ByteArrayInputStream {
    private boolean closed;

    public LoopingByteInputStream(byte[] buffer) {
        super(buffer);
        closed = false;
    }

    public int read(byte[] buffer, int offset, int length) {
        if (closed) {
            return -1;
        }

        int totalBytesRead = 0;

        while (totalBytesRead < length) {
            int numBytesRead = super.read(buffer, offset + totalBytesRead, length - totalBytesRead);

            if (numBytesRead < 0) {
                totalBytesRead += numBytesRead;
            } else {
                reset();
            }
        }

        return totalBytesRead;
    }

    public void close() throws IOException {
        super.close();
        closed = true;
    }
}
