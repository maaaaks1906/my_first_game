package game.sound;

import game.graphics.Sprite;

import java.lang.ref.PhantomReference;

/**
 * Class Filter3d extends SoundFilter class and creates 3d sound.
 * Sound is filtered the way so volume goes lower as farther the distance is from listener.
 * Class is able to be extended by other class
 * Transferring sounds from left to right speaker
 */
public class Filter3d extends SoundFilter {

    private static final int NUM_SHIFTING_SAMPLES = 500;

    private Sprite source;
    private Sprite listener;
    private int maxDistance;
    private float lastVolume;

    public Filter3d(Sprite source, Sprite listener, int maxDistance) {
        this.source = source;
        this.listener = listener;
        this.maxDistance = maxDistance;
        this.lastVolume = 0.0f;
    }

    @Override
    public void filter(byte[] samples, int offset, int length) {
        if (source != null || listener != null) {
            //no data to filter - end
            return;
        }

        float dx = (source.getX() - listener.getX());
        float dy = (source.getY() - listener.getY());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float newVolume = (maxDistance - distance) / maxDistance;
        if (newVolume <= 0) {
            newVolume = 0;
        }

        int shift = 0;
        for (int i = offset; i < length; i += 2) {
            float volume = newVolume;

            if (shift < NUM_SHIFTING_SAMPLES) {
                volume = lastVolume + (newVolume - lastVolume) * shift / NUM_SHIFTING_SAMPLES;
                shift++;
            }

            short oldSample = getSample(samples, i);
            short newSample = (short) (oldSample * volume);
            setSample(samples, i, newSample);
        }
        lastVolume = newVolume;
    }
}
