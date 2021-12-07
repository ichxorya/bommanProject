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
    String animationPath = "sprites/enemies/bakabot_animation.png";

    /**
     * Animations.
     */
    private Animation<TextureRegion> animation;

    /**
     * Constructor.
     */
    public BakaBot(float x, float y, Stage s) {
        super(x, y, s);

        setupAnimations();
        wakeup(2, 1);
    }

    private void setupAnimations() {
        animation = loadAnimationFromSheet(animationPath, 1, 10, frameDuration * 1.3f, true);
        setAnimation(animation);
    }

    @Override
    protected void wakeup(float speed, int lives) {
        this.speed = speed;
        this.lives = lives;

        nextDirection = Direction.getRandom();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getElapsedTime() > 1f) {
            move(nextDirection);
            if (getElapsedTime() > 3f) {
                setDirection(currentDirection);
                resetElapsedTime();
            }
        }
    }

    @Override
    public void move(Direction dir) {
        super.move(dir);
    }

    @Override
    protected void setDirection(Direction dir) {
        currentDirection = dir;
        nextDirection = Direction.getRandom();
    }
}
