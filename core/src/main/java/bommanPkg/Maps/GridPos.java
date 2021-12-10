package bommanPkg.Maps;

public class GridPos {
    private int x;
    private int y;

    /** GridPos constructor. */
    public GridPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Getter for x. */
    public int getX() {
        return x;
    }

    /** Getter for y. */
    public int getY() {
        return y;
    }

    /** Setter for x. */
    public void setX(int x) {
        this.x = x;
    }

    /** Setter for y. */
    public void setY(int y) {
        this.y = y;
    }

    /** Returns a string representation of the GridPos. */
    public String toString() {
        return "GridPos: (" + x + ", " + y + ")";
    }
}
