package game.test;

import game.graphics.Animation;
import game.graphics.Sprite;
import game.input.GameAction;
import game.input.InputManager;
import game.sound.Filter3d;
import game.sound.FilteredSoundStream;
import game.sound.SimpleSoundPlayer;
import game.util.LoopingByteInputStream;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

public class Filter3dTest extends GameCore {

    public static void main(String[] args) {
        new Filter3dTest().run();
    }

    private Sprite fly;
    private Sprite listener;
    private InputManager inputManager;
    private GameAction exit;
    private SimpleSoundPlayer bzzSound;
    private InputStream bzzSoundStream;

    public void init() {
        super.init();

        exit = new GameAction("Exit", GameAction.DETECT_INITIAL_PRESS_ONLY);
        inputManager = new InputManager(screen.getFullScreenWindow());
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        createSprites();

        bzzSound = new SimpleSoundPlayer("sounds/noww.wav");

        Filter3d filter = new Filter3d(fly, listener, screen.getHeight());

        bzzSoundStream = new FilteredSoundStream(new LoopingByteInputStream(bzzSound.getSamples()), filter);

        //Playing sound in a separate Thread
        new Thread() {
            public void run() {
                bzzSound.play(bzzSoundStream);
            }
        }.start();
    }

    private void createSprites() {
        Image fly1 = loadImage("images/fly1.png");
        Image fly2 = loadImage("images/fly2.png");
        Image fly3 = loadImage("images/fly3.png");
        Image ear = loadImage("images/ear.png");

        Animation anim = new Animation();
        anim.addFrame(fly1,50);
        anim.addFrame(fly2,50);
        anim.addFrame(fly3,50);
        anim.addFrame(fly2, 50);

        fly = new Sprite(anim);

        anim = new Animation();
        anim.addFrame(ear, 0);
        listener = new Sprite(anim);
        listener.setX((screen.getWidth() - listener.getWidth()) / 2);
        listener.setY((screen.getHeight() - listener.getHeight()) / 2);
    }

    public void update(long elapsedTime) {
        if (exit.isPressed()) {
            stop();
        } else {
            listener.update(elapsedTime);
            fly.update(elapsedTime);
            fly.setX(inputManager.getMouseX());
            fly.setY(inputManager.getMouseY());
        }
    }

    public void stop() {
        super.stop();

        try {
            bzzSoundStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0x33cc33));
        g.fillRect(0,0, screen.getWidth(), screen.getHeight());

        g.drawImage(listener.getImage(), Math.round(listener.getX()), Math.round(listener.getY()),null);

        g.drawImage(fly.getImage(),Math.round(fly.getX()),Math.round(fly.getY()),null);
    }
}
