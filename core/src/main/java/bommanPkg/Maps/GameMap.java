package bommanPkg.Maps;

import bommanPkg.Entities.Base.Entity;
import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
import bommanPkg.Entities.Derived.LivingEntities.Enemies.BakaBot;
import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import bommanPkg.Entities.Derived.MapEntities.Base.MapEntity;
import bommanPkg.Entities.Derived.MapEntities.Derived.Grass;
import bommanPkg.Entities.Derived.MapEntities.Derived.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import static bommanPkg.Entities.Base.Entity.gridSize;

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
                case '+':
                    gridMap[x][y] = 2;
                    break;
                case '?':
                    gridMap[x][y] = 3;
                    break;
                case 'x':
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
        }
    }

    public void actLivingEntities(float dt) {
        for (LivingEntity entity : livingList) {
            entity.act(dt, this);
        }
    }

    public void printGridMap() {
        for (int y = 0; y < verticalBlocks; y++) {
            for (int x = 0; x < horizontalBlocks; x++) {
                System.out.print(gridMap[x][y] + " ");
            }
            System.out.println();
        }
    }
}