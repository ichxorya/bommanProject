package bommanPkg.screens;

import bommanPkg.Entity;
import bommanPkg.MapManager;
import bommanPkg.PlayerController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MainGameScreen implements Screen {
    /* Variables */
    private static final String TAG = MainGameScreen.class.getSimpleName();

    //// VIEWPORT Subclass
    private static class VIEWPORT {
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    private PlayerController controller;
    private TextureRegion currentPlayerFrame;
    private Sprite currentPlayerSprite;
    private OrthogonalTiledMapRenderer mapRenderer = null;
    private OrthographicCamera camera = null;
    private static MapManager mapManager;

    /* Methods */
    //// Constructor
    public MainGameScreen() {
        mapManager = new MapManager();
    }

    private static Entity player;

    //////////////////////////////////////////////////
    @Override
    public void show() {
        setupViewport(10, 10);
        camera = new OrthographicCamera();
        camera.setToOrtho(
                false,
                VIEWPORT.viewportWidth,
                VIEWPORT.viewportHeight
        );
        mapRenderer = new OrthogonalTiledMapRenderer(
                mapManager.getCurrentMap(),
                MapManager.UNIT_SCALE
        );
        mapRenderer.setView(camera);
        Gdx.app.debug(TAG, "UnitScale value is: " +
                mapRenderer.getUnitScale());
        player = new Entity();
        player.init(
                mapManager.getPlayerStartUnitScaled().x,
                mapManager.getPlayerStartUnitScaled().y
        );
        currentPlayerSprite = player.getFrameSprite();
        controller = new PlayerController(player);
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Lock and center the camera to the player's position
        float[] currentPlayerSpritePos = {
                currentPlayerSprite.getX(),     // PosX
                currentPlayerSprite.getY(),     // PosY
                0f                              // PosZ (redundant)
        };
        camera.position.set(currentPlayerSpritePos);
        camera.update();

        player.update(delta);
        currentPlayerFrame = player.getFrame();

        updatePortalLayerActivation(player.boundingBox);
        if (!isCollisionWithMapLayer(player.boundingBox)) {
            player.setNextPositionToCurrent();
        }
        controller.update(delta);
        mapRenderer.setView(camera);
        mapRenderer.render();

        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(
                currentPlayerFrame,
                currentPlayerSpritePos[0], currentPlayerSpritePos[1],
                1, 1);
        mapRenderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        player.dispose();
        controller.dispose();
        Gdx.input.setInputProcessor(null);
        mapRenderer.dispose();
    }

    private void setupViewport(int width, int height) {
        // Make the viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        // Current viewport dimensions
        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        // Pixel dimensions of display
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        // Aspect ratio for current viewport
        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        // Update viewport if there could be skewing
        if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio) {

            // Letterbox left and right
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight *
                    (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        } else {

            // Letterbox above and below
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth *
                    (VIEWPORT.physicalHeight / VIEWPORT.physicalWidth);
        }
        Gdx.app.debug(TAG, "WorldRenderer: virtual: (" +
                VIEWPORT.virtualWidth + "," + VIEWPORT.virtualHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: viewport: (" +
                VIEWPORT.viewportWidth + "," + VIEWPORT.viewportHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: physical: (" +
                VIEWPORT.physicalWidth + "," + VIEWPORT.physicalHeight + ")");
    }

    private boolean isCollisionWithMapLayer(Rectangle boundingBox) {
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();

        if (mapCollisionLayer == null) {
            return false;
        }

        Rectangle rectangle = null;

        for (MapObject object : mapCollisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject) object).getRectangle();

                if (boundingBox.overlaps(rectangle)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean updatePortalLayerActivation(Rectangle boundingBox) {
        MapLayer mapPortalLayer = mapManager.getPortalLayer();
        if (mapPortalLayer == null) {
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
                    mapManager.setClosestStartPositionFromScaledUnits(player.getCurrentPosition());
                    mapManager.loadMap(mapName);
                    player.init(
                            mapManager.getPlayerStartUnitScaled().x,
                            mapManager.getPlayerStartUnitScaled().y
                    );
                    mapRenderer.setMap(mapManager.getCurrentMap());
                    Gdx.app.debug(TAG, "Portal Activated");
                    return true;
                }
            }
        }
        return false;
    }


}
