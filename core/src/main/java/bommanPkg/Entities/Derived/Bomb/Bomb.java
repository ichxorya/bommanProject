package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
// TODO
public class Bomb extends Entity {
    private boolean bombExploded;
    Animation<TextureRegion> bombAnimation;

    /**
     * Constructor.
     */
    public Bomb(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        bombAnimation = loadAnimationFromSheet("sprites/bomb/cirno_bomb.png", 1, 4, frameDuration, true);
        bombExploded = false;
        setAnimation(bombAnimation);
    }

    /** DEBUG: VANISH AFTER 2 SECONDS */
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);
        if (getElapsedTime() > 2f) {
            bombExploded = true;
        }

        if (bombExploded) {
            setVisible(false);
//
//            // Remove from stage
//            getStage().getActors().removeValue(this, true);

            generateExplosion();
        }
    }

    /** Generate explosion. */
    private void generateExplosion() {
        new Flame(getX(), getY(), getStage(), getGridPosX(), getGridPosY());
    }

}
