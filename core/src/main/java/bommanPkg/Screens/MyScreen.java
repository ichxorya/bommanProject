package bommanPkg.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;

public abstract class MyScreen implements Screen {
    protected Stage mainStage;
    protected Stage uiStage;

    // Constructor
    public MyScreen() {
        mainStage = new Stage();
        uiStage = new Stage();

        initialize();
    }

    // Initialize the screen
    public abstract void initialize();

    public abstract void update(float dt);

    public void render(float dt) {
        uiStage.act(dt);
        mainStage.act(dt);

        update(dt);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
        uiStage.draw();
    }

    // Methods required by Screen interface
    public void resize(int width, int height) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }

    public void show() {

    }

    public void hide() {

    }
}
