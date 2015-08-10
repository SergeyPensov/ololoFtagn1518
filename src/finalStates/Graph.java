package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class Graph {
    public final int width;
    public final int height;
    public final Node[] array;

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        array = new Node[width*height*6];
    }

}
