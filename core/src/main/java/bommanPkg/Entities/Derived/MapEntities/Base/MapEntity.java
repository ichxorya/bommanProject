package bommanPkg.Entities.Derived.MapEntities.Base;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MapEntity extends Entity {
    /** Variables. **/
    protected boolean destructible;
    protected boolean passable;

    /**
     * Constructor.
     */
    public MapEntity(float x, float y, Stage s) {
        super(x, y, s);
        setCollisionBounds();
    }

    /**
     * Getter: destructible.
     */
    public boolean isDestructible() {
        return destructible;
    }

    /**
     * Getter: passable.
     */
    public boolean isPassable() {
        return passable;
    }

}
