package finalStates;

/**
 * @Author Sergey Pensov
 */
public class Pivot {
    public int x;
    public int y;

    public Pivot() {
        x = 0;
        y = 0;
    }

    public Pivot(Pivot pivot) {
        this.x = pivot.x;
        this.y = pivot.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Pivot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pivot{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
