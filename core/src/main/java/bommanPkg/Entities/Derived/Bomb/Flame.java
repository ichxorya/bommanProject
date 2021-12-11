package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
// TODO
public class Flame extends Entity {
    private static int length;
    private boolean isDone;
    private Animation<TextureRegion> flameAnimation;

    /**
     * Constructor.
     */
    public Flame(float x, float y, Stage s, GameMap gameMap, int gridPosX, int gridPosY, boolean isCenter) {
        super(x, y, s, gridPosX, gridPosY);

        if (isCenter) {
            flameAnimation = loadTexture("sprites/bomb/cirno_bomb_center.png");
        } else {
            flameAnimation = loadTexture("sprites/bomb/cirno_bomb_hit.png");
        }
        setAnimation(flameAnimation);
        length = 2;
        gameMap.getGridMap()[gridPosX][gridPosY] = -2;
    }

    /**
     * Updates the explosion.
     * Done after 1s.
     */
    @Override
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);

        if (getElapsedTime() > 1f) {
            isDone = true;
        }

        if (isDone) {
            setVisible(false);
            gameMap.getGridMap()[getGridPosX()][getGridPosY()] = 0;
        }
    }

    /** Increase the length of the flame. */
    public static void upgradeFlame() {
        length++;
    }

    /**
     * Returns the length of the flame.
     */
    public static int getLength() {
        return length;
    }

    /**
     * Returns whether the flame is done.
     */
    public boolean isDone() {
        return isDone;
    }
}
