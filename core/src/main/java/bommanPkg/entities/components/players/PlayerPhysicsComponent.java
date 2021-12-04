package bommanPkg.entities.components.players;

import bommanPkg.entities.Entity;
import bommanPkg.entities.components.Component;
import bommanPkg.entities.components.PhysicsComponent;
import bommanPkg.maps.Map;
import bommanPkg.maps.MapFactory;
import bommanPkg.maps.MapManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class PlayerPhysicsComponent extends PhysicsComponent {
    private static final String TAG = PlayerPhysicsComponent.class.getSimpleName();

    private Entity.State state;
    private Vector3 mouseSelectCoordinates;
    private boolean isMouseSelectEnabled = false;
    private Ray selectionRay;
    private float selectRayMaximumDistance = 32.0f;

    public PlayerPhysicsComponent() {
        boundingBoxLocation = BoundingBoxLocation.BOTTOM_CENTER;
        initBoundingBox(0.3f, 0.5f);

        mouseSelectCoordinates = new Vector3(0, 0, 0);
        selectionRay = new Ray(new Vector3(), new Vector3());
    }

    @Override
    public void dispose() {
    }

    @Override
    public void receiveMessage(String message) {
        //Gdx.app.debug(TAG, "Got message " + message);
        String[] string = message.split(Component.MESSAGE_TOKEN);

        if (string.length == 0) return;

        //Specifically for messages with 1 object payload
        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
                nextEntityPosition.set(currentEntityPosition.x, currentEntityPosition.y);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                state = json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_SELECT_ENTITY.toString())) {
                mouseSelectCoordinates = json.fromJson(Vector3.class, string[1]);
                isMouseSelectEnabled = true;
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapManager, float delta) {
        //We want the hit box to be at the feet for a better feel
        //But this is a bomberman game TODO: FIX THIS BOX
        updateBoundingBoxPosition(nextEntityPosition);
        updatePortalLayerActivation(mapManager);

        if (isMouseSelectEnabled) {
            selectMapEntityCandidate(mapManager);
            isMouseSelectEnabled = false;
        }

        if (
                !isCollisionWithMapLayer(entity, mapManager) 
                && !isCollisionWithMapEntities(entity, mapManager) 
                && state == Entity.State.WALKING
        ) {
            setNextPositionToCurrent(entity);

            Camera camera = mapManager.getCamera();
            camera.position.set(currentEntityPosition.x, currentEntityPosition.y, 0f);
            camera.update();
        } else {
            updateBoundingBoxPosition(currentEntityPosition);
        }

        calculateNextPosition(delta);
    }

    private void selectMapEntityCandidate(MapManager mapManager) {
        Array<Entity> currentEntities = mapManager.getCurrentMapEntities();

        //Convert screen coordinates to world coordinates, then to unit scale coordinates
        mapManager.getCamera().unproject(mouseSelectCoordinates);
        mouseSelectCoordinates.x /= Map.UNIT_SCALE;
        mouseSelectCoordinates.y /= Map.UNIT_SCALE;

        for (Entity mapEntity : currentEntities) {
            //Don't break, reset all entities
            mapEntity.sendMessage(MESSAGE.ENTITY_DESELECTED);
            Rectangle mapEntityBoundingBox = mapEntity.getCurrentBoundingBox();

            if (mapEntity.getCurrentBoundingBox().contains(mouseSelectCoordinates.x, mouseSelectCoordinates.y)) {
                //Check distance
                selectionRay.set(boundingBox.x, boundingBox.y, 0.0f, mapEntityBoundingBox.x, mapEntityBoundingBox.y, 0.0f);
                float distance = selectionRay.origin.dst(selectionRay.direction);

                if (distance <= selectRayMaximumDistance) {
                    //We have a valid entity selection
                    //Picked/Selected
                    Gdx.app.debug(TAG, "Selected Entity! " + mapEntity.getEntityConfig().getEntityID());
                    mapEntity.sendMessage(MESSAGE.ENTITY_SELECTED);
                }
            }
        }
    }

    private boolean updatePortalLayerActivation(MapManager mapManager) {
        MapLayer mapPortalLayer = mapManager.getPortalLayer();

        if (mapPortalLayer == null) {
            Gdx.app.debug(TAG, "Portal Layer doesn't exist!");
            return false;
        }

        Rectangle rectangle = null;

        for (MapObject object : mapPortalLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject) object).getRectangle();

                if (boundingBox.overlaps(rectangle)) {
                    String mapName = object.getName();
                    if (mapName == null) {
                        return false;
                    }

                    mapManager.setClosestStartPositionFromScaledUnits(currentEntityPosition);
                    mapManager.loadMap(MapFactory.MapType.valueOf(mapName));

                    currentEntityPosition.x = mapManager.getPlayerStartUnitScaled().x;
                    currentEntityPosition.y = mapManager.getPlayerStartUnitScaled().y;
                    nextEntityPosition.x = mapManager.getPlayerStartUnitScaled().x;
                    nextEntityPosition.y = mapManager.getPlayerStartUnitScaled().y;

                    Gdx.app.debug(TAG, "Portal Activated");
                    return true;
                }
            }
        }
        return false;
    }
}