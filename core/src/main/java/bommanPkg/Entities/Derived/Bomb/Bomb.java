package bommanPkg.Entities.Derived.Bomb;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Bomb extends Entity {
    private boolean bombExploded;
    Animation<TextureRegion> bombAnimation;

    /**
     * Constructor.
     */
    public Bomb(float x, float y, Stage s) {
        super(x, y, s);

        bombAnimation = loadAnimationFromSheet("sprites/bomb/cirno_bomb.png", 1, 4, frameDuration, true);
        bombExploded = false;
        setAnimation(bombAnimation);
    }

    /** DEBUG: VANISH AFTER 2 SECONDS */
    public void act(float dt) {
        super.act(dt);
        if (getElapsedTime() > 2f) {
            bombExploded = true;
        }

        if (bombExploded) {
            setVisible(false);
            generateExplosion();
        }
    }

    /** Generate explosion. */
    private void generateExplosion() {
        new Explosion(getX(), getY(), getStage());
    }

}