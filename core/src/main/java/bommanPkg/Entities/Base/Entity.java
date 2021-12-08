package bommanPkg.Entities.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Entity extends Actor {
    /** Constants. **/
    protected static final int gridSize = 64;
    protected static final float frameDuration = 0.2f;

    /**
     * Variables.
     */
    private Animation<TextureRegion> animation;
    private float elapsedTime;

    private Rectangle collisionBounds;

    /**
     * Constructor.
     */
    public Entity(float x, float y, Stage s) {
        super();

        // Stage Related
        setPosition(x, y);
        s.addActor(this);

        // Self
        setSize(gridSize, gridSize);
        setCollisionBounds();

        animation = null;
        elapsedTime = 0;
    }

    /** Getter: collisionBounds. */
    public Rectangle getCollisionBounds() {
        return collisionBounds;
    }

    /** Setter: collisionBounds. */
    public void setCollisionBounds() {
        collisionBounds = new Rectangle(getX(), getY(), gridSize, gridSize);
        collisionBounds.setCenter(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    /**
     * Setter: Animation.
     */
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
        TextureRegion textureRegion = animation.getKeyFrame(0);

        float width = textureRegion.getRegionWidth();
        float height = textureRegion.getRegionHeight();
        setSize(width, height);
        setOrigin(width / 2, height / 2);

        if (collisionBounds == null)
            setCollisionBounds();
    }

    /**
     * Act (Update actor based on time).
     */
    public void act(float dt) {
        super.act(dt);
        elapsedTime += dt;
    }

    /**
     * Draw Graphics.
     */
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // Apply color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible())
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    /**
     * Load Animation from Files.
     */
    public Animation<TextureRegion> loadAnimationFromFiles(
            String[] fileNames,
            float frameDuration,
            boolean loop
    ) {
        Array<TextureRegion> textureArray = new Array<>();
        for (String fileName : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        return getTextureRegionAnimation(frameDuration, textureArray, loop);
    }

    /**
     * Load Animation from Spritesheet.
     */
    public Animation<TextureRegion> loadAnimationFromSheet(
            String fileName,
            int rows, int cols,
            float frameDuration, boolean loop
    ) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                textureArray.add(temp[r][c]);
            }
        }
        return getTextureRegionAnimation(frameDuration, textureArray, loop);
    }

    /** Get TextureRegion Animation. */
    private Animation<TextureRegion> getTextureRegionAnimation(
            float frameDuration,
            Array<TextureRegion> textureArray,
            boolean loop
    ) {
        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);

        if (loop) {
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }
        else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if (animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    /**
     * Load Texture (Only one image).
     */
    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = {fileName};
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    // Debugging Texture // TODO: Maybe redundant?
    private Animation<TextureRegion> loadDefaultTexture() {
        return loadTexture("sprites/default.png");
    }

    /** Getter: Elapsed Time. */
    public float getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Check if the Animation is finished.
     */
    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    /** Resetter: Elapsed Time. */
    public void resetElapsedTime() {
        elapsedTime = 0f;
    }

    /** Overlap Check. */
    public boolean overlaps(Entity other) {
        if (other == null) {
            return false;
        }
        return getCollisionBounds().overlaps(other.getCollisionBounds());
    }

    /** Update Entity. */
    public void updateEntity() {
        collisionBounds.set(getX(), getY(), getWidth(), getHeight());
    }
}
