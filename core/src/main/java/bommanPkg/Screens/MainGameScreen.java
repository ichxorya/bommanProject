package bommanPkg.Screens;

import bommanPkg.Entities.Derived.LivingEntities.Enemies.BakaBot;
import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import bommanPkg.Entities.Derived.MapEntities.Derived.Brick;
import bommanPkg.Entities.Derived.MapEntities.Derived.Grass;
import bommanPkg.Entities.Derived.MapEntities.Derived.Wall;
import bommanPkg.Maps.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static bommanPkg.Entities.Base.Entity.gridSize;

public class MainGameScreen extends MyScreen {
    private GameMap map;
    private Player player;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void initialize() {
        map = new GameMap();
        generateMap(map);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);
        viewport = new FitViewport(1024, 1024, camera);
        mainStage.setViewport(viewport);
    }

    // TODO: BRUH

    @Override
    public void update(float dt) {
        cameraUpdate();

        player.act(dt, map);
        map.actLivingEntities(dt);
        map.actBombEntities(dt);
        map.actMapEntities(dt);
        map.actFlameEntities(dt);
        map.printGridMap();

        theMostImportantMethodIGuess();
    }

    private void cameraUpdate() {
        //camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }

    private void theMostImportantMethodIGuess() {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)
        && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.U)
        && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)
        && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.M)
        && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.O)
        && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.N)) {
            Gdx.net.openURI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        }
    }

    private void generateMap(GameMap map) {
        int[][] mapData = map.getGridMap();
        int horizontalBlocks = map.getHorizontalBlocks();
        int verticalBlocks = map.getVerticalBlocks();

        fillMapWithGrass(horizontalBlocks, verticalBlocks);
        fillMapWithEntities(map, mapData, horizontalBlocks, verticalBlocks);
    }

    private void fillMapWithEntities(GameMap map, int[][] mapData, int horizontalBlocks, int verticalBlocks) {
        int entityPosY = (verticalBlocks * gridSize) - gridSize;
        for (int y = 0; y < verticalBlocks; y++) {
            int entityPosX = 0;
            for (int x = 0; x < horizontalBlocks; x++) {
                switch (mapData[x][y]) {
                    case 1:
                        Wall tempWall = new Wall(entityPosX, entityPosY, mainStage, x, y);
                        break;
                    case 2:
                        Brick tempBrick = new Brick(entityPosX, entityPosY, mainStage, x, y);
                        map.add(tempBrick);
                        break;
                    case 4:
                        BakaBot tempBot = new BakaBot(entityPosX, entityPosY, mainStage, x, y);
                        map.add(tempBot);
                        break;
                    case 7:
                        player = new Player(entityPosX, entityPosY, mainStage, x, y);
                        break;
                }
                entityPosX += gridSize;
            }
            entityPosY -= gridSize;
        }
    }

    private void fillMapWithGrass(int horizontalBlocks, int verticalBlocks) {
        int entityPosY = 0;
        for (int y = 0; y < verticalBlocks; y++) {
            int entityPosX = 0;
            for (int x = 0; x < horizontalBlocks; x++) {
                new Grass(entityPosX, entityPosY, mainStage, x, y);
                entityPosX += gridSize;
            }
            entityPosY += gridSize;
        }
    }
}
