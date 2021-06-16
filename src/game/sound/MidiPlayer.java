package game.sound;

import javax.sound.midi.*;
import java.io.*;

public class MidiPlayer implements MetaEventListener {
    public static final int END_OF_TRACK_MESSAGE = 47;

    private Sequencer sequencer;
    private boolean loop;
    private boolean paused;


    public MidiPlayer() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
        } catch (MidiUnavailableException ex) {
            sequencer = null;
        }
    }

    public Sequence getSequence(String filename) {
        try {
            return getSequence(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Sequence getSequence(InputStream is) {
        try {
            if (!is.markSupported()) {
                is = new BufferedInputStream(is);
            }
            Sequence s = MidiSystem.getSequence(is);
            is.close();
            return s;
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void play(Sequence sequence, boolean loop) {
        if (sequencer != null && sequence != null && sequencer.isOpen()) {
            try {
                sequencer.setSequence(sequence);
                sequencer.start();
                this.loop = loop;
            } catch (InvalidMidiDataException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void meta(MetaMessage event) {
        if (event.getType() == END_OF_TRACK_MESSAGE) {
            if (sequencer != null && sequencer.isOpen() && loop) {
                sequencer.start();
            }
        }
    }

    public void stop() {
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.stop();
            sequencer.setMicrosecondPosition(0);
        }
    }

    public void close() {
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.close();
        }
    }
    public Sequencer getSequencer() {
        return sequencer;
    }

    public void setPaused(boolean paused) {
        if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
            this.paused = paused;
            if (paused) {
                sequencer.stop();
            } else {
                sequencer.start();
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }
}
