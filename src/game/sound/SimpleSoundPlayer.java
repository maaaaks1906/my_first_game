package game.sound;

import javax.sound.sampled.*;
import java.io.*;

public class SimpleSoundPlayer {
    public static void main(String[] args) {
        //Loading sound
        SimpleSoundPlayer sound = new SimpleSoundPlayer("/Users/maks/IdeaProjects/my_first_game/sounds/voice.wav");

        //Creating a sound stream
        InputStream stream = new ByteArrayInputStream(sound.getSamples());

        //Method used to play sound
        sound.play(stream);

        System.exit(0);
    }

    private AudioFormat format;
    private byte[] samples;

    public SimpleSoundPlayer(String fileName) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));

            format = stream.getFormat();

            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples() {
        return samples;
    }

    private byte[] getSamples(AudioInputStream audioStream) {
        int length = (int) (audioStream.getFrameLength() * format.getFrameSize());

        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return samples;
    }

    public void play(InputStream source) {
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];

        SourceDataLine line;
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }

        line.start();

        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                    line.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        line.drain();
        line.close();
    }
}
