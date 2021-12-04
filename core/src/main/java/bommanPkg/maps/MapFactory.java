package bommanPkg.maps;

import java.util.Hashtable;

import static bommanPkg.maps.MapFactory.MapType.TOP_WORLD;

public class MapFactory {
    /**
     * **************************************************
     * Hashtable - Map Table.
     * **************************************************
     */
    private static Hashtable<MapType, Map> mapTable = new Hashtable<MapType, Map>();

    /**
     * **************************************************
     * Get Map from a MapType.
     * **************************************************
     */
    static public Map getMap(MapType mapType) {
        Map map = null;

        boolean isTopWorld = (mapType == TOP_WORLD);

        if (isTopWorld) {
            map = mapTable.get(TOP_WORLD);
            if (map == null) {
                map = new TopWorldMap();
                mapTable.put(TOP_WORLD, map);
            }
        } else {
            map = mapTable.get(mapType);
            if (map == null) {
                map = new LevelMap(mapType, String.format("maps/%s.tmx", mapType.toString()));
                mapTable.put(mapType, map);
            }
        }

        return map;
    }
    /**
     * **************************************************
     * Enum - MapType.
     * **************************************************
     */
    public static enum MapType {
        MAP1, MAP2, MAP3, MAP4, MAP5, MAP6, MAP7, TOP_WORLD, MAP_CUSTOM1, MAP_CUSTOM2
    }
}