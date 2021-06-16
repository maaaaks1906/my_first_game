package game.test;

import game.sound.MidiPlayer;

import javax.sound.midi.*;

public class MidiTest implements MetaEventListener {
    private static final int DRUM_TRACK = 1;

    public static void main(String[] args) {
        new MidiTest().run();
    }

    private MidiPlayer player;

    public void run() {
        player = new MidiPlayer();

        Sequence sequence = player.getSequence("sounds/music.midi");

        player.play(sequence, true);

        System.out.println("Odtwarzanie (bez bebnow)...");
        Sequencer sequencer = player.getSequencer();
        sequencer.setTrackMute(DRUM_TRACK, true);
        sequencer.addMetaEventListener(this);
        sequencer.setLoopCount(1);
    }

    public void meta(MetaMessage event) {
        Sequencer sequencer = player.getSequencer();
        if (event.getType() == MidiPlayer.END_OF_TRACK_MESSAGE && sequencer.getLoopCount() == 1) {
            Sequence sequence = player.getSequence("sounds/music.midi");
            player.stop();
            if (sequencer.getTrackMute(DRUM_TRACK)) {
                player.play(sequence, false);
                sequencer.setTrackMute(DRUM_TRACK, false);
                System.out.println("Wlaczanie bebnow... ");
            } else {
                System.out.println("Wylaczanie...");
                player.close();
                System.exit(1);
            }
        }
    }
}
