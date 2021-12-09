package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Portal extends MapEntity {
    private boolean portalActivated;
    Animation<TextureRegion> portalAnimation;

    /**
     * Constructor.
     */
    public Portal(float x, float y, Stage s) {
        super(x, y, s);

        portalAnimation = loadAnimationFromSheet("maps/map_resources/portal.png", 1, 4, 0.1f, true);
        destructible = false;
        portalActivated = false;
        setAnimation(portalAnimation);
    }

    public void activePortal() {
        portalActivated = true;
    }
}
