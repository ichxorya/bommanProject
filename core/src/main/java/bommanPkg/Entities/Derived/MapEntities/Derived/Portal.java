package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Portal extends MapEntity {
    private boolean portalActivated;
    Animation<TextureRegion> portalAnimation;

    /**
     * Constructor (grid-map).
     */
    public Portal(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }

    public void activePortal() {
        portalActivated = true;
    }
}
