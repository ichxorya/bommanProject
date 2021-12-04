package bommanPkg.entities.components;

import bommanPkg.Utility;
import bommanPkg.entities.Entity;
import bommanPkg.maps.MapManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.Hashtable;

public abstract class GraphicsComponent implements Component {
    protected TextureRegion currentFrame = null;
    protected float frameTime = 0f;
    protected Entity.State currentState;
    protected Entity.Direction currentDirection;
    protected Json json;
    protected Vector2 currentPosition;
    protected Hashtable<Entity.AnimationType, Animation<TextureRegion>> animations;
    protected ShapeRenderer shapeRenderer;

    protected GraphicsComponent() {
    }

    public abstract void update(Entity entity, MapManager mapManager, Batch batch, float delta);

    protected void updateAnimations(float delta) {
        // Want to avoid overflow
        frameTime = (frameTime + delta) % 5;

        // Look into the appropriate variable when changing position
        switch (currentDirection) {
            case UP:
                if (currentState == Entity.State.WALKING) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_UP);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                } else if (currentState == Entity.State.IDLE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_UP);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrames()[3];
                } else if (currentState == Entity.State.IMMOBILE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.IMMOBILE);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                }
                break;
            case RIGHT:
                if (currentState == Entity.State.WALKING) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_RIGHT);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                } else if (currentState == Entity.State.IDLE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_RIGHT);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrames()[2];
                } else if (currentState == Entity.State.IMMOBILE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.IMMOBILE);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                }
                break;
            case DOWN:
                if (currentState == Entity.State.WALKING) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_DOWN);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                } else if (currentState == Entity.State.IDLE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_DOWN);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrames()[0];
                } else if (currentState == Entity.State.IMMOBILE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.IMMOBILE);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                }
                break;
            case LEFT:
                if (currentState == Entity.State.WALKING) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_LEFT);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                } else if (currentState == Entity.State.IDLE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.WALK_LEFT);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrames()[1];
                } else if (currentState == Entity.State.IMMOBILE) {
                    Animation<TextureRegion> animation = animations.get(Entity.AnimationType.IMMOBILE);

                    if (animation == null) return;
                    currentFrame = animation.getKeyFrame(frameTime);
                }
                break;
        }
    }

    protected Animation loadAnimation(String firstTexture, String secondTexture, Array<GridPoint2> points, float frameDuration) {
        Utility.loadTextureAsset(firstTexture);
        Texture texture1 = Utility.getTextureAsset(firstTexture);

        Utility.loadTextureAsset(secondTexture);
        Texture texture2 = Utility.getTextureAsset(secondTexture);

        TextureRegion[][] texture1Frames = TextureRegion.split(texture1, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT);
        TextureRegion[][] texture2Frames = TextureRegion.split(texture2, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT);

        GridPoint2 point = points.first();

        Animation animation = new Animation(frameDuration, texture1Frames[point.x][point.y], texture2Frames[point.x][point.y]);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        return animation;
    }

    protected Animation loadAnimation(String textureName, Array<GridPoint2> points, float frameDuration) {
        Utility.loadTextureAsset(textureName);
        Texture texture = Utility.getTextureAsset(textureName);

        TextureRegion[][] textureFrames = TextureRegion.split(texture, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT);

        TextureRegion[] animationKeyFrames = new TextureRegion[points.size];

        for (int i = 0; i < points.size; i++) {
            animationKeyFrames[i] = textureFrames[points.get(i).x][points.get(i).y];
        }

        Animation animation = new Animation(frameDuration, (Object[]) animationKeyFrames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        return animation;
    }

    public Animation<TextureRegion> getAnimation(Entity.AnimationType type) {
        return animations.get(type);
    }
}
