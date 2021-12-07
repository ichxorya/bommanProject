package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Brick extends MapEntity {
    /**
     * Constructor.
     */
    public Brick(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("maps/map_resources/brick.png");
        destructible = true;
        passable = false;
    }
}