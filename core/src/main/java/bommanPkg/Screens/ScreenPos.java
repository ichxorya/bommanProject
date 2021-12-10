package bommanPkg.Screens;

public class ScreenPos {
    private float x;
    private float y;

    /** ScreenPos Constructor. */
    public ScreenPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** Getter for x. */
    public float getX() {
        return x;
    }

    /** Getter for y. */
    public float getY() {
        return y;
    }

    /** Setter for x. */
    public void setX(float x) {
        this.x = x;
    }

    /** Setter for y. */
    public void setY(float y) {
        this.y = y;
    }

    /** Setter for x and y. */
    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** Move right by x. */
    public void moveRight(float x) {
        this.x += x;
    }

    /** Move left by x. */
    public void moveLeft(float x) {
        this.x -= x;
    }

    /** Move up by y. */
    public void moveUp(float y) {
        this.y += y;
    }

    /** Move down by y. */
    public void moveDown(float y) {
        this.y -= y;
    }

    /** To string. */
    public String toString() {
        return "ScreenPos: (" + x + ", " + y + ")";
    }
}
