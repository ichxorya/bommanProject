package bommanPkg.Entities.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.viewport.Viewport;

public class EntityV1 extends Actor {
    private static final float PI = 3.14f;

    /**
     * Variables.
     */
    private Animation<TextureRegion> animation;
    private float elapsedTime;

    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;

    /**
     * Constructor.
     */
    public EntityV1(float x, float y, Stage s) {
        super();

        // Stage Related
        setPosition(x, y);
        s.addActor(this);

        // Self
        animation = null;
        elapsedTime = 0;
    }

    /**
     * Setter: worldBounds.
     */
    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(EntityV1 ba) {
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    /** Bound to World. */
    public void boundToWorld() {
        // Check left edge
        if (getX() < 0)
            setX(0);

        // Check right edge
        if (getX() + getWidth() > worldBounds.width)
            setX(worldBounds.width - getWidth());

        // Check bottom edge
        if (getY() < 0)
            setY(0);

        // Check top edge
        if (getY() + getHeight() > worldBounds.height)
            setY(worldBounds.height - getHeight());
    }

    /**
     * Setter: boundaryPolygon.
     */
    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    /**
     * Getter: boundaryPolygon.
     */
    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2 * numSides];

        for (int i = 0; i < numSides; i++) {
            float angle = (i * 2 * PI) / numSides;

            // X-coordinate
            vertices[2 * i] = w / 2 * MathUtils.cos(angle) + w / 2;

            // Y-coordinate
            vertices[2 * i + 1] = h / 2 * MathUtils.sin(angle) + h / 2;
        }

        boundaryPolygon = new Polygon(vertices);
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

        if (boundaryPolygon == null)
            setBoundaryRectangle();
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
    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames,
                                                           float frameDuration, boolean loop) {
        Array<TextureRegion> textureArray = new Array<>();
        for (String fileName : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);
        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        if (animation == null)
            setAnimation(anim);
        return anim;
    }

    /**
     * Load Animation from Spritesheet.
     */
    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols,
                                                           float frameDuration, boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add(temp[r][c]);

        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);
        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        if (animation == null)
            setAnimation(anim);
        return anim;
    }

    /**
     * Load Texture of MapEntity Object.
     */
    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = {fileName};
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    /**
     * Check if the Animation is finished.
     */
    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    /** Align Camera to Actor. */
    public void alignCamera() {
        Camera camera = getStage().getCamera();
        Viewport viewport = getStage().getViewport();

        // Center Camera on Actor
        camera.position.set(getX() + getOriginX(), getY() + getOriginY(), 0);

        // Bound Camera to World
        camera.position.x = MathUtils.clamp(
                camera.position.x,
                camera.viewportWidth/2, worldBounds.width - camera.viewportWidth/2
        );
        camera.position.y = MathUtils.clamp(
                camera.position.y,
                camera.viewportHeight/2, worldBounds.height - camera.viewportHeight/2
        );

        camera.update();
    }
}
