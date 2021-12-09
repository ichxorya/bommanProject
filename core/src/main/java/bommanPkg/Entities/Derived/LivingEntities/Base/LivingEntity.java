package bommanPkg.Entities.Derived.LivingEntities.Base;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
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
    protected Direction currentDirection;

    protected enum Direction {
        LEFT, RIGHT, UP, DOWN;

        // Random Enum value (Direction)
        // Source: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
        private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Direction getRandom() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        public static Direction getRandom(Direction dir) {
            Direction newDirection = getRandom();
            while (newDirection == dir) {
                newDirection = getRandom();
            }
            return newDirection;
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
                default:
                    throw new RuntimeException("Invalid Direction");
            }
        }
    }

    /**
     * Constructor.
     */
    public LivingEntity(float x, float y, Stage s) {
        super(x, y, s);

        isDead = false;
        currentDirection = Direction.DOWN;
    }
    /**
     * Constructor (grid-map).
     */
    public LivingEntity(float x, float y, Stage s, int gridX, int gridY) {
        super(x, y, s, gridX, gridY);

        isDead = false;
        currentDirection = Direction.DOWN;
    }

    public abstract void update(float dt, GameMap gameMap);

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
     * Abstract method: Die.
     */
    public void die() {
        isDead = true;
    }

    /**
     * Setup Value.
     */
    public void setupValues(float speed, int lives) {
        this.speed = speed;
        this.lives = lives;
    }

    public void resetDirection() {
        currentDirection = Direction.getRandom();
    }

    protected Direction getDirection() {
        return currentDirection;
    }

    protected abstract void setDirection(Direction dir);
}
