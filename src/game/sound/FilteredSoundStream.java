package game.sound;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilteredSoundStream extends FilterInputStream {

    private static final int REMAINING_SIZE_UNKNOWN = -1;

    private SoundFilter soundFilter;
    private int remainingSize;

    public FilteredSoundStream(InputStream in, SoundFilter soundFilter) {
        super(in);
        this.soundFilter = soundFilter;
        remainingSize = REMAINING_SIZE_UNKNOWN;
    }

    public int read(byte[] samples, int offset, int length) throws IOException {
        int bytesRead = super.read(samples, offset, length);
        if (bytesRead > 0) {
            soundFilter.filter(samples, offset, bytesRead);
            return bytesRead;
        }

        if (remainingSize == REMAINING_SIZE_UNKNOWN) {
            remainingSize = soundFilter.getRemainingSize();
            remainingSize = remainingSize / 4 * 4;
        }

        if (remainingSize > 0) {
            length = Math.min(length, remainingSize);

            for (int i = offset; i < offset + length; i++) {
                samples[i] = 0;
            }

            soundFilter.filter(samples, offset, length);
            remainingSize -= length;

            return length;
        } else {
            return -1;
        }
    }
}
