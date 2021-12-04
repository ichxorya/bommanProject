package bommanPkg.entities;

import bommanPkg.entities.components.Component;
import bommanPkg.entities.components.GraphicsComponent;
import bommanPkg.entities.components.InputComponent;
import bommanPkg.entities.components.PhysicsComponent;
import bommanPkg.maps.MapManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.*;

public class Entity {
    public static final int FRAME_WIDTH = 16;
    public static final int FRAME_HEIGHT = 16;
    private static final String TAG = Entity.class.getSimpleName();
    private static final int MAX_COMPONENTS = 5;
    private Json json;
    private EntityConfig entityConfig;
    private Array<Component> components;
    private InputComponent inputComponent;
    private GraphicsComponent graphicsComponent;
    private PhysicsComponent physicsComponent;

    public Entity(InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        entityConfig = new EntityConfig();
        json = new Json();
        components = new Array<Component>(MAX_COMPONENTS);

        this.inputComponent = inputComponent;
        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;

        this.components.add(this.inputComponent);
        this.components.add(this.physicsComponent);
        this.components.add(this.graphicsComponent);
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    // Note: Can be understood roughly as String... args = String[] args.
    // The keyword is Varargs.
    public void sendMessage(Component.MESSAGE messageType, String... args) {
        String fullMessage = messageType.toString();

        for (String string : args) {
            fullMessage += Component.MESSAGE_TOKEN + string;
        }

        for (Component component : components) {
            component.receiveMessage(fullMessage);
        }
    }

    public void update(MapManager mapManager, Batch batch, float delta){
        inputComponent.update(this, delta);
        physicsComponent.update(this, mapManager, delta);
        graphicsComponent.update(this, mapManager, batch, delta);
    }

    public void dispose() {
        for (Component component : components) {
            component.dispose();
        }
    }

    public Rectangle getCurrentBoundingBox() {
        return physicsComponent.boundingBox;
    }

    public void setEntityConfig(EntityConfig entityConfig){
        this.entityConfig = entityConfig;
    }

    public static EntityConfig getEntityConfig(String configFilePath){
        Json json = new Json();
        return json.fromJson(EntityConfig.class, Gdx.files.internal(configFilePath));
    }

    public static Array<EntityConfig> getEntityConfigs(String configFilePath) {
        Json json = new Json();
        Array<EntityConfig> configs = new Array<EntityConfig>();

        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(configFilePath));
        for (JsonValue jsonVal : list) {
            configs.add(json.readValue(EntityConfig.class, jsonVal));
        }

        return configs;
    }

    /**
     * Enums
     */
    public static enum Direction {
        UP, RIGHT, DOWN, LEFT;

        // Random Direction
        // Source: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
        private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Direction getRandom() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        // Opposite Direction
        public Direction getOpposite() {
            switch (this) {
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
                default:
                    throw new IllegalArgumentException("Invalid Direction");
            }
        }
    }

    public static enum State {
        IDLE, WALKING, IMMOBILE;

        // Random State
        private static final List<State> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        // Get Random State, ignore the last state (IMMOBILE)
        public static State getRandom() {
            return VALUES.get(RANDOM.nextInt(SIZE - 1));
        }
    }

    public static enum AnimationType {
        WALK_LEFT, WALK_RIGHT, WALK_UP, WALK_DOWN,
        IDLE, IMMOBILE
    }
}
