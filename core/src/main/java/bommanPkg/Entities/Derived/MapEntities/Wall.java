package bommanPkg.Entities.Derived.MapEntities;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wall extends MapEntity {
    /**
     * Constructor.
     */
    public Wall(float x, float y, float width, float height, Stage s) {
        super(x, y, width, height, s);
        passable = false;
        destructible = false;
    }
}
