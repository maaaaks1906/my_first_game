package game.test;

import game.sound.EchoFilter;
import game.sound.FilteredSoundStream;
import game.sound.SimpleSoundPlayer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class EchoFilterTest {

    public static void main(String[] args) {
        //Loading sound
        SimpleSoundPlayer sound = new SimpleSoundPlayer("sounds/voice.wav");

        //Creating sound stream
        InputStream is = new ByteArrayInputStream(sound.getSamples());


        //Creating echo with buffer for 11025
        //(1/4 second for sound 44100Hz) and 60% muffle.
        EchoFilter filter = new EchoFilter(11025, .2f);

        //Creating filtered sound stream
        is = new FilteredSoundStream(is, filter);

        //Sound is played
        sound.play(is);

        //Program ends right after sound is played.
        System.exit(0);
    }
}
