package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;
// TODO : Brick -> Item under brick | Item (-X> Brick)

public class Brick extends MapEntity {

    public Brick(int entityPosX, int entityPosY, Stage mainStage, int x, int y) {
        super(entityPosX, entityPosY, mainStage, x, y);

        loadTexture("maps/map_resources/brick.png");
        destructible = true;
    }
}
