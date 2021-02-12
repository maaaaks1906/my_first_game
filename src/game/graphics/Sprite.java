package game.graphics;

import java.awt.*;

public class Sprite {
    private Animation anim;

    //pozycja duszka na ekranie
    private float x;
    private float y;

    //przyspieszenie duszka d= predkosc w ms/pix
    private float dx;
    private float dy;


    //konstruktor tworzy duszka, ale bez zadnych parametrow
    public Sprite(Animation anim) {
        this.anim = anim;
    }

    /**
     * Aktualizuje obiekt Animation dla bierzacego duszka
     * oraz pozycje na podstawie predkosci
     * @param elapsedTime
     */
    public void update(long elapsedTime) {
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        anim.update(elapsedTime);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    public float getVelocityX() {
        return dx;
    }

    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    public float getVelocityY() {
        return dy;
    }

    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    public Image getImage() {
        return anim.getImage();
    }
}
