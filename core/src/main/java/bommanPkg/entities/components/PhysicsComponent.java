package bommanPkg.entities.components;

import bommanPkg.entities.Entity;
import bommanPkg.maps.Map;
import bommanPkg.maps.MapManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public abstract class PhysicsComponent implements Component {
    private static final String TAG = PhysicsComponent.class.getSimpleName();
    public Rectangle boundingBox;
    protected Vector2 nextEntityPosition;
    protected Vector2 currentEntityPosition;
    protected Entity.Direction currentDirection;
    protected Json json;
    protected Vector2 velocity;
    protected BoundingBoxLocation boundingBoxLocation;

    public PhysicsComponent() {
        this.nextEntityPosition = new Vector2(0, 0);
        this.currentEntityPosition = new Vector2(0, 0);
        this.velocity = new Vector2(2f, 2f);
        this.boundingBox = new Rectangle();
        this.json = new Json();
        boundingBoxLocation = BoundingBoxLocation.BOTTOM_LEFT;
    }

    public abstract void update(Entity entity, MapManager mapManager, float delta);

    /**
     * Collection-Collision Check.
     */
    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapManager) {
        Array<Entity> entities = mapManager.getCurrentMapEntities();
        boolean isCollisionWithMapEntities = false;

        for (Entity mapEntity : entities) {
            // Check for testing against self
            if (mapEntity.equals(entity)) {
                continue;
            }

            Rectangle targetRect = mapEntity.getCurrentBoundingBox();
            if (boundingBox.overlaps(targetRect)) {
                // Collision
                entity.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
                isCollisionWithMapEntities = true;
                break;
            }
        }

        return isCollisionWithMapEntities;
    }

    /**
     * Two Entities Collision Check.
     */
    protected boolean isCollision(Entity entitySource, Entity entityTarget) {
        boolean isCollisionWithMapEntities = false;
        if (entitySource.equals(entityTarget)) {
            return false;
        }

        if (entitySource.getCurrentBoundingBox().overlaps(
                entityTarget.getCurrentBoundingBox())) {
            // Collision
            entitySource.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
            isCollisionWithMapEntities = true;
        }

        return isCollisionWithMapEntities;
    }

    protected boolean isCollisionWithMapLayer(Entity entity, MapManager mapManager) {
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();

        if (mapCollisionLayer == null) {
            return false;
        }

        Rectangle rectangle = null;

        for (MapObject object : mapCollisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject) object).getRectangle();
                if (boundingBox.overlaps(rectangle)) {
                    // Collision
                    entity.sendMessage(MESSAGE.COLLISION_WITH_MAP);
                    return true;
                }
            }
        }

        return false;
    }

    protected void setNextPositionToCurrent(Entity entity) {
        this.currentEntityPosition.x = nextEntityPosition.x;
        this.currentEntityPosition.y = nextEntityPosition.y;

        entity.sendMessage(MESSAGE.CURRENT_POSITION, json.toJson(currentEntityPosition));
    }

    protected void calculateNextPosition(float deltaTime) {
        if (currentDirection == null) return;

        float testX = currentEntityPosition.x;
        float testY = currentEntityPosition.y;

        velocity.scl(deltaTime);

        switch (currentDirection) {
            case LEFT:
                testX -= velocity.x;
                break;
            case RIGHT:
                testX += velocity.x;
                break;
            case UP:
                testY += velocity.y;
                break;
            case DOWN:
                testY -= velocity.y;
                break;
            default:
                break;
        }

        nextEntityPosition.x = testX;
        nextEntityPosition.y = testY;

        //velocity
        velocity.scl(1 / deltaTime);
    }

    protected void initBoundingBox(float percentageWidthReduced, float percentageHeightReduced) {
        // Update the current bounding box
        float width;
        float height;
        float originalWidth = Entity.FRAME_WIDTH;
        float orinalHeight = Entity.FRAME_HEIGHT;

        // Size Reduction
        float widthReductionAmount = 1.0f - percentageWidthReduced;
        float heightReductionAmount = 1.0f - percentageHeightReduced;

        //
        if (widthReductionAmount > 0 && widthReductionAmount < 1) {
            width = Entity.FRAME_WIDTH * widthReductionAmount;
        } else {
            width = Entity.FRAME_WIDTH;
        }

        if (heightReductionAmount > 0 && heightReductionAmount < 1) {
            height = Entity.FRAME_HEIGHT * heightReductionAmount;
        } else {
            height = Entity.FRAME_HEIGHT;
        }
        if (width == 0 || height == 0) {
            Gdx.app.debug(TAG, "Width and Height are 0!! " + width + ":" + height);
        }

        //Need to account for the unit scale, since the map
        //coordinates will be in pixels
        float minX;
        float minY;
        if (Map.UNIT_SCALE > 0) {
            minX = nextEntityPosition.x / Map.UNIT_SCALE;
            minY = nextEntityPosition.y / Map.UNIT_SCALE;
        } else {
            minX = nextEntityPosition.x;
            minY = nextEntityPosition.y;
        }

        boundingBox.setWidth(width);
        boundingBox.setHeight(height);

        switch (boundingBoxLocation) {
            case BOTTOM_LEFT:
                boundingBox.set(minX, minY, width, height);
                break;
            case BOTTOM_CENTER:
                boundingBox.setCenter(minX + originalWidth / 2, minY + orinalHeight / 4);
                break;
            case CENTER:
                boundingBox.setCenter(minX + originalWidth / 2, minY + orinalHeight / 2);
                break;
        }
    }

    protected void updateBoundingBoxPosition(Vector2 position) {
        // Need to account for the unit scale, since the map coordinates will be in pixels
        float minX;
        float minY;
        if (Map.UNIT_SCALE > 0) {
            minX = position.x / Map.UNIT_SCALE;
            minY = position.y / Map.UNIT_SCALE;
        } else {
            minX = position.x;
            minY = position.y;
        }
        switch (boundingBoxLocation) {
            case BOTTOM_LEFT:
                boundingBox.set(minX, minY,
                        boundingBox.getWidth(),
                        boundingBox.getHeight()
                );
                break;
            case BOTTOM_CENTER:
                boundingBox.setCenter(
                        minX + Entity.FRAME_WIDTH / 2,
                        minY + Entity.FRAME_HEIGHT / 4
                );
                break;
            case CENTER:
                boundingBox.setCenter(
                        minX + Entity.FRAME_WIDTH / 2,
                        minY + Entity.FRAME_HEIGHT / 2
                );
                break;
        }
    }


    public static enum BoundingBoxLocation {
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        CENTER
    }
}
