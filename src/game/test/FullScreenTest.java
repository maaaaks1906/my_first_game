package game.test;

import game.graphics.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

public class FullScreenTest extends JFrame {
    public static void main(String[] args) {
        DisplayMode displayMode;

        displayMode = new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN);


        FullScreenTest test = new FullScreenTest();
        test.run(displayMode);
    }

    private static final long DEMO_TIME = 5000;

    private void run(DisplayMode displayMode) {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Monospaced", 0, 90));

        SimpleScreenManager screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            try {
                Thread.sleep(DEMO_TIME);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            screen.restoreScreen();
        }
    }

    boolean isInterrupted = true;

    public void paint(Graphics graphics) {
        if (graphics instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        String task = "Text1";
        String task2 = "A teraz text2";


        graphics.drawString(task, 600, 600);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        graphics.drawString(task2, 500, 500);

    }


}
