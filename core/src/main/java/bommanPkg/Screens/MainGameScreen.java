package bommanPkg.Screens;

import bommanPkg.Entities.Derived.LivingEntities.Enemies.BakaBot;
import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import bommanPkg.Entities.Derived.MapEntities.Derived.Wall;

public class MainGameScreen extends MyScreen {
    BakaBot bot;
    Player player;
    Wall wall1;
    Wall wall2;
    Wall wall3;
    Wall wall4;

    @Override
    public void initialize() {
        bot = new BakaBot(300, 300, mainStage);
        player = new Player(200, 200, mainStage);
        wall1 = new Wall(300, 236, mainStage);
        wall2 = new Wall(300, 364, mainStage);
        wall3 = new Wall(236, 300, mainStage);
        wall4 = new Wall(364, 300, mainStage);
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        bot.act(dt);

        player.preventOverlapBlock(wall1);
        bot.preventOverlapBlock(wall1);
        bot.preventOverlapBlock(wall2);
        bot.preventOverlapBlock(wall3);
        bot.preventOverlapBlock(wall4);
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
