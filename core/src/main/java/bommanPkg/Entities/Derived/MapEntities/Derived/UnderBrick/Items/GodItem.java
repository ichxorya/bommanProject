package bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Items;

import bommanPkg.Entities.Base.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GodItem extends Item {
    /**
     * Constructor (grid-map).
     */
    public GodItem(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        loadTexture("items/godItem.png");
        loadSfx();
    }

    @Override
    public void loadSfx() {
        // Empty sound, because this item is already in Player.
        sfx = Gdx.audio.newSound(Gdx.files.internal("sfxs/superidol_temp.mp3"));
    }
}
