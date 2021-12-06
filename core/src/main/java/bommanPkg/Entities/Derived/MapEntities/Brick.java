package bommanPkg.Entities.Derived.MapEntities;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Brick extends MapEntity {
    /**
     * Constructor.
     */
    public Brick(float x, float y, float width, float height, Stage s) {
        super(x, y, width, height, s);

        loadTexture("maps/map_resources/brick.png");
        destructible = true;
        passable = false;
    }
}
