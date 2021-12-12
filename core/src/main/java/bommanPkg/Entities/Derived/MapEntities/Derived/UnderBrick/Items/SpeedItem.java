package bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SpeedItem extends Item {

    /**
     * Constructor (grid-map).
     */
    public SpeedItem(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        Animation<TextureRegion> speedItem = loadTexture("items/speedItem.png");
        setAnimation(speedItem);
    }
}
