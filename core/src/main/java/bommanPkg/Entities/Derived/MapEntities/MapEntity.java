package bommanPkg.Entities.Derived.MapEntities;

import bommanPkg.Entities.Base.EntityV2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MapEntity extends EntityV2 {
    /** Variables. **/
    protected boolean destructible;
    protected boolean passable;

    /**
     * Constructor.
     */
    public MapEntity(float x, float y, float width, float height, Stage s) {
        super(x, y, s);
        setSize(width, height);
        setBoundaryRectangle();
    }
}
