package bommanPkg.Entities.Derived.MovableEntities;

import bommanPkg.Entities.Base.EntityV2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MovableEntity extends EntityV2 {

    /**
     * Constructor.
     */
    public MovableEntity(float x, float y, Stage s) {
        super(x, y, s);
    }

}
