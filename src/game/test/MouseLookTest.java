package game.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseLookTest extends GameCore implements MouseMotionListener, KeyListener {

    public static void main(String[] args) {
        new MouseLookTest().run();
    }

    private Image bgImage;
    private Robot robot;
    private Point mouseLocation;
    private Point centerLocation;
    private Point imageLocation;
    private boolean relativeMouseMode;
    private boolean isRecentering;
    private Cursor invisibleCursor;

    public void init() {
        super.init();
        mouseLocation = new Point();
        imageLocation = new Point();
        centerLocation = new Point();

        invisibleCursor = Toolkit.getDefaultToolkit().
                createCustomCursor(
                        Toolkit.getDefaultToolkit().
                        getImage(""),
                        new Point(0, 0),
                        "invisible"
                );

        relativeMouseMode = true;
        isRecentering = false;

        try {
            robot = new Robot();
            recenterMouse();
            mouseLocation.x = centerLocation.x;
            mouseLocation.y = centerLocation.y;
        } catch (AWTException e) {
            System.err.println("Nie mozna utworzyc obiektu robot");
        }
        Window window = screen.getFullScreenWindow();
        window.addMouseMotionListener(this);
        window.addKeyListener(this);
        window.setCursor(invisibleCursor);
        bgImage = new ImageIcon("images/background.jpg").getImage();
    }

    private synchronized void recenterMouse() {
        Window window = screen.getFullScreenWindow();
        if (robot != null && window.isShowing()) {
            centerLocation.x = 1920 / 2;
            centerLocation.y = 1080 / 2;
            SwingUtilities.convertPointToScreen(centerLocation, window);
            isRecentering = true;
            robot.mouseMove(centerLocation.x, centerLocation.y);
        }
    }

    @Override
    public synchronized void draw(Graphics2D g) {
        int w = 1920;
        int h = 1080;

        imageLocation.x %= w;
        imageLocation.y %= h;
        if (imageLocation.x < 0) {
            imageLocation.x += w;
        }
        if (imageLocation.y < 0) {
            imageLocation.y += h;
        }

        int x = imageLocation.x;
        int y = imageLocation.y;
        g.drawImage(bgImage, x, y, null);
        g.drawImage(bgImage, x-w, y, null);
        g.drawImage(bgImage, x, y-h, null);
        g.drawImage(bgImage, x-w, y-h, null);

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("Cisnij spacje by zmienic tryb",5, FONT_SIZE);
        g.drawString("Przyds ESC by zakonczyc, hej",5, FONT_SIZE*2);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            stop();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            relativeMouseMode = !relativeMouseMode;
            Cursor normal = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            Window window = screen.getFullScreenWindow();
            window.setCursor(normal);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent e) {
        if (isRecentering && centerLocation.x == e.getX() && centerLocation.y == e.getY()) {
            isRecentering = false;
        } else {
            int dx = e.getX() - mouseLocation.x;
            int dy = e.getY() - mouseLocation.y;
            imageLocation.x += dx;
            imageLocation.y += dy;
            if (relativeMouseMode) {
                recenterMouse();
            }
        }
        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();
    }
}
