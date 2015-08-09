package finalStates;

/**
 * @Author Sergey Pensov
 */
public class HexMap {
    private int[][] map;
    private int height;
    private int width;

    public HexMap(int width, int height, Point[] filled) {
        this.map = new int[width][height];
        for (Point fill : filled) {
            map[fill.getX()][fill.getY()] = Constants.FILL_NUMBER;
        }
        this.height = height;
        this.width = width;
    }

    public int[][] getMap() {
        return map;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
