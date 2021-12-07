package bommanPkg.Entities.Derived.LivingEntities;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class LivingEntity extends Entity {
    /** Variables. */
    protected float speed;
    protected int lives;
    protected boolean isDead;

    /**
     * Constructor.
     */
    public LivingEntity(float x, float y, Stage s) {
        super(x, y, s);
        isDead = false;
    }

    /** Setter: speed. */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /** Getter: speed. */
    public float getSpeed() {
        return speed;
    }

    /** Setter: lives. */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /** Getter: lives. */
    public int getLives() {
        return lives;
    }

    /**
     * Abstract method: Move.
     */
    public abstract void move();

    /**
     * Abstract method: Die.
     */
    public abstract void die();

    /** Is the entity dead? */
    public boolean isDead() {
        return (isDead || lives <= 0);
    }
}
