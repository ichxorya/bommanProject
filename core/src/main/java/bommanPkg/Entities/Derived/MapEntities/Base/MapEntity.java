package bommanPkg.Entities.Derived.MapEntities.Base;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MapEntity extends Entity {
    /** Variables. **/
    protected boolean destructible;

    /**
     * Constructor.
     */
    public MapEntity(float x, float y, Stage s) {
        super(x, y, s);
    }

    /** Constructor (grid-map). **/
    public MapEntity(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }

    /**
     * Getter: destructible.
     */
    public boolean isDestructible() {
        return destructible;
    }

}
