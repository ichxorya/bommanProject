package bommanPkg.entities;

import bommanPkg.entities.components.Component;
import bommanPkg.entities.components.npc.NPCGraphicsComponent;
import bommanPkg.entities.components.npc.NPCInputComponent;
import bommanPkg.entities.components.npc.NPCPhysicsComponent;
import bommanPkg.entities.components.players.PlayerGraphicsComponent;
import bommanPkg.entities.components.players.PlayerInputComponent;
import bommanPkg.entities.components.players.PlayerPhysicsComponent;
import com.badlogic.gdx.utils.Json;

public class EntityFactory {
    public static String PLAYER_CONFIG = "scripts/player.json";
    private static Json json = new Json();

    static public Entity getEntity(EntityType entityType) {
        Entity entity = null;
        switch (entityType) {
            case PLAYER:
                entity = new Entity(
                        new PlayerInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent()
                );

                entity.setEntityConfig(
                        Entity.getEntityConfig(EntityFactory.PLAYER_CONFIG)
                );

                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS,
                        json.toJson(entity.getEntityConfig()));
                return entity;

            case DEMO_PLAYER:
                entity = new Entity(
                        new NPCInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent()
                );
                return entity;

            case NPC:
                entity = new Entity(
                        new NPCInputComponent(),
                        new NPCPhysicsComponent(),
                        new NPCGraphicsComponent()
                );
                return entity;

            default:
                throw new IllegalStateException("Unexpected value: " + entityType);
        }
    }

    public static enum EntityType {
        PLAYER,
        DEMO_PLAYER,
        NPC // TODO: FIX TO ENEMY
    }
}

