package bommanPkg.Screens;

import bommanPkg.Entities.Derived.LivingEntities.Enemies.BakaBot;
import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import bommanPkg.Entities.Derived.MapEntities.Derived.Brick;
import bommanPkg.Entities.Derived.MapEntities.Derived.Grass;
import bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Items.*;
import bommanPkg.Entities.Derived.MapEntities.Derived.UnderBrick.Portal;
import bommanPkg.Entities.Derived.MapEntities.Derived.Wall;
import bommanPkg.Game.Secret;
import bommanPkg.Maps.GameMap;
import bommanPkg.Maps.GridPos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static bommanPkg.Entities.Base.Entity.gridSize;

public class MainGameScreen extends MyScreen {
    private GameMap gameMap;
    private Player player;
    private GridPos playerInitGridPos;
    private int[] playerInitPos;
    private OrthographicCamera camera;
    private boolean victory;
    private Sound victoryNotification;
    private boolean victoryChecked;

    @Override
    public void initialize() {
        gameMap = new GameMap("maps/demo_map.txt");
        generateMap(gameMap);
        player = new Player(playerInitPos[0], playerInitPos[1], mainStage, playerInitGridPos.getX(), playerInitGridPos.getY());

        camera = new OrthographicCamera(1024, 1024);
        Viewport viewport = new FitViewport(1024, 1024, camera);
        mainStage.setViewport(viewport);

        setupSound();
    }

    private void setupSound() {
        victoryNotification = Gdx.audio.newSound(Gdx.files.internal("sfxs/toasang.mp3"));
    }

    // TODO: BRUH

    @Override
    public void update(float dt) {
        cameraUpdate();

        checkVictory(gameMap);

        player.act(dt, gameMap);
        gameMap.actLivingEntities(dt);
        gameMap.actBombEntities(dt);
        gameMap.actBrickEntities(dt);
        gameMap.actFlameEntities(dt);
        gameMap.actPortalEntities(dt);
        gameMap.actItemEntities(dt);
        gameMap.printGridMap();

        theMostImportantMethodIGuess(player);
    }

    private void checkVictory(GameMap gameMap) {
        if (!victoryChecked && !player.isDead() && gameMap.killedAllEnemies()) {
            victory = true;
            victoryNotification.play();
            victoryNotification.loop();
            victoryChecked = true;
        }
    }

    private void cameraUpdate() {
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }

    private void theMostImportantMethodIGuess(Player player) {
        Secret.theMostImportantMethodIGuess(player);
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
                        new Wall(entityPosX, entityPosY, mainStage, x, y);
                        break;
                    case 2:
                        map.add(new Brick(entityPosX, entityPosY, mainStage, x, y));
                        break;
                    case 3:
                        map.add(new Portal(entityPosX, entityPosY, mainStage, x, y));
                        map.add(new Brick(entityPosX, entityPosY, mainStage, x, y));
                        break;
                    case 4:
                        BakaBot tempBot = new BakaBot(entityPosX, entityPosY, mainStage, x, y);
                        map.add(tempBot);
                        break;
                    case 7:
                        playerInitGridPos = new GridPos(x, y);
                        playerInitPos = new int[]{entityPosX, entityPosY};
                        break;
                    case 8:
                        map.add(new SpeedItem(entityPosX, entityPosY, mainStage, x, y));
                        map.add(new Brick(entityPosX, entityPosY, mainStage, x, y));
                        break;
                    case 9:
                        map.add(new BombItem(entityPosX, entityPosY, mainStage, x, y));
                        map.add(new Brick(entityPosX, entityPosY, mainStage, x, y));
                        break;
                    case 10:
                        map.add(new FlameItem(entityPosX, entityPosY, mainStage, x, y));
                        map.add(new Brick(entityPosX, entityPosY, mainStage, x, y));
                        break;
                    case 12:
                        map.add(new GodItem(entityPosX, entityPosY, mainStage, x, y));
                        map.add(new Brick(entityPosX, entityPosY, mainStage, x, y));
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
