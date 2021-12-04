package bommanPkg.maps;

import com.badlogic.gdx.graphics.g2d.Batch;

public class TopWorldMap extends Map {
    static String fullMapPath = "maps/topworld.tmx";

    TopWorldMap() {
        super(MapFactory.MapType.TOP_WORLD, fullMapPath);
    }

    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta) {

    }
}
