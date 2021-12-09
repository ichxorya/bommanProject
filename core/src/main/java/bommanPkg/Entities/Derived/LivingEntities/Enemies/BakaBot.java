package bommanPkg.Entities.Derived.LivingEntities.Enemies;

import bommanPkg.Entities.Derived.LivingEntities.Base.AI.Baka_AI;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BakaBot extends Baka_AI {
    /**
     * BakaBot Variables.
     **/
    float[] oldScreenPos;
    float[] newScreenPos;
    final static int entityID = 3;

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

        setupAnimations();
        wakeup(2, 1);
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

        currentDirection = Direction.getRandom();
    }

    @Override
    public void act(float delta, GameMap gameMap) {
        super.act(delta, gameMap);
        if (!isMoving) {
            if (validDirection(currentDirection, gameMap)) {
                gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
                moveToDirection(currentDirection);
                gameMap.getGridMap()[getGridPosX()][getGridPosY()] = entityID;
            } else {
                setDirection(Direction.getRandom(currentDirection));
            }
        }
    }

    private void moveToDirection(Direction currentDirection) {
        switch (currentDirection) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
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
        if (temp == 0) {
            valid = true;
        }
        return valid;
    }

    public void moveUp() {
        if (!isMoving) {
            isMoving = true;
            newScreenPos = new float[]{getX(), getY() + gridSize};
        }
        setPosition(getX(), getY() + speed);
        if (movedToNextGrid()) {
            setGridPos(getGridPosX(), getGridPosY() - 1);
            isMoving = false;
        }
    }

    public void moveDown() {
        if (!isMoving) {
            isMoving = true;
            newScreenPos = new float[]{getX(), getY() - gridSize};
        }
        setPosition(getX(), getY() - speed);
        if (movedToNextGrid()) {
            setGridPos(getGridPosX(), getGridPosY() + 1);
            isMoving = false;
        }
    }

    public void moveLeft() {
        if (!isMoving) {
            isMoving = true;
            newScreenPos = new float[]{getX() - gridSize, getY()};
        }
        setPosition(getX() - speed, getY());
        if (movedToNextGrid()) {
            setGridPos(getGridPosX() - 1, getGridPosY());
            isMoving = false;
        }
    }

    public void moveRight() {
        if (!isMoving) {
            isMoving = true;
            newScreenPos = new float[]{getX() + gridSize, getY()};
        }
        setPosition(getX() + speed, getY());
        if (movedToNextGrid()) {
            setGridPos(getGridPosX() + 1, getGridPosY());
            isMoving = false;
        }
    }

    private boolean movedToNextGrid() {
        if (isMoving) {
            switch (currentDirection) {
                case UP:
                    if (getY() >= newScreenPos[1]) {
                        isMoving = false;
                        setPosition(newScreenPos[0], newScreenPos[1]);
                        oldScreenPos = newScreenPos;
                        return true;
                    }
                case DOWN:
                    if (getY() <= newScreenPos[1]) {
                        isMoving = false;
                        setPosition(newScreenPos[0], newScreenPos[1]);
                        oldScreenPos = newScreenPos;
                        return true;
                    }
                case LEFT:
                    if (getX() <= newScreenPos[0]) {
                        isMoving = false;
                        setPosition(newScreenPos[0], newScreenPos[1]);
                        oldScreenPos = newScreenPos;
                        return true;
                    }
                case RIGHT:
                    if (getX() >= newScreenPos[0]) {
                        isMoving = false;
                        setPosition(newScreenPos[0], newScreenPos[1]);
                        oldScreenPos = newScreenPos;
                        return true;
                    }
            }
        }
        return false;
    }

    @Override
    protected void setDirection(Direction dir) {
        currentDirection = Direction.getRandom(dir);
    }
}
