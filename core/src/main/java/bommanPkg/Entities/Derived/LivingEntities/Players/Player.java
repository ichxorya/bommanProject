package bommanPkg.Entities.Derived.LivingEntities.Players;

import bommanPkg.Entities.Derived.Bomb.Bomb;
import bommanPkg.Entities.Derived.Bomb.Flame;
import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Player extends LivingEntity {
    /**
     * Player Variables.
     **/
    private final int entityID = 7;
    private int currentBomb = 1;
    private int currentMaxBombs = 1;
    private boolean pressedBombKey = false;
    private boolean isBombPlanted;

    /** Sound Variables **/
    private Music HolyMusic;
    private Sound bombSound1;
    private Sound bombSound2;

    // The smaller the 'idkSpeed', the faster the player moves.
    float idkSpeed = 0.6f;

    /**
     * Animations.
     */
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> moveUp;
    private Animation<TextureRegion> moveDown;
    private Animation<TextureRegion> moveLeft;
    private Animation<TextureRegion> moveRight;
    private Animation<TextureRegion> dead;
    private int flameLength = 1;
    private boolean isGod;

    /**
     * Constructor (grid-map).
     */
    public Player(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        // Debug
        isInvincible = false;

        setupPlayerAnimations();
        setupSound();
    }

    private void setupSound() {
        HolyMusic = Gdx.audio.newMusic(Gdx.files.internal("sfxs/superidol.mp3"));
        bombSound1 = Gdx.audio.newSound(Gdx.files.internal("sfxs/bomb1.mp3"));
        bombSound2 = Gdx.audio.newSound(Gdx.files.internal("sfxs/bomb2.mp3"));
    }

    /**
     * Deadly move.
     */
    @Override
    protected boolean touchedByDeath(GameMap gameMap) {
        return gameMap.getGridMap()[getGridPosX()][getGridPosY()] == 4
                || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == 5
                || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == 6
                || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == -2;
    }

    @Override
    protected void setDirection(Direction dir) {
        this.currentDirection = dir;
    }

    private void setupPlayerAnimations() {
        String deadPath = "sprites/players/cirno_dead.png";
        dead = loadAnimationFromSheet(deadPath, 1, 8, frameDuration * 1.6f, false);

        String movingPath = "sprites/players/cirno_moving.png";
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

        if (!isDead) {
            if (touchedByDeath(gameMap) && (!isInvincible || !isGod)) {
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
                        int itemID = thisTileHasItem(gameMap);
                        if (itemID != 0) {
                            pickUpItem(gameMap, itemID);
                        }
                        gameMap.getGridMap()[getGridPosX()][getGridPosY()] = entityID;
                    }
                }
            }
        } else {
            setAnimation(dead);
        }

//        debugPrintPlayer();
    }

    private void debugPrintPlayer() {
        System.out.println("Player: " + getGridPosX() + ", " + getGridPosY());
        System.out.println("Speed: " + idkSpeed);
        System.out.println("currentMaxBombs: " + currentMaxBombs);
        System.out.println("currentBombRange: " + flameLength);
    }

    private void pickUpItem(GameMap gameMap, int itemID) {
        switch (itemID) {
            case 80:
                if (idkSpeed > 0.3f) {
                    idkSpeed -= 0.1f;
                }
                break;
            case 90:
                currentMaxBombs++;
                break;
            case 100:
                flameLength++;
                Flame.setLength(flameLength);
                break;
            case 110:
                System.out.println("REDACTED");
                break;
            case 120:
                // ULTIMATE POWER
                isInvincible = true;
                superIdolCountdown();
                break;
        }
        gameMap.pickedUpItem(getGridPosX(), getGridPosY());
    }

    private void superIdolCountdown() {
        if (!isGod) {
            HolyMusic.play();
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isInvincible = false;
            }
        }, 14);
    }

    // Set god mode
    public void i_am_god_ok() {
        HolyMusic.setVolume(0f);
        isInvincible = true;
        isGod = true;
    }

    private int thisTileHasItem(GameMap gameMap) {
        int itemID = gameMap.getGridMap()[getGridPosX()][getGridPosY()];

        if (itemID == 80 || itemID == 90 || itemID == 100 || itemID == 110 || itemID == 120) {
            return itemID;
        }
        return 0;
    }

    private void getBombInfo(GameMap gameMap) {
        if (gameMap.getBombList().size() == 0) {
            currentBomb = currentMaxBombs;
        }
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
            if (!isBombPlanted) {
                bombSound1.play();
                isBombPlanted = true;
            } else {
                bombSound2.play();
                isBombPlanted = false;
            }
        }

        pressedBombKey = false;
    }

    private void setBomb(GameMap gameMap) {
        if (currentBomb > 0 && !pressedBombKey && validBombTile(gameMap)) {
            pressedBombKey = true;
            currentBomb--;
            Bomb bomb = new Bomb(getX(), getY(), this.getStage(), getGridPosX(), getGridPosY(), flameLength);
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = -1;    // Bomb ID
            gameMap.add(bomb);
        }
    }

    private boolean validBombTile(GameMap gameMap) {
        return !(gameMap.getGridMap()[getGridPosX()][getGridPosY()] == -1);
    }
}
