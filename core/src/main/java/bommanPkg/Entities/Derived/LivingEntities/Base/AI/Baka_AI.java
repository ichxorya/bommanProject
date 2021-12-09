package bommanPkg.Entities.Derived.LivingEntities.Base.AI;

import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Baka_AI extends LivingEntity {
    /** Variables. */

    /**
     * Constructor.
     */
    public Baka_AI(float x, float y, Stage s) {
        super(x, y, s);
    }
    public Baka_AI(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }


    protected abstract void wakeup(float speed, int lives);

    @Override
    public void update(float dt, GameMap gameMap) {
        if (isDead()) {
            die();
        }
    }

    @Override
    public void die() {
        super.die();
        setDirection(Direction.NONE);
    }

    @Override
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);
    }
}