package game.sound;

import game.util.LoopingByteInputStream;

import javax.sound.sampled.*;
import java.io.*;

public class SimpleSoundPlayer {
    public static void main(String[] args) {
        //Loading sound
        SimpleSoundPlayer sound = new SimpleSoundPlayer("sounds/voice.wav");
        SimpleSoundPlayer sound3 = new SimpleSoundPlayer("sounds/wave.wav");


        //Creating a sound stream
        InputStream stream = new ByteArrayInputStream(sound.getSamples());

        //Method used to play sound
        sound.play(stream);

        //To play another sound new stream is required
        stream = new ByteArrayInputStream(sound3.getSamples());

        //new sound is played using same stream object
        sound.play(stream);

        //Program has to exit manually
        System.exit(0);
    }

    private AudioFormat format;
    private byte[] samples;

    public SimpleSoundPlayer(String fileName) {
        try {
            //Loads file and opens stream for sound input
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));

            //Sets file format to get to know played sound specifics
            format = stream.getFormat();

            //Stream splits sound in to "pieces" now those pieces are loaded in to list as samples
            samples = getSamples(stream);

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples() {
        return samples;
    }

    //Returns list of samples read from sound file
    private byte[] getSamples(AudioInputStream audioStream) {
        //This way program is getting number of bytes to read
        int length = (int) (audioStream.getFrameLength() * format.getFrameSize());

        //then loads it in to a new list
        //then it opens a stream and takes audioStream as parameter
        //next it tries to read samples, if impossible to read - throws exception
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
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10); //8820

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

        byte[] buffer = new byte[bufferSize];

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
