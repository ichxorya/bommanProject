package bommanPkg.Entities.Derived.MapEntities.Derived;

import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Warp extends MapEntity {
    /**
     * Constructor (grid-map).
     *
     * @param x
     * @param y
     * @param s
     * @param gridPosX
     * @param gridPosY
     **/
    public Warp(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }


}
