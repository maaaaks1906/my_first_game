package game.test;

import game.graphics.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

public class ImageTest extends JFrame {
    public static void main(String[] args) {
        DisplayMode displayMode;

        displayMode = new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN);

        ImageTest test = new ImageTest();
        test.run(displayMode);
    }

    private static final int FONT_SIZE = 24;
    private static final int DEMO_TIME = 5000;

    private SimpleScreenManager screen;
    private Image bgImage;
    private Image opaqueImage;
    private Image transparentImage;
    private Image translucentImage;
    private Image antiAliasedImage;
    private boolean imageLoaded;

    private void run(DisplayMode displayMode) {
        setBackground(Color.blue);
        setForeground(Color.white);
        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        imageLoaded = false;

        screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            loadImages();
            try {
                Thread.sleep(DEMO_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        imageLoaded = true;
        // Sygna� przes�any do AWT, pozwalaj�cy na przerysowanie tego okna:
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

        if (imageLoaded) {
            g.drawImage(bgImage, 0, 0, null);
            drawImage(g, opaqueImage, 600, 0, "Nieprzezroczysty");
            drawImage(g, transparentImage, 920, 0, "Przezroczysty");
            drawImage(g, translucentImage, 600, 300, "Przeswiecajacy");
            drawImage(g, antiAliasedImage, 920, 300, "Przeswiecajacy (Antyaliasing)");
        }
        else {
            g.drawString("Ladowanie rysunkow...", 5, FONT_SIZE);
        }
    }

    private void drawImage(Graphics g, Image image, int x, int y, String caption) {
        g.drawImage(image, x, y, null);
        g.drawString(caption, x + 5, y + FONT_SIZE + image.getHeight(null));
    }


}
