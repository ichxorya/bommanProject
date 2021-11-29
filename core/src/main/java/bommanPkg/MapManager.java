package bommanPkg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.util.Hashtable;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    // All maps for the game
    private Hashtable<String, String> mapTable;
    private Hashtable<String, Vector2> playerStartLocationTable;

    // Maps
    private final static String TOP_WORLD = "TOP_WORLD";
    private final static String MAP1 = "MAP1";
    private final static String MAP2 = "MAP2";
    private final static String MAP3 = "MAP3";
    private final static String MAP4 = "MAP4";
    private final static String MAP5 = "MAP5";

    // Map layers
    private final static String MAP_COLLISION_LAYER = "MAP_COLLISION_LAYER";
    private final static String MAP_SPAWNS_LAYER = "MAP_SPAWNS_LAYER";
    private final static String MAP_PORTAL_LAYER = "MAP_PORTAL_LAYER";

    private final static String PLAYER_START = "PLAYER_START";

    private Vector2 playerStartPositionRect;

    // Constructor
    private Vector2 closestPlayerStartPosition;
    private Vector2 convertedUnits;
    private Vector2 playerStart;
    private TiledMap currentMap = null;
    private String currentMapName;
    private MapLayer collisionLayer = null;
    private MapLayer portalLayer = null;
    private MapLayer spawnsLayer = null;
    public final static float UNIT_SCALE = 1/16f;

    public MapManager() {
        playerStart = new Vector2(0,0);
        mapTable = new Hashtable();
        mapTable.put(TOP_WORLD, "maps/topworld.tmx");
        mapTable.put(MAP1, "maps/map1.tmx");
        mapTable.put(MAP2, "maps/map2.tmx");
        mapTable.put(MAP3, "maps/map3.tmx");
        mapTable.put(MAP4, "maps/map4.tmx");
        mapTable.put(MAP5, "maps/map5.tmx");

        playerStartLocationTable = new Hashtable<>();
        playerStartLocationTable.put(TOP_WORLD, playerStart.cpy());
        playerStartLocationTable.put(MAP1, playerStart.cpy());
        playerStartLocationTable.put(MAP2, playerStart.cpy());
        playerStartLocationTable.put(MAP3, playerStart.cpy());
        playerStartLocationTable.put(MAP4, playerStart.cpy());
        playerStartLocationTable.put(MAP5, playerStart.cpy());

        playerStartPositionRect = new Vector2(0,0);
        closestPlayerStartPosition = new Vector2(0,0);
        convertedUnits = new Vector2(0,0);
    }

    public void loadMap(String mapName) {
        playerStart.set(0, 0);
        String mapFullPath = mapTable.get(mapName);

        if (mapFullPath == null || mapFullPath.isEmpty()) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        }

        if (currentMap != null) {
            currentMap.dispose();
        }

        Utility.loadMapAsset(mapFullPath);
        if (Utility.isAssetLoaded(mapFullPath)) {
            currentMap = Utility.getMapAsset(mapFullPath);
            currentMapName = mapName;
        }else{
            Gdx.app.debug(TAG, "Map not loaded");
            return;
        }
        collisionLayer = currentMap.getLayers().get(MAP_COLLISION_LAYER);

        if (collisionLayer == null) {
            Gdx.app.debug(TAG, "No collision layer!");
        }

        portalLayer = currentMap.getLayers().get(MAP_PORTAL_LAYER);

        if (portalLayer == null) {
            Gdx.app.debug(TAG, "No portal layer!");
        }

        spawnsLayer = currentMap.getLayers().get(MAP_SPAWNS_LAYER);

        if (spawnsLayer == null) {
            Gdx.app.debug(TAG, "No spawn layer!");
        } else {
            Vector2 start = playerStartLocationTable.get(currentMapName);

            if (start.isZero()) {
                setClosestStartPosition(playerStart);
                start = playerStartLocationTable.get(currentMapName);
            }
            playerStart.set(start.x, start.y);
        }
        Gdx.app.debug(TAG,"Player Start: ("
                                  + playerStart.x + ","
                                  + playerStart.y + ")");
    }

    public TiledMap getCurrentMap() {
        if (currentMap == null) {
            currentMapName = MAP1;
            loadMap(currentMapName);
        }
        return currentMap;
    }

    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }

    public MapLayer getPortalLayer() {
        return portalLayer;
    }

    public Vector2 getPlayerStartUnitScaled() {
        Vector2 playerStartUnitScaled = playerStart.cpy();
        playerStartUnitScaled.set(playerStart.x * UNIT_SCALE, playerStart.y * UNIT_SCALE);

        return playerStartUnitScaled;
    }

    // TODO: FIX THIS AFTER IMPLEMENTED (FOR THE BOMBERMAN PURPOSE)
    private void setClosestStartPosition(final Vector2 position) {
        // Get last known position on this map
        playerStartPositionRect.set(0, 0);
        closestPlayerStartPosition.set(0, 0);
        float shortestDistance = 0f;

        // Go through all player start positions and choose closest to last known position
        for(MapObject object : spawnsLayer.getObjects()) {
            if (object.getName().equalsIgnoreCase(PLAYER_START)) {
                ((RectangleMapObject)object).getRectangle().getPosition(playerStartPositionRect);
                float distance = position.dst2(playerStartPositionRect);

                if (distance < shortestDistance || shortestDistance == 0) {
                    closestPlayerStartPosition.set(playerStartPositionRect);
                    shortestDistance = distance;
                }
            }
        }

        playerStartLocationTable.put(currentMapName, closestPlayerStartPosition.cpy());
    }

    public void setClosestStartPositionFromScaledUnits(Vector2 position) {
        if (UNIT_SCALE > 0) {
            convertedUnits.set(position.x/UNIT_SCALE, position.y/UNIT_SCALE);
            setClosestStartPosition(convertedUnits);
        }
    }
}