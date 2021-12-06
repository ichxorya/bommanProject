package bommanPkg.Entities.Derived.MovableEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Player extends MovableEntity {
    /**
     * Constants and Variables.
     */
    final int frameWidth = 64;
    final int frameHeight = 64;
    final int rows = 4;
    final int cols = 4;
    protected float frameDuration = 0.2f;

    /**
     * Resource Paths.
     */
    String movingPath = "sprites/players/cirno_moving.png";
    String yeetPath = "sprites/players/cirno_yeet.png";

    /**
     * Animations.
     */
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> moveUp;
    private Animation<TextureRegion> moveDown;
    private Animation<TextureRegion> moveLeft;
    private Animation<TextureRegion> moveRight;
    private Animation<TextureRegion> yeet;

    /**
     * Constructor.
     */
    public Player(float x, float y, Stage s) {
        super(x, y, s);

        setupAnimations();

        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
    }

    private void setupAnimations() {
        yeet = loadAnimationFromSheet(yeetPath, 1, 8, frameDuration, false);

        Texture movement = new Texture(Gdx.files.internal(movingPath), true);
        TextureRegion[][] temp = TextureRegion.split(movement, frameWidth, frameHeight);

        moveUp = setupMovementAnimation(temp, "moveUp");
        moveLeft = setupMovementAnimation(temp, "moveLeft");
        moveDown = setupMovementAnimation(temp, "moveDown");
        moveRight = setupMovementAnimation(temp, "moveRight");

        idle = moveDown;
        setAnimation(idle);
    }

    private Animation<TextureRegion> setupMovementAnimation(TextureRegion[][] temp, String move) {
        Array<TextureRegion> textureArray = new Array<>();

        int direction = getAnimationDirection(move);

        for (int c = 0; c < cols; c++)
            textureArray.add(temp[direction][c]);
        return new Animation<>(frameDuration, textureArray, Animation.PlayMode.LOOP);
    }

    private int getAnimationDirection(String move) {
        switch (move) {
            case "moveUp":
                return 0;
            case "moveLeft":
                return 1;
            case "moveDown":
                return 2;
            case "moveRight":
                return 3;
        }
        return 2;
    }

    public void act(float dt) {
        super.act(dt);

        // IDLE
        if (getSpeed() == 0) {
            setAnimation(idle);
        } else {
            // MOVING
            float angle = getMotionAngle();
            if (angle >= 45 && angle <= 135) {
                setAnimation(moveUp);
            } else if (angle > 135 && angle < 225) {
                setAnimation(moveLeft);
            } else if (angle >= 225 && angle <= 315) {
                setAnimation(moveDown);
            } else {
                setAnimation(moveRight);
            }
        }

        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }
}
