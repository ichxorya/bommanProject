package bommanPkg.Entities.Base;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class EntityV2 extends EntityV1 {

    protected Vector2 velocityVector;
    protected Vector2 accelerationVector;
    protected float acceleration;
    protected float deceleration;
    protected float maxSpeed;

    /**
     * Constructor.
     */
    public EntityV2(float x, float y, Stage s) {
        super(x, y, s);

        velocityVector = new Vector2(0, 0);
        accelerationVector = new Vector2(0, 0);
        acceleration = 0;
        deceleration = 0;
        maxSpeed = 1000;
    }


    /**
     * Set position at center.
     */
    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(EntityV2 other) {
        centerAtPosition(other.getX() + other.getWidth() / 2, other.getY() + other.getHeight() / 2);
    }

    /**
     * Setter: Opacity.
     */
    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }

    /**
     * Velocity/Speed and Acceleration Methods.
     */
    public void setSpeed(float speed) {
        // If length is zero, then assume motion angle is zero degrees
        if (velocityVector.len() == 0)
            velocityVector.set(speed, 0);
        else
            velocityVector.setLength(speed);
    }

    public float getSpeed() {
        return velocityVector.len();
    }

    public float getMotionAngle() {
        return velocityVector.angleDeg();
    }

    public void setMotionAngle(float angle) {
        velocityVector.setAngleDeg(angle);
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVector.add(new Vector2(acceleration, 0).setAngleDeg(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Apply Physics.
     */
    public void applyPhysics(float dt) {
        // Apply Acceleration
        velocityVector.add(
                accelerationVector.x * dt,
                accelerationVector.y * dt
        );

        float speed = getSpeed();

        // Decelerate
        if (accelerationVector.len() == 0) {
            speed -= deceleration * dt;
        }

        // Keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // Update velocity
        setSpeed(speed);

        // Apply velocity
        moveBy(velocityVector.x * dt, velocityVector.y * dt);

        // Reset acceleration
        accelerationVector.set(0, 0);
    }

    /**
     * Overlap check.
     */
    public boolean overlaps(EntityV2 other) {
        Polygon polygon1 = this.getBoundaryPolygon();
        Polygon polygon2 = other.getBoundaryPolygon();

        if (!overlapsBoundingRectangle(polygon1, polygon2))
            return false;

        return Intersector.overlapConvexPolygons(polygon1, polygon2);
    }

    private boolean overlapsBoundingRectangle(Polygon polygon1, Polygon polygon2) {
        return polygon1.getBoundingRectangle().overlaps(
                polygon2.getBoundingRectangle()
        );
    }

    /**
     * Prevent Overlap
     */
    public void preventOverlap(EntityV2 other) {
        Polygon polygon1 = this.getBoundaryPolygon();
        Polygon polygon2 = other.getBoundaryPolygon();

        if (!overlapsBoundingRectangle(polygon1, polygon2))
            return;
        MinimumTranslationVector mtv = new MinimumTranslationVector();

        boolean polygonOverlap = Intersector.overlapConvexPolygons(polygon1, polygon2, mtv);

        if (!polygonOverlap)
            return;
        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
    }
}
