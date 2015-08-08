package finalStates;

/**
 * Created by kirill.sidorchuk on 8/7/2015.
 */
public class Field {

    private int[] array;
    private int width;
    private int height;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
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
