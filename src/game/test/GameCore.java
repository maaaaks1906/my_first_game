package game.test;

import game.graphics.ScreenManager;

import javax.swing.*;
import java.awt.*;

public abstract class GameCore {
    protected static final int FONT_SIZE = 24;

    private static final DisplayMode[] POSSIBLE_MODES = {
            new DisplayMode(1920, 1080, 32, 0),
            new DisplayMode(1920, 1080, 24, 0),
            new DisplayMode(1920, 1080, 16, 0),
            new DisplayMode(1680, 1050, 32, 0),
            new DisplayMode(1680, 1050, 24, 0),
            new DisplayMode(1680, 1050, 16, 0),
    };

    private boolean isRunning;
    protected ScreenManager screen;

    public void stop() {
        isRunning = false;
    }

    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screen.restoreScreen();
        }
    }

    public void init() {
        screen = new ScreenManager();
        DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
        screen.setFullScreen(displayMode);

        Window window = screen.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.HANGING_BASELINE, FONT_SIZE));
        window.setBackground(Color.blue);
        window.setForeground(Color.RED);

        isRunning = true;
    }

    public Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    public void gameLoop() {
        System.out.println("game loop");
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while (isRunning) {
            long elapsedTime = System.currentTimeMillis() - currentTime;
            currentTime += elapsedTime;

            update(elapsedTime);

            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) { }
        }
    }

    public void update(long elapsedTime) {

    }

    public abstract void draw(Graphics2D g);


}
