package game.test;

import game.input.GameAction;
import game.input.InputManager;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;


public class KeyConfigTest extends MenuTest {

    public static void main(String[] args) {
        new KeyConfigTest().run();
    }

    private static final String INSTRUCTIONS =
            "<html>Kliknij pole tekstowe akcji w celu zmiany przypisanych do niego klawiszy." +
                    "<br>Z akcją mogą być skojarzone najwyżej trzy klawisze." +
                    "<br>Naciśnij Backspace, aby skasować klawisze akcji.";

    private JPanel dialog;
    private JButton okButton;
    private List inputs;

    public void init() {
        super.init();

        inputs = new ArrayList<>();
        Border border = BorderFactory.createLineBorder(Color.BLACK);


        /*
        Creates the layout with yellow borders to be added in the middle of the main layout
         */
        JPanel configPanel = new JPanel(new GridLayout(5, 2, 2, 2));
        configPanel.setBorder(border);
        addActionConfig(configPanel, moveLeft);
        addActionConfig(configPanel, moveRight);
        addActionConfig(configPanel, jump);
        addActionConfig(configPanel, pause);
        addActionConfig(configPanel, exit);

        /*
        Creates the button to be placed at the bottom of main layout
         */
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBorder(border);
        okButton = new JButton("OK");
        okButton.setFocusable(false);
        okButton.addActionListener(this);
        bottomPanel.add(okButton);

        /*
        Layout with instructions printed at the top
         */
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBorder(border);
        topPanel.add(new JLabel(INSTRUCTIONS));

        dialog = new JPanel(new BorderLayout());
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(configPanel, BorderLayout.WEST);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setBorder(border);
        dialog.setVisible(false); // <- Makes the config layout invisible when app is launched at first
        dialog.setSize(dialog.getPreferredSize());

        /*
        Sets the position at the screen
         */
        dialog.setLocation(
                (screen.getWidth() - dialog.getWidth()) / 2,
                (screen.getHeight() - dialog.getHeight()) / 2
        );

        screen.getFullScreenWindow().getLayeredPane().add(dialog, JLayeredPane.MODAL_LAYER);
    }

    private void addActionConfig(JPanel configPanel, GameAction action) {
        JLabel label = new JLabel(action.getName(),SwingConstants.CENTER);
        InputComponent input = new InputComponent(action);
        configPanel.add(label);
        configPanel.add(input);
        inputs.add(input);
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == okButton) {
            configAction.tap();
        }
    }

    public void checkSystemInput() {
        super.checkSystemInput();
        if (configAction.isPressed()) {
            boolean show = !dialog.isVisible();
            dialog.setVisible(show);
            setPaused(show);
        }
    }

    private void resetInputs() {
        for (int i = 0; i < inputs.size(); i++) {
            ((InputComponent) inputs.get(i)).setText();
        }
    }

    class InputComponent extends JTextField {
        private GameAction action;

        public InputComponent(GameAction action) {
            this.action = action;
            setText();
            enableEvents(KeyEvent.KEY_EVENT_MASK |
                    MouseEvent.MOUSE_EVENT_MASK |
                    MouseEvent.MOUSE_MOTION_EVENT_MASK |
                    MouseEvent.MOUSE_WHEEL_EVENT_MASK
            );
        }

        private void setText() {
            String text = "";
            List list = inputManager.getMaps(action);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    text += (String) list.get(i) + ", ";
                }
                text = text.substring(0, text.length() - 2);
            }
            synchronized (getTreeLock()) {
                setText(text);
            }
        }

        private void mapGameAction(int code, boolean isMouseMap) {
            if (inputManager.getMaps(action).size() >= 3) {
                inputManager.clearMap(action);
            }
            if (isMouseMap) {
                inputManager.mapToMouse(action, code);
            } else {
                inputManager.mapToKey(action, code);
            }
            resetInputs();
            screen.getFullScreenWindow().requestFocus();
        }

        protected void processKeyEvent(KeyEvent e) {
            if (e.getID() == e.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && inputManager.getMaps(action).size() > 0) {
                    inputManager.clearMap(action);
                    setText("");
                    screen.getFullScreenWindow().requestFocus();
                } else {
                    mapGameAction(e.getKeyCode(), false);
                }
            }
            e.consume();
        }

        protected void processMouseEvent(MouseEvent e) {
            if (e.getID() == e.MOUSE_PRESSED) {
                if (hasFocus()) {
                    int code = InputManager.getMouseButtonCode(e);
                    mapGameAction(code, true);
                } else {
                    requestFocus();
                }
            }
            e.consume();
        }

        protected void processMouseMotionEvent(MouseEvent e) {
            e.consume();
        }

        protected void processMouseWheelEvent(MouseWheelEvent e) {
            if (hasFocus()) {
                int code = InputManager.MOUSE_WHEEL_DOWN;
                if (e.getWheelRotation() < 0) {
                    code = InputManager.MOUSE_WHEEL_UP;
                }
                mapGameAction(code, true);
            }
            e.consume();
        }
    }

}