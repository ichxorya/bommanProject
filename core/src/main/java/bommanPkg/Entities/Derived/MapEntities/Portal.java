package bommanPkg.Entities.Derived.MapEntities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Portal extends MapEntity {
    private boolean portalActivated;
    Animation<TextureRegion> portalAnimation;

    /**
     * Constructor.
     */
    public Portal(float x, float y, float width, float height, Stage s) {
        super(x, y, width, height, s);

        portalAnimation = loadAnimationFromSheet("maps/map_resources/portal.png", 1, 4, 0.1f, true);
        destructible = false;
        passable = true;
        portalActivated = false;
        setAnimation(portalAnimation);
    }

    public void activePortal() {
        portalActivated = true;
    }
}
