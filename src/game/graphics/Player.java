package game.graphics;

public class Player extends Sprite {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING =1;

    public static final float SPEED = .3f;
    public static final float GRAVITY = .002f;

    private int floorY;
    private int state;

    public Player(Animation anim) {
        super(anim);
        state = STATE_NORMAL;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setFloorY(int floorY) {
        this.floorY = floorY;
        setY(floorY);
    }

    public void jump() {
        setVelocityY(-1);
        state = STATE_JUMPING;
    }

    public void update(long elapsedTime) {
        if (getState() == STATE_JUMPING) {
            setVelocityY(getVelocityY() + GRAVITY*elapsedTime);
        }

        super.update(elapsedTime);

        if (getState() == STATE_JUMPING && getY() >= floorY) {
            setVelocityY(0);
            setY(floorY);
            setState(STATE_NORMAL);
        }
    }
}
