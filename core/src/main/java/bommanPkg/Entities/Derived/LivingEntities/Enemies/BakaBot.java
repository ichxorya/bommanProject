package bommanPkg.Entities.Derived.LivingEntities.Enemies;

import bommanPkg.Entities.Derived.LivingEntities.Base.AI.Baka_AI;
import bommanPkg.Maps.GameMap;
import bommanPkg.Screens.ScreenPos;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BakaBot extends Baka_AI {
    final static int entityID = 4;
    /**
     * BakaBot Variables.
     */

    /**
     * Resource Paths.
     */
    String bakaLive = "sprites/enemies/bakabot_live.png";
    String bakaDead = "sprites/enemies/bakabot_dead.png";

    /**
     * Animations.
     */
    private Animation<TextureRegion> live;
    private Animation<TextureRegion> dead;

    /**
     * Constructor (grid-map).
     */
    public BakaBot(float x, float y, Stage s, int gridX, int gridY) {
        super(x, y, s, gridX, gridY);
        oldScreenPos = new ScreenPos(x, y);
        System.out.println("BakaBot" + getGridPosX() + " " + getGridPosY());
        setupAnimations();
        wakeup(1);
    }


    private void setupAnimations() {
        live = loadAnimationFromSheet(bakaLive, 1, 10, frameDuration * 1.3f, true);
        dead = loadAnimationFromSheet(bakaDead, 1, 6, frameDuration, false);
        setAnimation(live);
    }

    @Override
    protected void wakeup(int lives) {
        this.lives = lives;
        this.isMoving = false;

        currentDirection = Direction.getRandom();
    }

    @Override
    public void act(float delta, GameMap gameMap) {
        super.act(delta, gameMap);
        System.out.println("BakaBot: " + getGridPosX() + " " + getGridPosY());

        if (!isDead) {
            if (touchedByDeath(gameMap)) {
                die();
            }
            boolean isValidDirection = validDirection(currentDirection, gameMap);
            if (getElapsedTime() > 2f) {
                if (!isValidDirection || currentDirection == Direction.NONE) {
                    currentDirection = Direction.getRandom();
                    resetElapsedTime();
                } else {
                    isMoving = true;
                    moveToDirection(currentDirection, gameMap);
                    gameMap.getGridMap()[getGridPosX()][getGridPosY()] = entityID;
                }
            }
        } else {
            setAnimation(dead);
        }
    }

    @Override
    protected void setDirection(Direction dir) {
        currentDirection = dir;
    }

    /**
     * Deadly move.
     */
    @Override
    protected boolean touchedByDeath(GameMap gameMap) {
        return gameMap.getGridMap()[getGridPosX()][getGridPosY()] == -2;
    }
}
