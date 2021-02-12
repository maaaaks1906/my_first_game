package game.test;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class MouseTest extends GameCore implements
        KeyListener,
        MouseMotionListener,
        MouseWheelListener,
        MouseListener
{
    public static void main(String[] args) {
        new MouseTest().run();
    }

    private static final int TRAIL_SIZE = 10;
    private static final Color[] COLORS = {
            Color.white,Color.black, Color.yellow, Color.magenta
    };

    private LinkedList trailList;
    private boolean trailMode;
    private int colorIndex;

    public void init() {
        super.init();
        trailList = new LinkedList();

        Window window = screen.getFullScreenWindow();
        window.addMouseListener(this);
        window.addMouseMotionListener(this);
        window.addMouseWheelListener(this);
        window.addKeyListener(this);
    }


    @Override
    public synchronized void draw(Graphics2D g) {
        int count = trailList.size();

        if (count > 1 && !trailMode) {
            count = 1;
        }

        Window window = screen.getFullScreenWindow();

        g.setColor(window.getBackground());
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(window.getForeground());

        String welcome = "MouseTest. Naciśnij ESCAPE, aby zakończyć";
        window.setFont(new Font("Dialog",Font.PLAIN,50));
        g.drawString(welcome, (screen.getWidth()/2 - 240),50);
        for (int i = 0; i < count; i++) {
            Point p = (Point) trailList.get(i);
            g.drawString("Hello world",p.x, p.y);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        trailMode = !trailMode;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        trailList.addFirst(p);
        while (trailList.size() > TRAIL_SIZE) {
            trailList.removeLast();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        colorIndex = (colorIndex + e.getWheelRotation()) % COLORS.length;

        if (colorIndex < 0) {
            colorIndex += COLORS.length;
        }

        Window window = screen.getFullScreenWindow();
        window.setForeground(COLORS[colorIndex]);
    }
}
