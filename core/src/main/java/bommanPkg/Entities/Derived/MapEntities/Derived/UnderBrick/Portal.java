package bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Portal extends MapEntity {
    private static boolean portalActivated;
    Animation<TextureRegion> portalAnimation;

    /**
     * Constructor (grid-map).
     */
    public Portal(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        portalAnimation = loadAnimationFromSheet("maps/map_resources/portal.png", 1, 4, frameDuration, true);
        setAnimation(portalAnimation);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
