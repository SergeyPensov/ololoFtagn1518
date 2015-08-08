package finalStates;

/**
 * @Author Sergey Pensov
 */
public class HexMap {
    private int[][] map;

    public HexMap(int width, int height, Pivot[] filled) {
        this.map = new int[width][height];
        for (Pivot fill : filled) {
            map[fill.getX()][fill.getY()] = Constants.FILL_NUMBER;
        }
    }
}
