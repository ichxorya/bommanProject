package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

// TODO
public class Bomb extends Entity {
    private boolean bombExploded;
    private int[] flameDir;
    private boolean[] stopCheckDir;

    /**
     * Constructor.
     */
    public Bomb(float x, float y, Stage s, int gridPosX, int gridPosY, int flameLength) {
        super(x, y, s, gridPosX, gridPosY);
        Flame.setLength(flameLength);

        Animation<TextureRegion> bombAnimation = loadAnimationFromSheet("sprites/bomb/cirno_bomb.png", 1, 4, frameDuration, true);
        bombExploded = false;
        setAnimation(bombAnimation);
    }

    /** BOOM BOOM AFTER 1.69 SECONDS OR IF THE BOMB-TILE IS OCCUPIED BY A FLAME TILE */
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);
        if (
                getElapsedTime() > 1.69f
                        || gameMap.getGridMap()[getGridPosX()][getGridPosY()] == -2
        ) {
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
        gameMap.add(new Flame(getX(), getY(), getStage(), gameMap, getGridPosX(), getGridPosY(), true));

        int[] flameLengthByDirection = getFlameLengthByDirection(flameLength, gameMap);

        // Left
        for (int i = 1; i <= flameLengthByDirection[1]; i++) {
            if (getGridPosX() - i < 0) {
                break;
            }

            if (stopCheckDir[1] && i == flameLengthByDirection[1]) {
                gameMap.mapDestroyWall(getGridPosX() - i, getGridPosY());
            }

            gameMap.add(new Flame(getX() - i * gridSize, getY(), getStage(), gameMap, getGridPosX() - i, getGridPosY(), false));

        }

        // Right
        for (int i = 1; i <= flameLengthByDirection[3]; i++) {
            if (getGridPosX() + i >= gameMap.getHorizontalBlocks()) {
                break;
            }

            if (stopCheckDir[3] && i == flameLengthByDirection[3]) {
                gameMap.mapDestroyWall(getGridPosX() + i, getGridPosY());
            }

            gameMap.add(new Flame(getX() + i * gridSize, getY(), getStage(), gameMap, getGridPosX() + i, getGridPosY(), false));
        }

        // Up
        for (int i = 1; i <= flameLengthByDirection[0]; i++) {
            if (getGridPosY() - i < 0) {
                break;
            }

            if (stopCheckDir[0] && i == flameLengthByDirection[0]) {
                gameMap.mapDestroyWall(getGridPosX(), getGridPosY() - i);
            }

            gameMap.add(new Flame(getX(), getY() + i * gridSize, getStage(), gameMap, getGridPosX(), getGridPosY() - i, false));
        }

        // Down
        for (int i = 1; i <= flameLengthByDirection[2]; i++) {
            if (getGridPosY() + i >= gameMap.getVerticalBlocks()) {
                break;
            }

            if (stopCheckDir[2] && i == flameLengthByDirection[2]) {
                gameMap.mapDestroyWall(getGridPosX(), getGridPosY() + i);
            }

            gameMap.add(new Flame(getX(), getY() - i * gridSize, getStage(), gameMap, getGridPosX(), getGridPosY() + i, false));
        }
    }

    private int[] getFlameLengthByDirection(int flameLength, GameMap gameMap) {
        flameDir = new int[4];
        stopCheckDir = new boolean[4];

        flameDir[0] = getFlameLengthUp(flameLength, gameMap, stopCheckDir);
        flameDir[1] = getFlameLengthLeft(flameLength, gameMap, stopCheckDir);
        flameDir[2] = getFlameLengthDown(flameLength, gameMap, stopCheckDir);
        flameDir[3] = getFlameLengthRight(flameLength, gameMap, stopCheckDir);
        return flameDir;
    }

    private int getFlameLengthRight(int flameLength, GameMap gameMap, boolean[] stopCheckDir) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, 3, i, stopCheckDir) && !stopCheckDir[3]) {
                maxLength = i;
            } else {
                if (stopCheckDir[3]) {
                    maxLength += 1;
                }
                break;
            }
        }

        return maxLength;
    }

    private int getFlameLengthDown(int flameLength, GameMap gameMap, boolean[] flameDir) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, 2, i, flameDir) && !stopCheckDir[2]) {
                maxLength = i;
            } else {
                if (stopCheckDir[2]) {
                    maxLength += 1;
                }
                break;
            }
        }

        return maxLength;
    }

    private int getFlameLengthLeft(int flameLength, GameMap gameMap, boolean[] flameDir) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, 1, i, flameDir) && !stopCheckDir[1]) {
                maxLength = i;
            } else {
                if (stopCheckDir[1]) {
                    maxLength += 1;
                }
                break;
            }
        }

        return maxLength;
    }

    private int getFlameLengthUp(int flameLength, GameMap gameMap, boolean[] flameDir) {
        int maxLength = 0;

        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, 0, i, flameDir) && !stopCheckDir[0]) {
                maxLength = i;
            } else {
                if (stopCheckDir[0]) {
                    maxLength += 1;
                }
                break;
            }
        }

        return maxLength;
    }


    /** Is the bomb exploded? */
    public boolean isExploded () {
        return bombExploded;
    }

    /** Valid Direction check for Bomb. */
    private boolean validDirection(GameMap gameMap, int dir, int pos, boolean[] stopCheckDir) {
        int temp = 0;
        boolean valid = false;

        switch (dir) {
            case 0:
                if (getGridPosY() - pos < 0) {
                    return false;
                }
                temp = gameMap.getGridMap()[getGridPosX()][getGridPosY() - pos];

                break;
            case 2:
                if (getGridPosY() + pos >= gameMap.getVerticalBlocks()) {
                    return false;
                }
                temp = gameMap.getGridMap()[getGridPosX()][getGridPosY() + pos];

                break;
            case 1:
                if (getGridPosX() - pos < 0) {
                    return false;
                }
                temp = gameMap.getGridMap()[getGridPosX() - pos][getGridPosY()];

                break;
            case 3:
                if (getGridPosX() + pos >= gameMap.getHorizontalBlocks()) {
                    return false;
                }
                temp = gameMap.getGridMap()[getGridPosX() + pos][getGridPosY()];

                break;
        }

        if (temp != 1) {
            valid = true;
            if (temp == 2 || temp == 3 || temp == 8 || temp == 9 || temp == 10 || temp == 11 || temp == 12) {
                stopCheckDir[dir] = true;
                return false;
            }
        }
        return valid;
    }
}