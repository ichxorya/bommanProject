package bommanPkg.Entities.Derived.LivingEntities.Players;

import bommanPkg.Entities.Derived.Bomb.Bomb;
import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Player extends LivingEntity implements InputProcessor {
    /**
     * Player Variables.
     **/
    private final int maxBombs = 3;
    private final int entityID = 7;
    private int currentBomb = 3;
    private int currentMaxBombs = 3;
    private boolean pressedBombKey = false;

    /**
     * Resource Paths.
     */
    private final String movingPath = "sprites/players/cirno_moving.png";
    private final String deadPath = "sprites/players/cirno_dead.png";

    /**
     * Animations.
     */
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> moveUp;
    private Animation<TextureRegion> moveDown;
    private Animation<TextureRegion> moveLeft;
    private Animation<TextureRegion> moveRight;
    private Animation<TextureRegion> dead;

    /**
     * Constructor (grid-map).
     */
    public Player(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        setupPlayerAnimations();
        setupValues(1);
    }

    @Override
    protected void setDirection(Direction dir) {
        this.currentDirection = dir;
    }

    @Override
    public void die() {
        isDead = true;
    }

    private void setupPlayerAnimations() {
        dead = loadAnimationFromSheet(deadPath, 1, 8, frameDuration * 1.6f, false);

        Texture movement = new Texture(Gdx.files.internal(movingPath), true);
        TextureRegion[][] temp = TextureRegion.split(movement, gridSize, gridSize);

        moveUp = setupMovementAnimation(temp, Direction.UP);
        moveLeft = setupMovementAnimation(temp, Direction.LEFT);
        moveDown = setupMovementAnimation(temp, Direction.DOWN);
        moveRight = setupMovementAnimation(temp, Direction.RIGHT);

        idle = moveDown;
        setAnimation(idle);
    }

    private Animation<TextureRegion> setupMovementAnimation(TextureRegion[][] temp, Direction dir) {
        Array<TextureRegion> textureArray = new Array<>();

        int direction = getAnimationDirection(dir);

        for (int column = 0; column < 4; column++)
            textureArray.add(temp[direction][column]);
        return new Animation<>(frameDuration, textureArray, Animation.PlayMode.LOOP);
    }

    private int getAnimationDirection(Direction dir) {
        switch (dir) {
            case UP:
                return 0;
            case LEFT:
                return 1;
            case DOWN:
                return 2;
            case RIGHT:
                return 3;
        }
        return 2;   // Default to down
    }

    @Override
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);
        getBombInfo(gameMap);

        // The smaller the 'idkSpeed', the faster the player moves.
        float idkSpeed = 0.5f;

        if (!isDead) {
            if (touchedByDeath(gameMap)) {
                die();
            }
            getDirectionFromInput(gameMap);
            boolean isValidDirection = validDirection(currentDirection, gameMap);
            setAnimationFromDirection(currentDirection);
            if (getElapsedTime() > idkSpeed) {
                if (currentDirection == Direction.NONE) {
                    setAnimation(idle);
                } else if (isValidDirection) {
                    isMoving = true;
                    moveToDirection(currentDirection, gameMap);

                    if (!touchedByDeath(gameMap)) {
                        gameMap.getGridMap()[getGridPosX()][getGridPosY()] = entityID;
                    }
                } else {
                    System.out.println("Invalid Direction " + currentDirection);
                }
            }
        } else {
            setAnimation(dead);
        }

    }

    private void getBombInfo(GameMap gameMap) {
        if (gameMap.getBombList().size() == 0) {
            currentBomb = currentMaxBombs;
        }
    }

    // TODO: If it works like a charm, should be implemented in the super class.
    private boolean touchedByDeath(GameMap gameMap) {
        return gameMap.getGridMap()[getGridPosX()][getGridPosY()] == 4
                || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == 5
                || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == 6
                || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == -2;
    }

    private void setAnimationFromDirection(Direction currentDirection) {
        switch (currentDirection) {
            case UP:
                setAnimation(moveUp);
                break;
            case LEFT:
                setAnimation(moveLeft);
                break;
            case DOWN:
                setAnimation(moveDown);
                break;
            case RIGHT:
                setAnimation(moveRight);
                break;
        }
    }

    private void getDirectionFromInput(GameMap gameMap) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentDirection = Direction.UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentDirection = Direction.DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentDirection = Direction.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentDirection = Direction.RIGHT;
        } else {
            currentDirection = Direction.NONE;

            getActionFromInput(gameMap);
        }
    }

    private void getActionFromInput(GameMap gameMap) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            setBomb(gameMap);
        }

        pressedBombKey = false;
    }

    private void setBomb(GameMap gameMap) {
        System.out.println("U HAVE " + currentBomb + " BOMBS LEFT");
        if (currentBomb > 0 && !pressedBombKey && validBombTile(gameMap)) {
            pressedBombKey = true;
            currentBomb--;
            Bomb bomb = new Bomb(getX(), getY(), this.getStage(), getGridPosX(), getGridPosY());
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = -1;
            gameMap.add(bomb);
        }
    }

    private boolean validBombTile(GameMap gameMap) {
        return !(gameMap.getGridMap()[getGridPosX()][getGridPosY()] == -1);
    }

    // InputProcessor required methods
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
