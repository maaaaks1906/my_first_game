package game.test;

import game.sound.EchoFilter;
import game.sound.FilteredSoundStream;
import game.sound.SimpleSoundPlayer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class EchoFilterTest {

    public static void main(String[] args) {
        SimpleSoundPlayer sound = new SimpleSoundPlayer("sounds/voice.wav");

        InputStream is = new ByteArrayInputStream(sound.getSamples());

        EchoFilter filter = new EchoFilter(11025, .3f);

        is = new FilteredSoundStream(is, filter);

        sound.play(is);

        System.exit(0);
    }
}
