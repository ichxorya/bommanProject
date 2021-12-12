package bommanPkg.Screens;

import bommanPkg.Entities.Base.GUIEntity;
import bommanPkg.Game.BommanProject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MainMenuScreen extends MyScreen {
    @Override
    public void initialize() {
        GUIEntity background = new GUIEntity(0, 0, mainStage, 1200, 960);
        background.loadTexture("mainmenu.png");

        GUIEntity title = new GUIEntity(600, 400, mainStage, 400, 100);
        title.loadTexture("bg.png");
        title.centerAtPosition(600, 400);

        GUIEntity start = new GUIEntity(0, 0, mainStage, 400, 100);
        start.loadTexture("startbutton.png");
        start.centerAtPosition(400, 300);
        start.moveBy(0, -100);
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            BommanProject.setActiveScreen(new MainGameScreen());
    }
}
