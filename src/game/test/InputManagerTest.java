package game.test;

import game.graphics.Animation;
import game.graphics.Player;
import game.input.GameAction;
import game.input.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class InputManagerTest extends GameCore {

    public static void main(String[] args) {
        new InputManagerTest().run();
    }

    protected GameAction jump;
    protected GameAction exit;
    protected GameAction moveLeft;
    protected GameAction moveRight;
    protected GameAction pause;
    protected InputManager inputManager;
    private Player player;
    private Image bgImage;
    private boolean paused;

    public void init() {
        super.init();
        Window window = screen.getFullScreenWindow();
        inputManager = new InputManager(window);

        createGameActions();
        createSprite();
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean p) {
        if (paused != p) {
            this.paused = p;
            inputManager.resetAllGameActions();
        }
    }

    public void update(long elapsedTime) {
        float ground = 0;
        ground = player.getY() - player.getHeight();
        checkSystemInput();
        System.out.println(ground + " player Y");
        if (!isPaused()) {
            checkGameInput();
            player.update(elapsedTime);
        }
    }

    public void checkSystemInput() {
        if (pause.isPressed()) {
            setPaused(!isPaused());
        }

        if (exit.isPressed()) {
            stop();
        }
    }

    public void checkGameInput() {
        float velocityX = 0;
        if (moveLeft.isPressed()) {
            velocityX -= player.SPEED;
        }

        if (moveRight.isPressed()) {
            velocityX += player.SPEED;
        }

        player.setVelocityX(velocityX);

        if (jump.isPressed()) { // TODO: 11.02.2021  && player.getState() != Player.STATE_JUMPING <= bylo w warunku
            player.jump();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bgImage, 0, 0, null);
        g.drawImage(player.getImage(), Math.round(player.getX()), Math.round(player.getY()), null);
    }

    public void createGameActions() {
        jump = new GameAction("jump", GameAction.DETECT_INITIAL_PRESS_ONLY);
        exit = new GameAction("exit", GameAction.DETECT_INITIAL_PRESS_ONLY);
        moveLeft = new GameAction("move left");
        moveRight = new GameAction("move right");
        pause = new GameAction("pause", GameAction.DETECT_INITIAL_PRESS_ONLY);

        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);
        inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
        inputManager.mapToMouse(jump, InputManager.MOUSE_BUTTON_ONE);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
        inputManager.mapToKey(moveRight, KeyEvent.VK_D);
        inputManager.mapToMouse(moveLeft,InputManager.MOUSE_MOVE_LEFT);
        inputManager.mapToMouse(moveRight,InputManager.MOUSE_MOVE_RIGHT);
    }

    private void createSprite() {
        bgImage = loadImage("images/background.jpg");
        Image player1 = loadImage("images/morda1a.png");
        Image player2 = loadImage("images/morda2a.png");
        Image player3 = loadImage("images/morda3a.png");

        Animation animation = new Animation();

        animation.addFrame(player1, 300);
        animation.addFrame(player2, 360);
        animation.addFrame(player1, 250);
        animation.addFrame(player2, 300);
        animation.addFrame(player3, 400);
        animation.addFrame(player2, 350);

        player = new Player(animation);
        player.setFloorY(screen.getHeight()-player.getHeight());
    }

}
