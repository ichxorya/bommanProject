package bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Items;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Item extends MapEntity {
    protected boolean pickedUp;

    /**
     * Constructor (grid-map).
     */
    public Item(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }

    public static boolean isPicked(Item item) {
        return item.pickedUp;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (pickedUp) {
            setVisible(false);
        }
    }

    public void pickedUp() {
        pickedUp = true;
    }
}
