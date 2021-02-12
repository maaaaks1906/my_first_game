package game.test;

import game.graphics.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

public class ScreenTest extends JFrame {
    public static void main(String[] args) {
        DisplayMode displayMode = new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN);

        ScreenTest screen = new ScreenTest();
        screen.run(displayMode);
    }

    private Image kicia;

    private void run(DisplayMode displayMode) {
        setBackground(Color.blue);
        setForeground(Color.white);
        setFont(new Font("Dialog", Font.PLAIN, 24));

        SimpleScreenManager screenManager = new SimpleScreenManager();

        try {
            screenManager.setFullScreen(displayMode, this);
            kicia = new ImageIcon("images/kicia.jpg").getImage();
            repaint();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            screenManager.restoreScreen();
        }
    }




    public void paint(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        drawImage(g,kicia,0,0,"");
        g.drawString("KOHAM CIEM KICIA", 600, 600);
    }

    private void drawImage(Graphics g, Image image, int x, int y, String caption) {
        g.drawImage(image, x, y, null);
    }


}

