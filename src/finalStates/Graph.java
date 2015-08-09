package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class Graph {
    public int width;
    public int height;
    public Node[] array;

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        array = new Node[width*height*6];
    }
}
