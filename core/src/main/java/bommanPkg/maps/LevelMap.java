package bommanPkg.maps;

import com.badlogic.gdx.graphics.g2d.Batch;

public class LevelMap extends Map {
    LevelMap(MapFactory.MapType mapType, String fullMapPath) {
        super(mapType, fullMapPath);
    }

    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta) {

    }
}
