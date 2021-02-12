package game.test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class KeyTest extends GameCore implements KeyListener {
    public static void main(String[] args) {
        new KeyTest().run();
    }

    private LinkedList messages = new LinkedList();

    public void init() {
        super.init();

        Window window = screen.getFullScreenWindow();
        //Pozwala na przechwycenie klawiszy do zmiany fokusów np. TAB
        window.setFocusTraversalKeysEnabled(false);
        //Rejestrowanie tego obiektu jako proces nasluchu dla okana
        window.addKeyListener(this);

    }

    private void addMessage(String s) {
        messages.add(s);
        if (messages.size() >= screen.getHeight() / FONT_SIZE) {
            messages.remove(0);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        Window window = screen.getFullScreenWindow();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(window.getBackground());
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());

        g.setColor(window.getForeground());
        int y = FONT_SIZE;
        String welcome = "KeyTest. Naciśnij ESCAPE, aby zakończyć";
        g.drawString(welcome, (screen.getWidth()/2 - (welcome.length()/2)),24);
        for (Object message : messages) {

            g.drawString((String) message, 5, y);
            y += FONT_SIZE;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE) {
            stop();
        } else {
            addMessage("Nacisnieto: " + KeyEvent.getKeyText(keyCode));
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        addMessage("Zwolniono: " + KeyEvent.getKeyText(keyCode));
        e.consume();
    }
}
