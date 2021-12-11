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

        // Left
        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.LEFT)) {
                gameMap.add(new Flame(getX() - i * gridSize, getY(), getStage(), getGridPosX() - i, getGridPosY(), false));
            }
        }

        // Right
        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.RIGHT)) {
                gameMap.add(new Flame(getX() + i * gridSize, getY(), getStage(), getGridPosX() + i, getGridPosY(), false));
            }
        }

        // Up
        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.UP)) {
                gameMap.add(new Flame(getX(), getY() + i * gridSize, getStage(), getGridPosX(), getGridPosY() + i, false));
            }
        }

        // Down
        for (int i = 1; i <= flameLength; i++) {
            if (validDirection(gameMap, Direction.DOWN)) {
                gameMap.add(new Flame(getX(), getY() - i * gridSize, getStage(), getGridPosX(), getGridPosY() - i, false));
            }
        }
    }


    /** Is the bomb exploded? */
    public boolean isExploded () {
        return bombExploded;
    }

    private boolean validDirection (GameMap gameMap, Direction dir) {
        int temp = 0;
        boolean valid = false;

        switch (dir) {
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

        if (temp != 1) {
            valid = true;
        }
        return valid;
    }
}