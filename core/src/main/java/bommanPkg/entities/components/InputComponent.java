package bommanPkg.entities.components;

import bommanPkg.entities.Entity;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public abstract class InputComponent implements Component {
    /**
     * **************************************************
     * Constructor and Update method.
     * **************************************************
     */
    public InputComponent() {
        json = new Json();
    }

    public abstract void update(Entity entity, float delta);

    /**
     * **************************************************
     * HashMaps.
     * **************************************************
     */
    protected static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    protected static Map<Mouse, Boolean> mouseButtons = new HashMap<Mouse, Boolean>();

    // Initialize the hashmap for inputs
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.QUIT, false);
    }

    static {
        mouseButtons.put(Mouse.SELECT, false);
        mouseButtons.put(Mouse.DO_ACTION, false);
    }

    /**
     * **************************************************
     * Variables.
     * **************************************************
     */
    protected Entity.Direction currentDirection = null;
    protected Entity.State currentState = null;
    protected Json json;

    /**
     * **************************************************
     * Enums.
     * **************************************************
     */
    protected enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT
    }

    protected enum Mouse {
        SELECT, DO_ACTION
    }

}