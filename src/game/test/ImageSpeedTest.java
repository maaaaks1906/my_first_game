package game.test;

import game.graphics.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

public class ImageSpeedTest extends JFrame {
    public static void main(String[] args) {
        DisplayMode displayMode;

        displayMode = new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN);

        ImageSpeedTest test = new ImageSpeedTest();
        test.run(displayMode);
    }

    private static final int FONT_SIZE = 24;
    private static final int TIME_PER_IMAGE = 1500;

    private SimpleScreenManager screen;
    private Image bgImage;
    private Image opaqueImage;
    private Image transparentImage;
    private Image translucentImage;
    private Image antiAliasedImage;
    private boolean imagesLoaded;

    private void run(DisplayMode displayMode) {
        setBackground(Color.blue);
        setForeground(Color.white);
        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        imagesLoaded = false;

        screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            synchronized (this) {
                loadImages();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            screen.restoreScreen();
        }
    }

    private void loadImages() {
        bgImage = loadImage("images/background.jpg");
        opaqueImage = loadImage("images/opaque.png");
        transparentImage = loadImage("images/transparent.png");
        translucentImage = loadImage("images/translucent.png");
        antiAliasedImage = loadImage("images/antialiased.png");
        imagesLoaded = true;
        repaint();
    }

    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    public void paint(Graphics g) {
        // Ustawienie antyaliasingu tekstu:
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        if (imagesLoaded) {
            drawImage(g, opaqueImage, "Nieprzezroczysty");
            drawImage(g, transparentImage, "Przezroczysty");
            drawImage(g, translucentImage, "Przeswiecajacy");
            drawImage(g, antiAliasedImage, "Przeswiecajacy (Antyaliasing)");

            synchronized (this) {
                notify();
            }
        }
        else {
            g.drawString("Ladowanie rysunkow...", 5, FONT_SIZE);
        }
    }

    private void drawImage(Graphics g, Image image, String name) {
        int width = screen.getFullScreenWindow().getWidth() -
                image.getWidth(null);
        int height = screen.getFullScreenWindow().getHeight() -
                image.getHeight(null);
        int numImages = 0;

        g.drawImage(bgImage, 0, 0, null);

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TIME_PER_IMAGE) {
            int x = Math.round((float) Math.random() * width);
            int y = Math.round((float) Math.random() * height);
            g.drawImage(image, x, y, null);
            numImages++;
        }
        long time = System.currentTimeMillis() - startTime;
        float speed = numImages * 1000f / time;
        System.out.println(name + ": " + speed + " rysunki/s");
    }

}
