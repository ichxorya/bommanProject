package bommanPkg.Entities.Derived.LivingEntities.Base;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import bommanPkg.Screens.ScreenPos;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class LivingEntity extends Entity {
    /** Variables. */
    protected int lives;
    protected boolean isDead;
    protected boolean isMoving;
    protected Direction currentDirection;

    /** Enum: Direction. */
    protected enum Direction {
        UP, LEFT, DOWN, RIGHT, NONE;

        // Random Enum value (Direction)
        // Source: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
        private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Direction getRandom() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        // Return Random Direction (not the input direction)
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
            }
            return NONE;
        }
    }

    /**
     * Constructor (grid-map).
     */
    public LivingEntity(float x, float y, Stage s, int gridX, int gridY) {
        super(x, y, s, gridX, gridY);

        isDead = false;
        currentDirection = Direction.DOWN;
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
        if (lives <= 0) {
            isDead = true;
        }

        return isDead;
    }

    /**
     * Method: Die. TODO ???
     */
    public void die() {
        isDead = true;
    }

    /**
     * Setup Value.
     */
    public void setupValues(int lives) {
        this.lives = lives;
    }

    public void resetDirection() {
        currentDirection = Direction.NONE;
    }

    protected Direction getDirection() {
        return currentDirection;
    }

    protected abstract void setDirection(Direction dir);

    /** Valid Direction check. */
    protected boolean validDirection(Direction currentDirection, GameMap gameMap) {
        int temp = 0;
        boolean valid = false;

        switch (currentDirection) {
            case UP:
                temp = gameMap.getGridMap()[getGridPosX()][getGridPosY() - 1];
                break;
            case DOWN:
                temp = gameMap.getGridMap()[getGridPosX()][getGridPosY() + 1];
                break;
            case LEFT:
                temp = gameMap.getGridMap()[getGridPosX() - 1][getGridPosY()];
                break;
            case RIGHT:
                temp = gameMap.getGridMap()[getGridPosX() + 1][getGridPosY()];
                break;
        }

        if (temp != 1 && temp != 2) {
            valid = true;
        }
        return valid;
    }

    /** Movement methods. */
    protected void moveToDirection(Direction currentDirection, GameMap gameMap) {
        switch (currentDirection) {
            case UP:
                moveUp(gameMap);
                break;
            case DOWN:
                moveDown(gameMap);
                break;
            case LEFT:
                moveLeft(gameMap);
                break;
            case RIGHT:
                moveRight(gameMap);
                break;
        }
    }

    public void moveUp(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new ScreenPos(getX(), getY() + gridSize);
        }
        setPosition(getX(), newScreenPos.getY());

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX(), getGridPosY() - 1);
            resetElapsedTime();
        }
    }

    public void moveDown(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new ScreenPos(getX(), getY() - gridSize);;
        }
        setPosition(getX(), newScreenPos.getY());

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX(), getGridPosY() + 1);
            resetElapsedTime();
        }
    }

    public void moveLeft(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new ScreenPos(getX() - gridSize, getY());
        }
        setPosition(newScreenPos.getX(), getY());

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX() - 1, getGridPosY());
            resetElapsedTime();
        }
    }

    public void moveRight(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new ScreenPos(getX() + gridSize, getY());
        }
        setPosition(newScreenPos.getX(), getY());

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX() + 1, getGridPosY());
            resetElapsedTime();
        }
    }

    private boolean movedToNextGrid() {
        switch (currentDirection) {
            case UP:
                if (getY() >= newScreenPos.getY()) {
                    setPosition(newScreenPos.getX(), newScreenPos.getY());
                    oldScreenPos = newScreenPos;
                    return true;
                }
            case DOWN:
                if (getY() <= newScreenPos.getY()) {
                    setPosition(newScreenPos.getX(), newScreenPos.getY());
                    oldScreenPos = newScreenPos;
                    return true;
                }
            case LEFT:
                if (getX() <= newScreenPos.getX()) {
                    setPosition(newScreenPos.getX(), newScreenPos.getY());
                    oldScreenPos = newScreenPos;
                    return true;
                }
            case RIGHT:
                if (getX() >= newScreenPos.getX()) {
                    setPosition(newScreenPos.getX(), newScreenPos.getY());
                    oldScreenPos = newScreenPos;
                    return true;
                }
        }
        return false;
    }
}
