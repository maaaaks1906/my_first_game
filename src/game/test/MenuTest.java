package game.test;

import game.graphics.NullRepaintManager;
import game.input.GameAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTest extends InputManagerTest implements ActionListener {

    public static void main(String[] args) {
        new MenuTest().run();
    }

    protected GameAction configAction;

    private JButton playButton;
    private JButton configButton;
    private JButton quitButton;
    private JButton pauseButton;
    private JPanel playButtonSpace;

    @Override
    public void init() {
        super.init();
        NullRepaintManager.install();

        configAction = new GameAction("config");

        quitButton = createButton("quit", "koniec");
        playButton = createButton("play", "Kontynuuj");
        pauseButton = createButton("pause", "Pauza");
        configButton = createButton("config", "Ustawienia");

        playButtonSpace = new JPanel();
        playButtonSpace.setOpaque(false);
        playButtonSpace.add(pauseButton);

        JFrame frame = super.screen.getFullScreenWindow();
        Container contentPane = frame.getContentPane();

        if (contentPane instanceof JComponent) {
            ((JComponent)contentPane).setOpaque(false);
        }

        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(playButtonSpace);
        contentPane.add(configButton);
        contentPane.add(quitButton);

        frame.validate();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        JFrame frame = super.screen.getFullScreenWindow();

        frame.getLayeredPane().paintComponents(g);
    }

    public void setPaused(boolean p) {
        super.setPaused(p);
        playButtonSpace.removeAll();
        if (isPaused()) {
            playButtonSpace.add(playButton);
        } else {
            playButtonSpace.add(pauseButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            super.exit.tap();
        } else if (src == configButton) {
            configAction.tap();
        } else if (src == playButton || src == pauseButton) {
            super.pause.tap();
        }
    }

    public JButton createButton(String name, String tooltip) {
        String imagePath = "images/menu/" + name + ".png";
        ImageIcon iconRollover = new ImageIcon(imagePath);
        int w = iconRollover.getIconWidth();
        int h = iconRollover.getIconHeight();

        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        Image image = screen.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);
        g.setComposite(alpha);
        g.drawImage(iconRollover.getImage(), 0, 0, null);
        g.dispose();
        ImageIcon iconDefault = new ImageIcon(image);

        image = screen.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        g = (Graphics2D) image.getGraphics();
        g.drawImage(iconRollover.getImage(), 5, 5, null);
        g.dispose();
        ImageIcon iconPressed = new ImageIcon(image);


        JButton button = new JButton();
        button.addActionListener(this);
        button.setIgnoreRepaint(true);
        button.setFocusable(false);
        button.setToolTipText(tooltip);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setCursor(cursor);
        button.setIcon(iconDefault);
        button.setRolloverIcon(iconRollover);
        button.setPressedIcon(iconPressed);

        return button;
    }
}
