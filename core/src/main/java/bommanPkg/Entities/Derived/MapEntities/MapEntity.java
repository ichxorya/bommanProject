package bommanPkg.Entities.Derived.MapEntities;

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
}
