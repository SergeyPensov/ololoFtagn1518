package finalStates;

/**
 * Created by kirill on 8/8/2015.
 */
public class Board {

    private static final float R = 1.0f/(float)Math.sqrt(3);
    private static final float DY = R * 1.5f;

    public int width;
    public int height;
    private int[] array;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width*height];
    }

    public static FPoint getCoordsForIndexes(final Pivot index) {
        float x = index.x;
        if( 1 == (index.y & 1)) x += 0.5f;
        float y = index.y * DY;
        return new FPoint(x, y);
    }

    public static Pivot getIndexForCoordinates(final FPoint point) {
        int j = Math.round(point.y / DY);
        int i = Math.round( (1==(j&1)) ? point.x-0.5f : point.x);
        return new Pivot(i,j);
    }

    int readCell( int i, int j) {
        final int index = getIndex(i, j);
        return array[index];
    }

    void setCell( int i, int j, int value) {
        final int index = getIndex(i, j);
        array[index] = value;
    }

    private int getIndex(int i, int j) {
        if( i < 0 || i >= width || j < 0 || j >= height ) throw new IllegalArgumentException("indexes out of bounds");
        return i + j*width;
    }

}
