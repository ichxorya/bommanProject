package bommanPkg.Screens;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Entities.Derived.LivingEntities.Enemies.BakaBot;
import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import bommanPkg.Entities.Derived.MapEntities.Derived.Grass;
import bommanPkg.Entities.Derived.MapEntities.Derived.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.List;
import java.util.Scanner;

import static bommanPkg.Entities.Base.Entity.gridSize;

public class MainGameScreen extends MyScreen {
    MapEntity[][] gridMap;
    List<LivingEntity> livingList;
    List<MapEntity> blockList;
    Player player;

    @Override
    public void initialize() {
        new GameMap();
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        wallTest(livingList, blockList);
        wallTest(player, blockList);
        updateLivingList(dt);
    }

    private void updateLivingList(float dt) {
        for (LivingEntity entity : livingList) {
            entity.update(dt);
        }
    }

    private void wallTest(List<LivingEntity> living, List<MapEntity> walls) {
        for (LivingEntity entity : living) {
            for (MapEntity wall : walls) {
                entity.preventOverlapBlock(wall);
            }
        }
    }
    private void wallTest(LivingEntity living, List<MapEntity> walls) {
        for (MapEntity wall : walls) {
            living.preventOverlapBlock(wall);
        }
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
    private class GameMap {
        /** Variables and Constants. **/
        private int horizontalBlocks;
        private int verticalBlocks;
        private String[] mapFile;
        private int level;

        /** Constructor. **/
        public GameMap() {
            blockList = new java.util.ArrayList<MapEntity>();
            livingList = new java.util.ArrayList<LivingEntity>();

            loadMapFile();
            generateGrassLayer();
//            generateItemLayer();
            generateWallLayer();
            generateLivingLayer();
        }

        private void generateLivingLayer() {
            int posX = 0;
            int posY = verticalBlocks * 64;

            for (int y = 0; y < verticalBlocks; y++) {
                posX = 0;
                for (int x = 0; x < horizontalBlocks; x++) {
                    switch (mapFile[y + 1].charAt(x)) {
                        case 'p':
                            player = new Player(posX, posY, mainStage);
                            break;
                        case 'x':
                            livingList.add(new BakaBot(posX, posY, mainStage));
                            break;
                    }
                    posX += gridSize;
                }
                posY -= gridSize;
            }
        }

        private void generateWallLayer() {
            int posX = 0;
            int posY = verticalBlocks * 64;

            for (int y = 0; y < verticalBlocks; y++) {
                posX = 0;
                for (int x = 0; x < horizontalBlocks; x++) {
                    if (mapFile[y + 1].charAt(x) == '#') {
                        gridMap[x][y] = new Wall(posX, posY, mainStage);
                        blockList.add(gridMap[x][y]);
                    }
                    posX += gridSize;
                }
                posY -= gridSize;
            }
        }

        private void generateGrassLayer() {
            int posX = 0;
            int posY = verticalBlocks * 64;

            for (int y = 0; y < verticalBlocks; y++) {
                posX = 0;
                for (int x = 0; x < horizontalBlocks; x++) {
                    gridMap[x][y] = new Grass(posX, posY, mainStage);
                    posX += gridSize;
                }
                posY -= gridSize;
            }
        }

        public void loadMapFile() {
            FileHandle file = Gdx.files.internal("maps/map2.txt");
            mapFile = file.readString().split("\n");

            Scanner reader = new Scanner(mapFile[0]);
            if (reader.hasNextLine()) {
                level = reader.nextInt();
                verticalBlocks = reader.nextInt();
                horizontalBlocks = reader.nextInt();

                gridMap = new MapEntity[horizontalBlocks][verticalBlocks];
            }
        }
    }
}
