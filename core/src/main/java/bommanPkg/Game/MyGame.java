package bommanPkg.Game;

import bommanPkg.Screens.MyScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public abstract class MyGame extends Game {
    private static MyGame game;

    public MyGame() {
        game = this;
    }

    public static void setActiveScreen(MyScreen screen) {
        game.setScreen(screen);
    }
}
