package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import squidpony.squidgrid.Direction;

// TODO
public class Bomb extends Entity {
    private boolean bombExploded;

    /**
     * Constructor.
     */
    public Bomb(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        Animation<TextureRegion> bombAnimation = loadAnimationFromSheet("sprites/bomb/cirno_bomb.png", 1, 4, frameDuration, true);
        bombExploded = false;
        setAnimation(bombAnimation);
    }

    /** DEBUG: VANISH AFTER 1.69 SECONDS */
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);
        if (getElapsedTime() > 1.69f) {
            bombExploded = true;
        }

        if (bombExploded) {
            setVisible(false);

            generateExplosion(Flame.getLength(), gameMap);
        }
    }

    /** Generate explosion. */
    private void generateExplosion(int flameLength, GameMap gameMap) {
        // Center of the explosion
        gameMap.add(new Flame(getX(), getY(), getStage(), getGridPosX(), getGridPosY(), true));

        int[] flameLengthByDirection = getFlameLengthByDirection(flameLength, gameMap);

        // Left
        for (int i = 1; i <= flameLengthByDirection[1]; i++) {
            gameMap.add(new Flame(getX() - i * gridSize, getY(), getStage(), getGridPosX() - i, getGridPosY(), false));
        }

        // Right
        for (int i = 1; i <= flameLengthByDirection[3]; i++) {
            gameMap.add(new Flame(getX() + i * gridSize, getY(), getStage(), getGridPosX() + i, getGridPosY(), false));
        }

        // Up
        for (int i = 1; i <= flameLengthByDirection[0]; i++) {
            gameMap.add(new Flame(getX(), getY() + i * gridSize, getStage(), getGridPosX(), getGridPosY() + i, false));
        }

        // Down
        for (int i = 1; i <= flameLengthByDirection[2]; i++) {
            gameMap.add(new Flame(getX(), getY() - i * gridSize, getStage(), getGridPosX(), getGridPosY() - i, false));
        }
    }

    private int[] getFlameLengthByDirection(int flameLength, GameMap gameMap) {
        int[] flameDir = new int[4];
        flameDir[0] = getFlameLengthUp(flameLength, gameMap);
        flameDir[1] = getFlameLengthLeft(flameLength, gameMap);
        flameDir[2] = getFlameLengthDown(flameLength, gameMap);
        flameDir[3] = getFlameLengthRight(flameLength, gameMap);
        return flameDir;
    }

    private int getFlameLengthRight(int flameLength, GameMap gameMap) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.RIGHT, i)) {
                maxLength = i;
            } else {
                break;
            }
        }

        return maxLength;
    }

    private int getFlameLengthDown(int flameLength, GameMap gameMap) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.DOWN, i)) {
                maxLength = i;
            } else {
                break;
            }
        }

        return maxLength;
    }

    private int getFlameLengthLeft(int flameLength, GameMap gameMap) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.LEFT, i)) {
                maxLength = i;
            } else {
                break;
            }
        }

        return maxLength;
    }

    private int getFlameLengthUp(int flameLength, GameMap gameMap) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.UP, i)) {
                maxLength = i;
            } else {
                break;
            }
        }

        return maxLength;
    }


    /** Is the bomb exploded? */
    public boolean isExploded () {
        return bombExploded;
    }

    private boolean validDirection (GameMap gameMap, Direction dir, int pos) {
        int temp = 0;
        boolean valid = false;

        switch (dir) {
            case UP:
                temp = gameMap.getGridMap()[getGridPosX()][getGridPosY() - pos];
                break;
            case DOWN:
                temp = gameMap.getGridMap()[getGridPosX()][getGridPosY() + pos];
                break;
            case LEFT:
                temp = gameMap.getGridMap()[getGridPosX() - pos][getGridPosY()];
                break;
            case RIGHT:
                temp = gameMap.getGridMap()[getGridPosX() + pos][getGridPosY()];
                break;
        }

        if (temp != 1) {
            valid = true;
        }
        return valid;
    }
}