package game.test;

import game.graphics.Animation;
import game.graphics.Sprite;
import game.graphics.ScreenManager;

import javax.swing.*;
import java.awt.*;

public class SpriteTest1 {
    public static void main(String[] args) {
        SpriteTest1 test1 = new SpriteTest1();
        test1.run();
    }

    private static final DisplayMode POSSIBLE_DISPLAY_MODES[] = {
            new DisplayMode(1920, 1080, 32, 0),
            new DisplayMode(1920, 1080, 24, 0),
            new DisplayMode(1920, 1080, 16, 0),
            new DisplayMode(1680, 1050, 32, 0),
            new DisplayMode(1680, 1050, 24, 0),
            new DisplayMode(1680, 1050, 16, 0)
    };

    private static final long DEMO_TIME = 10000;

    private ScreenManager screen;
    private Image bgImage;
    private Sprite sprite;

    private void loadImages() {
        bgImage = loadImage("images/background.jpg");
        Image player1 = loadImage("images/player1.png");
        Image player2 = loadImage("images/player2.png");
        Image player3 = loadImage("images/player3.png");

        Animation animation = new Animation();
        animation.addFrame(player1, 250);
        animation.addFrame(player2, 150);
        animation.addFrame(player1, 150);
        animation.addFrame(player2, 150);
        animation.addFrame(player3, 200);
        animation.addFrame(player2, 150);
        sprite = new Sprite(animation);

        sprite.setVelocityX(0.6f);
        sprite.setVelocityY(0.1f);
    }

    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    private void run() {
        screen = new ScreenManager();
        try {
            DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_DISPLAY_MODES);
            screen.setFullScreen(displayMode);
            loadImages();
            animationLoop();
        }finally {
            screen.restoreScreen();
        }
    }

    private void animationLoop() {
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while (currentTime - startTime < DEMO_TIME) {
            long elapsedTime = System.currentTimeMillis() - currentTime;
            currentTime += elapsedTime;

            update(elapsedTime);

            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update(long elapsedTime) {
        if (sprite.getX() < 0) {
            sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
        } else if (sprite.getX() + sprite.getWidth() >= screen.getWidth()) {
            sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
        }

        if (sprite.getY() < 0) {
            sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
        } else if (sprite.getY() + sprite.getHeight() >= screen.getHeight()) {
            sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
        }

        sprite.update(elapsedTime);
    }

    public void draw(Graphics g) {
        g.drawImage(bgImage, 0, 0, null);

        g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
    }
}
