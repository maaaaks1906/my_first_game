package game.test;

import game.graphics.Animation;
import game.graphics.ScreenManager;

import javax.swing.*;
import java.awt.*;

public class AnimationTest2 {
    public static void main(String[] args) {
        AnimationTest2 test = new AnimationTest2();
        test.run();
    }

    private static final DisplayMode POSSIBLE_MODES[] = {
            new DisplayMode(1920, 1080, 32, 0),
            new DisplayMode(1920, 1080, 24, 0),
            new DisplayMode(1920, 1080,16,0),
            new DisplayMode(1680, 1050,32,0),
            new DisplayMode(1680, 1050,24,0),
            new DisplayMode(1680, 1050,16,0),
    };

    private static final long DEMO_TIME = 10000;

    private ScreenManager screen;
    private Image bgImage;
    private Animation anim;

    public void loadImages() {
        bgImage = loadImage("images/background.jpg");
        Image player1 = loadImage("images/player1.png");
        Image player2 = loadImage("images/player2.png");
        Image player3 = loadImage("images/player3.png");

        anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);
    }

    public Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    private void run() {
        screen = new ScreenManager();

        try {
            DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
            screen.setFullScreen(displayMode);
            loadImages();
            animationLoop();
        } finally {
            screen.restoreScreen();
        }
    }

    private void animationLoop() {
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while (currentTime - startTime < DEMO_TIME) {
            long elapsedTime = System.currentTimeMillis() - currentTime;
            currentTime += elapsedTime;

            anim.update(elapsedTime);
            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }


    }

    private void draw(Graphics2D g) {
        g.drawImage(bgImage, 0, 0, null);

        g.drawImage(anim.getImage(),0,0,null);
    }
}
