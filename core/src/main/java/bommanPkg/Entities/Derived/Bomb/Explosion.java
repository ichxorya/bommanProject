package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Explosion extends Entity {
    private int length;
    private boolean isDone;
    private Animation<TextureRegion> bombCenter;
    private Animation<TextureRegion> bombUp;
    private Animation<TextureRegion> bombLeft;
    private Animation<TextureRegion> bombDown;
    private Animation<TextureRegion> bombRight;

    /**
     * Constructor.
     */
    public Explosion(float x, float y, Stage s) {
        super(x, y, s);

        setupExplosionAnimations();
        setAnimation(bombCenter);
        length = 3;
    }

    private void setupExplosionAnimations() {
        bombCenter = loadTexture("sprites/bomb/cirno_bomb_center.png");
        bombUp = loadTexture("sprites/bomb/cirno_bomb_hit.png");
        bombLeft = loadTexture("sprites/bomb/cirno_bomb_hit.png");
        bombDown = loadTexture("sprites/bomb/cirno_bomb_hit.png");
        bombRight = loadTexture("sprites/bomb/cirno_bomb_hit.png");
    }

    /**
     * Updates the explosion.
     */
    @Override
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);

    }
}
