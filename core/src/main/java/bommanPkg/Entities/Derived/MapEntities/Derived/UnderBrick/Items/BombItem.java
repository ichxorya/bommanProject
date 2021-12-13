package bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BombItem extends Item {
    /**
     * Constructor (grid-map).
     */
    public BombItem(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);

        Animation<TextureRegion> bombItem = loadTexture("items/bombItem.png");
        setAnimation(bombItem);
        loadSfx();
    }

    @Override
    public void loadSfx() {
        sfx = Gdx.audio.newSound(Gdx.files.internal("sfxs/bombItem.mp3"));
    }
}
