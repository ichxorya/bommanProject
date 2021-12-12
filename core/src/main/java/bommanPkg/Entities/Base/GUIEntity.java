package bommanPkg.Entities.Base;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class GUIEntity extends Entity {
    public GUIEntity(int x, int y, Stage s, int w, int h) {
        super(x, y, s, 0, 0);

        // Self
        setSize(w, h);
    }

    /**
     * Set position at center.
     */
    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }
}
