package bommanPkg.Entities.Derived.MapEntities.Base;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MapEntity extends Entity {
    protected boolean destroyed;

    /** Constructor (grid-map). **/
    public MapEntity(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }

    public static boolean isDestroyed(MapEntity mapEntity) {
        return mapEntity.destroyed;
    }

    public void destroy() {
        destroyed = true;
        setVisible(false);
    }
}
