package bommanPkg.Entities.Derived.LivingEntities.Base.AI;

import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Baka_AI extends LivingEntity {
    /** Variables. */

    public Baka_AI(float x, float y, Stage s, int gridPosX, int gridPosY) {
        super(x, y, s, gridPosX, gridPosY);
    }

    protected abstract void wakeup(int lives);

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void act(float dt, GameMap gameMap) {
        super.act(dt, gameMap);
    }
}