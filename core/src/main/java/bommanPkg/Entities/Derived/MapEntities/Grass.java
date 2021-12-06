package bommanPkg.Entities.Derived.MapEntities;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Grass extends MapEntity {
    /**
     * Constructor.
     */
    public Grass(float x, float y, float width, float height, Stage s) {
        super(x, y, width, height, s);

        loadTexture("maps/map_resources/grass.png");
        destructible = false;
        passable = true;
    }
}
