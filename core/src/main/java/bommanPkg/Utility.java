package bommanPkg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Utility {
    /* Variables */
    public static final AssetManager assetManager = new AssetManager();
    private static final String TAG = Utility.class.getSimpleName();
    private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

    /* Methods */
    //// Unload Asset
    public static void unloadAsset(String assetFilenamePath) {
        if (assetManager.isLoaded(assetFilenamePath)) {
            assetManager.unload(assetFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload: " + assetFilenamePath);
        }
    }

    //// Load percentage.
    public static float loadCompleted() {
        return assetManager.getProgress();
    }

    //// Number of Assets in the queue.
    public static int numberAssetsQueued() {
        return assetManager.getQueuedAssets();
    }

    //// Loading update
    public static boolean updateAssetLoading() {
        return assetManager.update();
    }

    //// Is the Asset loaded
    public static boolean isAssetLoaded(String fileName) {
        return assetManager.isLoaded(fileName);
    }

    //// Load Map
    public static void loadMapAsset(String mapFilenamePath) {
        // Stop if the path is invalid
        if (mapFilenamePath == null || mapFilenamePath.isEmpty()) {
            return;
        }

        // Load
        if (filePathResolver.resolve(mapFilenamePath).exists()) {
            assetManager.setLoader(
                    TiledMap.class,
                    new TmxMapLoader(filePathResolver)
            );

            assetManager.load(mapFilenamePath, TiledMap.class);

            // Until we add loading screen,
            // Just block until we load the map

            assetManager.finishLoadingAsset(mapFilenamePath);
            Gdx.app.debug(TAG, "Map loaded!: " + mapFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Map doesn't exist!: " + mapFilenamePath);
        }
    }

    public static TiledMap getMapAsset(String mapFilenamePath) {
        TiledMap map = null;

        if (assetManager.isLoaded(mapFilenamePath)) {
            map = assetManager.get(mapFilenamePath, TiledMap.class);
        } else {
            Gdx.app.debug(TAG, "Map is not loaded: " + mapFilenamePath);
        }
        return map;
    }

    //// Load Texture
    public static void loadTextureAsset(String textureFilenamePath) {
        if (textureFilenamePath == null || textureFilenamePath.isEmpty()) {
            return;
        }

        if (filePathResolver.resolve(textureFilenamePath).exists()) {
            assetManager.setLoader(
                    Texture.class,
                    new TextureLoader(filePathResolver)
            );

            assetManager.load(textureFilenamePath, Texture.class);

            //Until we add loading screen,
            //just block until we load the map

            assetManager.finishLoadingAsset(textureFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Texture doesn't exist!: " + textureFilenamePath);
        }
    }

    public static Texture getTextureAsset(String textureFilenamePath) {
        Texture texture = null;

        if (assetManager.isLoaded(textureFilenamePath)) {
            texture = assetManager.get(textureFilenamePath, Texture.class);
        } else {
            Gdx.app.debug(TAG, "Texture is not loaded: " + textureFilenamePath);
        }
        return texture;
    }
}
