package bommanPkg.Maps;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Entities.Derived.Bomb.Bomb;
import bommanPkg.Entities.Derived.Bomb.Flame;
import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.*;
public class GameMap {
    public int getHorizontalBlocks() {
        return horizontalBlocks;
    }

    public int getVerticalBlocks() {
        return verticalBlocks;
    }

    /**
     * Variables and Constants.
     **/
    private int horizontalBlocks;
    private int verticalBlocks;
    private String[] mapFile;
    private int level;
    private int[][] gridMap;
    List<LivingEntity> livingList;
    List<MapEntity> blockList;
    List<Bomb> bombList;
    List<Flame> flameList;

    /** Getter: gridMap. **/
    public int[][] getGridMap() {
        return gridMap;
    }

    /** Getter: level. **/
    public int getLevel() {
        return level;
    }

    /**
     * Constructor.
     **/
    public GameMap() {
        blockList = new ArrayList<>();
        livingList = new ArrayList<>();
        bombList = new ArrayList<>();
        flameList = new ArrayList<>();

        loadMapFile();
        generateMap();

        printGridMap();
    }

    private void generateMap() {
        int y = 0;
        for (int s = 1; s < mapFile.length; s++) {
            generateFromLine(mapFile[s], y);
            ++y;
        }
    }

    /**
     * Generate map from line.
     * Char denotations:
     * # - Wall
     * + - Brick
     * ? - Portal
     * x - Baka_AI
     * p - Player
     *
     * Others - Grass (already initialized)
     */
    private void generateFromLine(String s, int y) {
        for (int x = 0; x < s.length(); x++) {
            switch (s.charAt(x)) {
                case '#':
                    gridMap[x][y] = 1;
                    break;
                case '*':
                    gridMap[x][y] = 2;
                    break;
                case '?':
                    gridMap[x][y] = 3;
                    break;
                case '1':
                    gridMap[x][y] = 4;
                    break;
                case 'p':
                    gridMap[x][y] = 7;
                    break;
            }
        }
    }

    public void loadMapFile() {
        FileHandle file = Gdx.files.internal("maps/demo_map.txt");
        mapFile = file.readString().split("\n");

        Scanner reader = new Scanner(mapFile[0]);
        if (reader.hasNextLine()) {
            level = reader.nextInt();
            verticalBlocks = reader.nextInt();
            horizontalBlocks = reader.nextInt();

            gridMap = new int[horizontalBlocks][verticalBlocks];
        }
    }

    public void add(Entity entity) {
        if (entity instanceof MapEntity) {
            blockList.add((MapEntity) entity);
        } else if (entity instanceof LivingEntity) {
            livingList.add((LivingEntity) entity);
        } else if (entity instanceof Bomb) {
            bombList.add((Bomb) entity);
        } else if (entity instanceof Flame) {
            flameList.add((Flame) entity);
        }
    }

    public void actLivingEntities(float dt) {
        for (LivingEntity entity : livingList) {
            entity.act(dt, this);
        }
    }

    public void actBombEntities(float dt) {
        for (Bomb entity : bombList) {
            entity.act(dt, this);
        }

        bombList.removeIf(Bomb::isExploded);
    }

    public void actFlameEntities(float dt) {
        for (Flame entity : flameList) {
            entity.act(dt, this);
            System.out.println("flame x-" + entity.getGridPosX() + " y-" + entity.getGridPosY());
        }

        flameList.removeIf(Flame::isDone);
    }

    private void setGridPos(int x, int y, int i) {
        this.gridMap[x][y] = i;
    }

    public void actMapEntities(float dt) {
        for (MapEntity entity : blockList) {
            entity.act(dt, this);
        }

        // Remove all instances of Brick which are destroyed.
        blockList.removeIf(MapEntity::isDestroyed);
    }

    public void printGridMap() {
        for (int y = 0; y < verticalBlocks; y++) {
            for (int x = 0; x < horizontalBlocks; x++) {
                System.out.print(gridMap[x][y] + " ");
            }
            System.out.println();
        }
    }

    public void removeFromList(Entity entity) {
        if (entity instanceof MapEntity) {
            blockList.remove(entity);
        } else if (entity instanceof LivingEntity) {
            livingList.remove(entity);
        } else if (entity instanceof Bomb) {
            bombList.remove(entity);
        }
    }

    public boolean isInList(Entity entity) {
        if (entity instanceof MapEntity) {
            return blockList.contains(entity);
        } else if (entity instanceof LivingEntity) {
            return livingList.contains(entity);
        } else if (entity instanceof Bomb) {
            return bombList.contains(entity);
        }
        return false;
    }

    public List<? extends Entity> getList(Entity entity) {
        if (entity instanceof MapEntity) {
            return blockList;
        } else if (entity instanceof LivingEntity) {
            return livingList;
        } else if (entity instanceof Bomb) {
            return bombList;
        }
        return null;
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    public void mapDestroyWall(int gridPosX, int gridPosY) {
        for (MapEntity entity : blockList) {
            if (entity.getGridPosX() == gridPosX && entity.getGridPosY() == gridPosY) {
                entity.destroy();
            }
        }
    }
}