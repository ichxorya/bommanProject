package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
// TODO
public class Flame extends Entity {
    private int length;
    private boolean isDone;
    private Animation<TextureRegion> bombCenter;
    private Animation<TextureRegion> bombFlame;

    /**
     * Constructor.
     */
    public Flame(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        setupExplosionAnimations();
        setAnimation(bombCenter);
        length = 1;
    }

    private void setupExplosionAnimations() {
        bombCenter = loadTexture("sprites/bomb/cirno_bomb_center.png");
        bombFlame = loadTexture("sprites/bomb/cirno_bomb_hit.png");
    }

    /**
     * Updates the explosion.
     */
    @Override
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);

    }
}
