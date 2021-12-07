package bommanPkg.Entities.Derived.LivingEntities.Base;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class LivingEntity extends Entity {
    /** Variables. */
    protected float speed;
    protected int lives;
    protected boolean isDead;
    protected boolean isMoving;
    protected Direction currentDirection;

    protected enum Direction {
        NONE, LEFT, RIGHT, UP, DOWN;

        // Random Enum value (Direction)
        // Source: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
        private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Direction getRandom() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        // Opposite Direction
        public Direction getOpposite() {
            switch (this) {
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
            }
            return NONE;
        }
    }

    /**
     * Constructor.
     */
    public LivingEntity(float x, float y, Stage s) {
        super(x, y, s);

        isDead = false;
        isMoving = false;
        currentDirection = Direction.NONE;
    }

    public abstract void update(float dt);

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

    /** Is the entity dead? */
    public boolean isDead() {
        return (isDead || lives <= 0);
    }

    /**
     * Abstract method: Move.
     */
    public abstract void move(Direction dir);

    /**
     * Abstract method: Die.
     */
    public abstract void die();

    /**
     * Abstract method: Setup Value.
     */
    public abstract void setupValue(float speed, int lives);

    /** Prevent Overlap. */
    public void preventOverlapBlock(MapEntity other) {
        if (this.overlaps(other) && !other.isPassable()) {
            switch (getDirection()) {
                case UP:
                    this.setY(this.getY() - speed);
                    break;
                case LEFT:
                    this.setX(this.getX() + speed);
                    break;
                case DOWN:
                    this.setY(this.getY() + speed);
                    break;
                case RIGHT:
                    this.setX(this.getX() - speed);
                    break;
            }
        }
    }

    protected Direction getDirection() {
        return currentDirection;
    }

    protected abstract void setDirection(Direction dir);
}