package finalStates;

/**
 * @Author Sergey Pensov
 */
public class Point {
    public int x;
    public int y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(Point pivot) {
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

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point pivot = (Point) o;

        if (x != pivot.x) return false;
        return y == pivot.y;

    }

    @Override
    public int hashCode() {
        int result = y;
        result = 3431 * result + x;
        return result;
    }

    @Override
    public String toString() {
        return "Pivot{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
