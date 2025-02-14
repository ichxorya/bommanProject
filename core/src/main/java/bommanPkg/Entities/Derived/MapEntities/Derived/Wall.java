package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wall extends MapEntity {

    /** Constructor (grid-map). **/
    public Wall(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        loadTexture("maps/map_resources/wall.png");
    }
}
