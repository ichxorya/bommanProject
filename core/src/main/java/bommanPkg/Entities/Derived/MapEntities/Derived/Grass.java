package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Grass extends MapEntity {
    /**
     * Constructor.
     */
    public Grass(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("maps/map_resources/grass.png");
        destructible = false;
        passable = true;
    }
}
