package bommanPkg.Screens;

import bommanPkg.Entities.Derived.LivingEntities.Enemies.BakaBot;
import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import bommanPkg.Entities.Derived.MapEntities.Derived.Brick;
import bommanPkg.Entities.Derived.MapEntities.Derived.Grass;
import bommanPkg.Entities.Derived.MapEntities.Derived.Wall;
import bommanPkg.Maps.GameMap;

import static bommanPkg.Entities.Base.Entity.gridSize;

public class MainGameScreen extends MyScreen {
    private GameMap map;
    private Player player;

    @Override
    public void initialize() {
        map = new GameMap();
        generateMap(map);
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
                new Grass(entityPosX, entityPosY, mainStage);
                entityPosX += gridSize;
            }
            entityPosY += gridSize;
        }
    }

    @Override
    public void update(float dt) {
        player.act(dt, map);
        map.actLivingEntities(dt);
        map.printGridMap();
    }

    // Methods required by InputProcessor interface
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    /** Inner class: GameMap. */

}
