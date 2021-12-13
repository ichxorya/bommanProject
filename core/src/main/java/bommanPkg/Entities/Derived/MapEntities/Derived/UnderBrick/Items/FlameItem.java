package bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Items;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class FlameItem extends Item {

    /**
     * Constructor (grid-map).
     */
    public FlameItem(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
        Animation<TextureRegion> flameItem = loadTexture("items/flameItem.png");
        setAnimation(flameItem);
    }
}
