package game.test;

import game.graphics.Animation;
import game.graphics.Sprite;
import game.graphics.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class SpriteTest2 {
    public static void main(String[] args) {
        SpriteTest2 test = new SpriteTest2();
        test.run();
    }

    private static final DisplayMode AVAILABLE_MODES[] = {
            new DisplayMode(1920, 1080, 32, 0),
            new DisplayMode(1920,1080,24,0),
            new DisplayMode(1920,1080,16,0),
            new DisplayMode(1680,1050,32,0),
            new DisplayMode(1680,1050,24,0),
            new DisplayMode(1680,1050,16,0)
    };

    private ScreenManager screen;
    private Image bgImage;
    private Image bgImage2;
    private Sprite[] sprites;

    private static final long DEMO_TIME = 20000;
    private static final long FADE_TIME = 1000;
    private static final int NUM_SPRITES = 6;

    private void loadImages() {
        bgImage = loadImage("images/kicia.jpg");

        Image player1 = loadImage("images/morda1.png");
        Image player2 = loadImage("images/morda2.jpg");
        Image player3 = loadImage("images/morda3.jpg");

        sprites = new Sprite[NUM_SPRITES];
        for (int i = 0; i < NUM_SPRITES; i++) {
            Animation anim = new Animation();
            anim.addFrame(player1, 400);
            anim.addFrame(player2, 350);
            anim.addFrame(player1, 350);
            anim.addFrame(player2, 250);
            anim.addFrame(player3, 300);
            anim.addFrame(player2, 250);
            sprites[i] = new Sprite(anim);

            sprites[i].setX((float) Math.random() * (screen.getWidth() - sprites[i].getWidth()));
            sprites[i].setY((float) Math.random() * (screen.getHeight() - sprites[i].getHeight()));

            sprites[i].setVelocityX((float) Math.random() - 0.5f);
            sprites[i].setVelocityY((float) Math.random() - 0.5f);
        }
    }

    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    private void run() {
        screen = new ScreenManager();
        try {
            DisplayMode displayMode = screen.findFirstCompatibleMode(AVAILABLE_MODES);
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

//            Toolkit.getDefaultToolkit().addAWTEventListener(
//                    new AWTEventListener() {
//                        @Override
//                        public void eventDispatched(AWTEvent event) {
//                            System.out.println(event+"lol");
//                        }
//                    }, -1
//            );

            update(elapsedTime);

            Graphics2D g = screen.getGraphics();
            draw(g);
            drawFade(g, currentTime - startTime);
            g.dispose();
            screen.update();


            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

    private void drawFade(Graphics2D g, long currentTime) {
        long time = 0;

        if (currentTime <= FADE_TIME) {
            time = FADE_TIME - currentTime;
        } else if (currentTime > DEMO_TIME - FADE_TIME) {
            time = FADE_TIME - DEMO_TIME + currentTime;
        } else {
            return;
        }

        byte numBars = 8;
        int barHeight = screen.getHeight();
        int blackHeight = (int) (time * barHeight / FADE_TIME);

        g.setColor(Color.black);
        for (int i = 0; i < numBars; i++) {
            int y = i * barHeight + (barHeight - blackHeight) / 2;
            g.fillRect(0,y, screen.getWidth(), blackHeight);
        }
    }

    private void update(long elapsedTime) {
        for (int i = 0; i < NUM_SPRITES; i++) {
            Sprite s = sprites[i];
            if (s.getX() < 0) {
                s.setVelocityX(Math.abs(s.getVelocityX()));
            } else if (s.getX() + s.getWidth() >= screen.getWidth()) {
                s.setVelocityX(-Math.abs(s.getVelocityX()));
            }

            if (s.getY() < 0) {
                s.setVelocityY(Math.abs(s.getVelocityY()));
            } else if (s.getY() + s.getHeight()>= screen.getHeight()) {
                s.setVelocityY(-Math.abs(s.getVelocityY()));
            }

            s.update(elapsedTime);
        }
    }

    private void draw(Graphics2D g) {
        g.drawImage(bgImage, -200, 0, null);
        g.drawImage(bgImage,bgImage.getWidth(null)-200,0,null);

        AffineTransform transform = new AffineTransform();

        for (int i = 0; i < NUM_SPRITES; i++) {
            Sprite sprite = sprites[i];
            transform.setToTranslation(sprite.getX(), sprite.getY());

            if (sprite.getVelocityX() < 0) {
                transform.scale(-1, 1);
                transform.translate(-sprite.getWidth(),0);
            }

//            if (sprite.getVelocityY() < 0) {
//                transform.scale(-1, -1);
//                transform.translate(-sprite.getHeight(),0);
//            }

            g.drawImage(sprite.getImage(),transform,null);
        }
    }
}
