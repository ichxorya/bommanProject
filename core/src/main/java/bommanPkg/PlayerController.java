package bommanPkg;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class PlayerController implements InputProcessor {
    /**
     *  **************************************************
     *  Variables.
     *  **************************************************
     */
    private final static String TAG = PlayerController.class.getSimpleName();
    private static HashMap<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();
    private static HashMap<Mouse, Boolean> mouseButtons = new HashMap<PlayerController.Mouse, Boolean>();

    // Hash Maps initialization
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
    }

    static {
        mouseButtons.put(Mouse.SELECT, false);
        mouseButtons.put(Mouse.DO_ACTION, false);
    }

    private Entity player;
    private Vector3 lastMouseCoordinates;

    /* Methods */
    /**
     *  **************************************************
     *  Key-released methods.
     *  **************************************************
     */
    public PlayerController(Entity player) {
        this.player = player;
        this.lastMouseCoordinates = new Vector3();
    }

    /**
     *  **************************************************
     *  Key-pressed methods.
     *  **************************************************
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            this.leftPressed();
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            this.rightPressed();
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            this.upPressed();
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            this.downPressed();
        }
        if (keycode == Input.Keys.Q) {
            this.quitPressed();
        }

        return true;
    }

    public void leftPressed() {
        keys.put(Keys.LEFT, true);
    }

    public void rightPressed() {
        keys.put(Keys.RIGHT, true);
    }

    public void upPressed() {
        keys.put(Keys.UP, true);
    }

    public void downPressed() {
        keys.put(Keys.DOWN, true);
    }

    public void quitPressed() {
        keys.put(Keys.QUIT, true);
    }

    /**
     *  **************************************************
     *  Key-released methods.
     *  **************************************************
     */
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            this.leftReleased();
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            this.rightReleased();
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            this.upReleased();
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            this.downReleased();
        }
        if (keycode == Input.Keys.Q) {
            this.quitReleased();
        }

        return true;
    }

    public void leftReleased() {
        keys.put(Keys.LEFT, false);
    }

    public void rightReleased() {
        keys.put(Keys.RIGHT, false);
    }

    public void upReleased() {
        keys.put(Keys.UP, false);
    }

    public void downReleased() {
        keys.put(Keys.DOWN, false);
    }

    public void quitReleased() {
        keys.put(Keys.QUIT, false);
    }

    // TODO
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Mouse-clicked methods.
     */
    // TODO: Reformat
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT) {
            this.setClickedMouseCoordinates(screenX, screenY);
        }

        // Left is selection, right is context menu
        if (button == Input.Buttons.LEFT) {
            this.selectMouseButtonPressed(screenX, screenY);
        }
        if (button == Input.Buttons.RIGHT) {
            this.doActionMouseButtonPressed(screenX, screenY);
        }

        return true;
    }

    public void setClickedMouseCoordinates(int screenX, int screenY) {
        lastMouseCoordinates.set(screenX, screenY, 0);
    }

    public void selectMouseButtonPressed(int screenX, int screenY) {
        mouseButtons.put(Mouse.SELECT, true);
    }

    public void doActionMouseButtonPressed(int screenX, int screenY) {
        mouseButtons.put(Mouse.DO_ACTION, true);
    }

    /**
     * Mouse-released method.
     */
    // TODO: Reformat
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Left is selection, right is context menu
        if (button == Input.Buttons.LEFT) {
            this.selectMouseButtonReleased(screenX, screenY);
        }
        if (button == Input.Buttons.RIGHT) {
            this.doActionMouseButtonReleased(screenX, screenY);
        }

        return true;
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

    enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT
    }

    enum Mouse {
        SELECT, DO_ACTION
    }
}
