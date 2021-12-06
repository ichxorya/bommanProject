package bommanPkg.Entities.Derived;

import bommanPkg.Entities.Base.EntityV2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Solid extends EntityV2 {
    /**
     * Constructor.
     *
     */
    public Solid(float x, float y, float width, float height, Stage s) {
        super(x, y, s);
        setSize(width, height);
        setBoundaryRectangle();
    }
}
