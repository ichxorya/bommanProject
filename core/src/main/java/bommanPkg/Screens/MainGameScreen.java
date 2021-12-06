package bommanPkg.Screens;

import bommanPkg.Entities.Base.EntityV2;
import bommanPkg.Entities.Derived.MovableEntities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;

public class MainGameScreen extends MyScreen {
    Player hero;

    @Override
    public void initialize() {
        EntityV2 background = new EntityV2(0,0, mainStage);
        background.loadTexture("bg.png");
        background.setSize(1200, 960);

        EntityV2.setWorldBounds(background);

        hero = new Player(100, 100, mainStage);
    }

    @Override
    public void update(float dt) {
        hero.alignCamera();

        playerInput();

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
}
