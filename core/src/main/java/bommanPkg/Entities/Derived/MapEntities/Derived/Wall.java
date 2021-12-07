package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wall extends MapEntity {
    /**
     * Constructor.
     */
    public Wall(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("maps/map_resources/wall.png");
        destructible = false;
        passable = false;
    }
}