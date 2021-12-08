package bommanPkg.Entities.Derived.LivingEntities.Enemies;

import bommanPkg.Entities.Derived.LivingEntities.Base.AI.Baka_AI;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BakaBot extends Baka_AI {
    /** BakaBot Variables. **/

    /**
     * Resource Paths.
     */
    String bakaLive = "sprites/enemies/bakabot_live.png";
    String bakaYeet = "sprites/enemies/bakabot_yeet.png";

    /**
     * Animations.
     */
    private Animation<TextureRegion> live;
    private Animation<TextureRegion> yeet;

    /**
     * Constructor.
     */
    public BakaBot(float x, float y, Stage s) {
        super(x, y, s);

        setupAnimations();
        wakeup(2, 1);
    }

    private void setupAnimations() {
        live = loadAnimationFromSheet(bakaLive, 1, 10, frameDuration * 1.3f, true);
        yeet = loadAnimationFromSheet(bakaYeet, 1, 6, frameDuration, false);
        setAnimation(live);
    }

    @Override
    protected void wakeup(float speed, int lives) {
        this.speed = speed;
        this.lives = lives;

        currentDirection = Direction.getRandom();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isDead()) {
            setAnimation(yeet);
            if (isAnimationFinished()) {
                remove();
            }
        }

        if (getElapsedTime() > 1f) {
            move(currentDirection);
            if (getElapsedTime() > 3f) {
                setDirection(currentDirection);
                resetElapsedTime();
            }
        }

        updateEntity();
    }

    @Override
    protected void setDirection(Direction dir) {
        currentDirection = Direction.getRandom(dir);
    }
}
