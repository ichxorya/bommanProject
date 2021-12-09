package bommanPkg.Entities.Derived.LivingEntities.Enemies;

import bommanPkg.Entities.Derived.LivingEntities.Base.AI.Baka_AI;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BakaBot extends Baka_AI {
    final static int entityID = 4;
    /**
     * BakaBot Variables.
     */
    float[] oldScreenPos;
    float[] newScreenPos;
    /**
     * Resource Paths.
     */
    String bakaLive = "sprites/enemies/bakabot_live.png";
    String bakaYeet = "sprites/enemies/bakabot_yeet.png";

    /**
     * Animations.
     */
    private Animation<TextureRegion> live;
    private Animation<TextureRegion> yeet;

    /**
     * Constructor.
     */
    public BakaBot(float x, float y, Stage s) {
        super(x, y, s);

        setupAnimations();
        wakeup(2, 1);
    }

    /**
     * Constructor (grid-map).
     */
    public BakaBot(float x, float y, Stage s, int gridX, int gridY) {
        super(x, y, s, gridX, gridY);
        oldScreenPos = new float[]{x, y};
        System.out.println("BakaBot" + getGridPosX() + " " + getGridPosY());
        setupAnimations();
        wakeup(2, 1);
    }

    @Override
    public void update(float dt, GameMap gameMap) {

    }


    private void setupAnimations() {
        live = loadAnimationFromSheet(bakaLive, 1, 10, frameDuration * 1.3f, true);
        yeet = loadAnimationFromSheet(bakaYeet, 1, 6, frameDuration, false);
        setAnimation(live);
    }

    @Override
    protected void wakeup(float speed, int lives) {
        this.speed = speed;
        this.lives = lives;
        this.isMoving = false;

        currentDirection = Direction.getRandom();
    }

    @Override
    public void act(float delta, GameMap gameMap) {
        super.act(delta, gameMap);
        System.out.println("BakaBot: " + getGridPosX() + " " + getGridPosY());

        // New Way
        boolean isValidDirection = validDirection(currentDirection, gameMap);
        if (getElapsedTime() > 0.5f) {
            if (!isValidDirection) {
                resetDirection();
                resetElapsedTime();
            } else {
                isMoving = true;
                moveToDirection(currentDirection, gameMap);
                gameMap.getGridMap()[getGridPosX()][getGridPosY()] = entityID;
            }
        }
    }

    private void moveToDirection(Direction currentDirection, GameMap gameMap) {
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
//        if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
//            moveUp();
//        } else {
//            moveRight();
//        }
    }

    private boolean validDirection(Direction currentDirection, GameMap gameMap) {
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
        if (temp == 0 || temp == 7) {
            valid = true;
        }
        return valid;
    }

    public void moveUp(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new float[]{getX(), getY() + gridSize};
        }
        setPosition(getX(), newScreenPos[1]);

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX(), getGridPosY() - 1);
            resetElapsedTime();
        }
    }

    public void moveDown(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new float[]{getX(), getY() - gridSize};
        }
        setPosition(getX(), newScreenPos[1]);

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX(), getGridPosY() + 1);
            resetElapsedTime();
        }
    }

    public void moveLeft(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new float[]{getX() - gridSize, getY()};
        }
        setPosition(newScreenPos[0], getY());

        if (movedToNextGrid()) {
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
            isMoving = false;
            setGridPos(getGridPosX() - 1, getGridPosY());
            resetElapsedTime();
        }
    }

    public void moveRight(GameMap gameMap) {
        if (isMoving) {
            newScreenPos = new float[]{getX() + gridSize, getY()};
        }
        setPosition(newScreenPos[0], getY());

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
                if (getY() >= newScreenPos[1]) {
                    setPosition(newScreenPos[0], newScreenPos[1]);
                    oldScreenPos = newScreenPos;
                    return true;
                }
            case DOWN:
                if (getY() <= newScreenPos[1]) {
                    setPosition(newScreenPos[0], newScreenPos[1]);
                    oldScreenPos = newScreenPos;
                    return true;
                }
            case LEFT:
                if (getX() <= newScreenPos[0]) {
                    setPosition(newScreenPos[0], newScreenPos[1]);
                    oldScreenPos = newScreenPos;
                    return true;
                }
            case RIGHT:
                if (getX() >= newScreenPos[0]) {
                    setPosition(newScreenPos[0], newScreenPos[1]);
                    oldScreenPos = newScreenPos;
                    return true;
                }
        }
        return false;
    }

    @Override
    protected void setDirection(Direction dir) {
        currentDirection = dir;
    }
}
