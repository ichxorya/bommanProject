package bommanPkg.Entities.Derived.LivingEntities.Players;

import bommanPkg.Entities.Derived.Bomb.Bomb;
import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.Input.Keys.*;

public class Player extends LivingEntity {
    /** Player Variables. **/
    private final int maxBombs = 1;
    private int bomb;

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

        setupPlayerAnimations();
        setupValue(3, 1);
        bomb = 0;
    }

    @Override
    public void setupValue(float speed, int lives) {
        this.speed = speed;
        this.lives = lives;
    }

    @Override
    protected void setDirection(Direction dir) {
        this.currentDirection = dir;
    }

    @Override
    public void update(float dt) {
        getInput();
        setBomb();
        suicide();
        updateEntity();
    }

    private void getInput() {
        if (Gdx.input.isKeyPressed(UP)) {
            move(Direction.UP);
            setDirection(Direction.UP);
        } else if (Gdx.input.isKeyPressed(LEFT)) {
            move(Direction.LEFT);
            setDirection(Direction.LEFT);
        } else if (Gdx.input.isKeyPressed(DOWN)) {
            move(Direction.DOWN);
            setDirection(Direction.DOWN);
        } else if (Gdx.input.isKeyPressed(RIGHT)) {
            move(Direction.RIGHT);
            setDirection(Direction.RIGHT);
        } else {
            if (!isDead) {
                setAnimation(idle);
                setDirection(Direction.NONE);
            }
        }

        if (Gdx.input.isKeyPressed(Z)) {
            isDead = false;
        }

        if (Gdx.input.isKeyPressed(N)) {
            speed++;
        }
    }

    @Override
    public void move(Direction dir) {
        if (!isDead) {
            if (dir == Direction.UP) {
                setAnimation(moveUp);
                setY(getY() + speed);
            } else if (dir == Direction.LEFT) {
                setAnimation(moveLeft);
                setX(getX() - speed);
            } else if (dir == Direction.DOWN) {
                setAnimation(moveDown);
                setY(getY() - speed);
            } else if (dir == Direction.RIGHT) {
                setAnimation(moveRight);
                setX(getX() + speed);
            }
        }
    }

    @Override
    public void die() {
        setAnimation(yeet);
        isDead = true;
    }

    /** Player: Set Bomb. */
    public void setBomb() {
        if (bomb < maxBombs && Gdx.input.isKeyPressed(B)) {
            new Bomb(getX(), getY(), getStage());
            bomb++;
        }
    }

    /** Debug: Suicide. */
    public void suicide() {
        if (Gdx.input.isKeyPressed(X)) {
            die();
        }
    }

    private void setupPlayerAnimations() {
        yeet = loadAnimationFromSheet(yeetPath, 1, 8, frameDuration * 1.5f, false);

        Texture movement = new Texture(Gdx.files.internal(movingPath), true);
        TextureRegion[][] temp = TextureRegion.split(movement, gridSize, gridSize);

        moveUp = setupMovementAnimation(temp, Direction.UP);
        moveLeft = setupMovementAnimation(temp, Direction.LEFT);
        moveDown = setupMovementAnimation(temp, Direction.DOWN);
        moveRight = setupMovementAnimation(temp, Direction.RIGHT);

        idle = moveDown;
        setAnimation(idle);
    }

    private Animation<TextureRegion> setupMovementAnimation(TextureRegion[][] temp, Direction dir) {
        Array<TextureRegion> textureArray = new Array<>();

        int direction = getAnimationDirection(dir);

        for (int column = 0; column < 4; column++)
            textureArray.add(temp[direction][column]);
        return new Animation<>(frameDuration, textureArray, Animation.PlayMode.LOOP);
    }

    private int getAnimationDirection(Direction dir) {
        switch (dir) {
            case UP:
                return 0;
            case LEFT:
                return 1;
            case DOWN:
                return 2;
            case RIGHT:
                return 3;
        }
        return 2;   // Default to down
    }

    public void act(float dt) {
        super.act(dt);

        // IDLE
//        if (getSpeed() == 0) {
//            setAnimation(idle);
//        } else {
//            // MOVING
//            float angle = getMotionAngle();
//            if (angle >= 45 && angle <= 135) {
//                setAnimation(moveUp);
//            } else if (angle > 135 && angle < 225) {
//                setAnimation(moveLeft);
//            } else if (angle >= 225 && angle <= 315) {
//                setAnimation(moveDown);
//            } else {
//                setAnimation(moveRight);
//            }
//        }
//
//        alignCamera();
//        boundToWorld();
//        applyPhysics(dt);
    }
}
