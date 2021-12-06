package bommanPkg.Screens;

import bommanPkg.Entities.Base.EntityV2;
import bommanPkg.Entities.Derived.MapEntities.*;
import bommanPkg.Entities.Derived.MovableEntities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class MainGameScreen extends MyScreen {
    Player hero;
    int[] playerPos;

    @Override
    public void initialize() {
        playerPos = new int[2];

        loadMap("maps/map.txt");

        EntityV2.setWorldBounds(sizeX * gridSize, sizeY * gridSize);
    }

    @Override
    public void update(float dt) {
        hero.alignCamera();

        playerInput();
        wallstuff();

    }

    private void playerInput() {
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            hero.accelerateAtAngle(180);
        else if (Gdx.input.isKeyPressed(Keys.RIGHT))
            hero.accelerateAtAngle(0);
        else if (Gdx.input.isKeyPressed(Keys.UP))
            hero.accelerateAtAngle(90);
        else if (Gdx.input.isKeyPressed(Keys.DOWN))
            hero.accelerateAtAngle(270);
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

    /////////////////////////////
    private int level;
    private int sizeY;
    private int sizeX;

    private final int gridSize = 64;
    private int mapLineX;
    private int mapLineY;
    ArrayList<MapEntity> mapEntities;

    public void loadMap(String path) {
        mapEntities = new ArrayList<>();

        FileHandle file = Gdx.files.internal("maps/map.txt");
        String mapInfo = file.readString();
        Scanner reader = new Scanner(mapInfo);

        System.out.println(mapInfo);

        mapInfoGenerator(reader);
        mapFiller(reader);
    }

    private void mapFiller(Scanner reader) {
        Stack<String> mapLines = new Stack<>();
        for (int i = 0; i < sizeY; i++) {
            mapLines.push(reader.nextLine());
        }

        createMapLine_Grass();

        while (!mapLines.isEmpty()) {
            String mapLine = mapLines.pop();
            if (mapLine.length() > 0) {
                createMapLine(mapLine);
            }
        }

        hero = new Player(playerPos[0], playerPos[1], mainStage);
    }

    private void wallstuff() {
        for (MapEntity mapEntity : mapEntities) {
            hero.preventOverlap(mapEntity);
        }
    }

    private void createMapLine_Grass() {
        for (int j = 0; j < sizeY; j++) {
            mapLineX = 0;
            for (int i = 0; i < sizeX; i++) {
                new Grass(mapLineX, mapLineY, 64, 64, mainStage);
                mapLineX += gridSize;
            }
            mapLineY += gridSize;
        }
        mapLineX = 0;
        mapLineY = 0;
    }

    private void createMapLine(String mapLine) {
        mapLineX = 0;
        for (int i = 0; i < sizeX; i++) {
            char entity = mapLine.charAt(i);
            switch(entity) {
                case '#':
                    mapEntities.add(new Wall(mapLineX, mapLineY, 64, 64, mainStage));
                    break;
                case '*':
                    mapEntities.add(new Brick(mapLineX, mapLineY, 64, 64, mainStage));
                    break;
                case 'x':
                    new Portal(mapLineX, mapLineY, 64, 64, mainStage);
                    break;
                case 'p':
                    playerPos[0] = mapLineX;
                    playerPos[1] = mapLineY;
                    break;
            }

            mapLineX += gridSize;
        }
        mapLineY += gridSize;
    }

    private void mapInfoGenerator(Scanner reader) {
        if (reader.hasNextLine()) {
            this.level = reader.nextInt();
            sizeY = reader.nextInt();
            sizeX = reader.nextInt();

            mapLineX = 0;
            mapLineY = 0;
        }
    }
}
