package bommanPkg.Entities.Derived.LivingEntities.Base.AI;

import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Baka_AI extends LivingEntity {
    /** Variables. */
    protected Direction nextDirection;

    /**
     * Constructor.
     */
    public Baka_AI(float x, float y, Stage s) {
        super(x, y, s);
    }

    protected abstract void wakeup(float speed, int lives);

    @Override
    public void update(float dt) {

    }

    @Override
    public void move(Direction dir) {
        if (!isDead) {
            if (dir == Direction.UP) {
                setY(getY() + speed);
            } else if (dir == Direction.LEFT) {
                setX(getX() - speed);
            } else if (dir == Direction.DOWN) {
                setY(getY() - speed);
            } else if (dir == Direction.RIGHT) {
                setX(getX() + speed);
            }
        }
    }

    @Override
    public void die() {
        super.die();
        setDirection(Direction.NONE);
    }
}
