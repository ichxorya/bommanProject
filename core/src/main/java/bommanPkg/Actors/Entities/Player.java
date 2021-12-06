package bommanPkg.Actors.Entities;

import bommanPkg.Actors.MyActors.MyActorV2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class Player extends MyActorV2 {
    /**
     * Constants.
     */
    final int frameWidth = 64;
    final int frameHeight = 64;
    final int rows = 4;
    final int cols = 4;
    float frameDuration = 0.2f;

    /**
     * Animations.
     */
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> moveUp;
    private Animation<TextureRegion> moveDown;
    private Animation<TextureRegion> moveLeft;
    private Animation<TextureRegion> moveRight;

    HashMap<String, Animation<TextureRegion>> animationsHashMap;

    /**
     * Resource Paths.
     */
    String movingPath = "sprites/players/cirno_moving.png";
    String idlePath = "sprites/players/cirno_idle.png";

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
        idle = loadAnimationFromSheet(idlePath, 1, 2, frameDuration * 2.5f, true);

        Texture movement = new Texture(Gdx.files.internal(movingPath), true);
        TextureRegion[][] temp = TextureRegion.split(movement, frameWidth, frameHeight);

        setupAnimationHashMap();
        setupMovementAnimation(temp, "moveUp");
        setupMovementAnimation(temp, "moveLeft");
        setupMovementAnimation(temp, "moveDown");
        setupMovementAnimation(temp, "moveRight");


        setAnimation(idle);
    }

    private void setupAnimationHashMap() {
        animationsHashMap = new HashMap<>();
        animationsHashMap.put("idle", idle);
        animationsHashMap.put("moveUp", moveUp);
        animationsHashMap.put("moveDown", moveDown);
        animationsHashMap.put("moveLeft", moveLeft);
        animationsHashMap.put("moveRight", moveRight);
    }

    private void setupMovementAnimation(TextureRegion[][] temp, String move) {
        Array<TextureRegion> textureArray = new Array<>();

        int direction = getAnimationDirection(move);

        for (int c = 0; c < cols; c++)
            textureArray.add(temp[direction][c]);
        animationsHashMap.replace(
                move,
                new Animation<>(frameDuration, textureArray, Animation.PlayMode.LOOP)
        );
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
            setAnimationPaused(true);
        } else {
            // MOVING
            setAnimationPaused(false);
        }
    }
}
