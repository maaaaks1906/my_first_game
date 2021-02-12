package game.graphics;

import java.awt.*;
import java.util.ArrayList;

public class Animation {
    private ArrayList frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;

    public Animation() {
        frames = new ArrayList();
        totalDuration = 0;
        start();
    }

    public synchronized void start() {
        animTime = 0;
        currFrameIndex = 0;
    }

    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public synchronized void update(long elapsedTime) {

        if (frames.size() > 1) {
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            } else {
            }
            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;
            }
        }
    }

    private AnimFrame getFrame(int currFrameIndex) {
        return (AnimFrame) frames.get(currFrameIndex);
    }

    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currFrameIndex).image;
        }
    }

    private class AnimFrame { // TODO: 10.02.2021 bylo static
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
