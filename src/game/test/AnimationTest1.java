package game.test;

import game.graphics.Animation;
import game.graphics.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

public class AnimationTest1 {
    public static void main(String[] args) {
        System.out.println("////////////////////");
        System.out.println("JEDZIEMY");
        DisplayMode displayMode = new DisplayMode(1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN);

        AnimationTest1 test = new AnimationTest1();
        System.out.println("METODA RUN = ON");
        System.out.println("/////////////////////");
        test.run(displayMode);
    }

    private static final long DEMO_TIME = 5000;

    private SimpleScreenManager screen;
    private Image bgImage;
    private Animation anim;

    private void run(DisplayMode displayMode) {
        screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, new JFrame());
            loadImages();
            animationLoop();
        } finally {
            screen.restoreScreen();
        }
    }
    private void loadImages() {
        System.err.println("Załadowanie obrazkow! 1");
        bgImage = loadImage("images/background.jpg");
        Image player1 = loadImage("images/player1.png");
        Image player2 = loadImage("images/player2.png");
        Image player3 = loadImage("images/player3/png");

        System.err.println("Ladowanie animacji 2");
        anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);
        System.out.println("Koniec ladowania");
        System.out.println("////////////////////////////");
    }

    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    private void animationLoop() {
        System.err.println("Animation loop start 3");
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        System.out.println("Sprawdzanie czy currentTime - startTime < 5000");
        System.out.println(currentTime - startTime);
        System.out.println("///////////////////////");
        while (currentTime - startTime < DEMO_TIME) {
            System.err.println("Jest mniejsze!");
            long elapsedTime = System.currentTimeMillis() - currentTime;
            System.out.println("elapsedTime = currentTimeMillis - current time");
            System.out.println(elapsedTime);
            System.out.println("///////////////////");
            System.out.println("currentTime += elapsedTime");

            currentTime += elapsedTime;
            System.out.println(currentTime);
            System.out.println("///////////////////");

            System.err.println("Metoda update()! 4");;
            anim.update(elapsedTime);

            Graphics g = screen.getFullScreenWindow().getGraphics();
            draw(g);
            g.dispose(); // TODO: 15.01.2021 co to robi <--

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw(Graphics g) {
        g.drawImage(bgImage, 0, 0, null);

        g.drawImage(anim.getImage(), 0, 0, null);
    }


}
