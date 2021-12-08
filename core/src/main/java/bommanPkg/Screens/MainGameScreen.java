package bommanPkg.Screens;

import bommanPkg.Entities.Derived.LivingEntities.Base.LivingEntity;
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
//        wall1 = new Wall(300, 236, mainStage);
        wall2 = new Wall(300, 364, mainStage);
//        wall3 = new Wall(236, 300, mainStage);
        wall4 = new Wall(364, 300, mainStage);

//        wall3.setVisible(false);
//        bot.setPosition(wall3.getX() - 128, wall3.getY() - 64);
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        bot.act(dt);

        player.preventOverlapBlock(wall1);
        wallTest(bot, wall1, wall2, wall3, wall4);
    }

    private void wallTest(LivingEntity entity, Wall ... walls) {
        for (Wall wall : walls) {
            entity.preventOverlapBlock(wall);
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
}
