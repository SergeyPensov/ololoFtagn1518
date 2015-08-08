package finalStates;

import com.google.gson.Gson;
import http.APIServer;

import java.util.Arrays;

/**
 * Created by kirill.sidorchuk on 8/7/2015.
 */
public class Problem {
    int id;
    Unit[] units;
    int width;
    int height;
    Pivot[] filled;
    int sourceLength;
    int[] sourceSeeds;

    public static Problem read(String json) {
        Gson gson = new Gson();
        Problem result = gson.fromJson(json, Problem.class);
        return result;
    }

    public static void main(String[] args) {
        final String json = "{\"height\":15,\"width\":15,\"sourceSeeds\":[0],\"units\":[{\"members\":[{\"x\":0,\"y\":0}],\"pivot\":{\"x\":0,\"y\":0}}],\"id\":1,\"filled\":[{\"x\":2,\"y\":4},{\"x\":3,\"y\":4},{\"x\":4,\"y\":4},{\"x\":5,\"y\":4},{\"x\":6,\"y\":4},{\"x\":11,\"y\":4},{\"x\":2,\"y\":5},{\"x\":8,\"y\":5},{\"x\":11,\"y\":5},{\"x\":2,\"y\":6},{\"x\":11,\"y\":6},{\"x\":2,\"y\":7},{\"x\":3,\"y\":7},{\"x\":4,\"y\":7},{\"x\":8,\"y\":7},{\"x\":11,\"y\":7},{\"x\":2,\"y\":8},{\"x\":9,\"y\":8},{\"x\":11,\"y\":8},{\"x\":2,\"y\":9},{\"x\":8,\"y\":9},{\"x\":2,\"y\":10},{\"x\":3,\"y\":10},{\"x\":4,\"y\":10},{\"x\":5,\"y\":10},{\"x\":6,\"y\":10},{\"x\":9,\"y\":10},{\"x\":11,\"y\":10}],\"sourceLength\":100}";
        Problem prob = read(json);
        System.out.println(prob.toString());
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", units=" + Arrays.toString(units) +
                ", width=" + width +
                ", height=" + height +
                ", filled=" + Arrays.toString(filled) +
                ", sourceLength=" + sourceLength +
                ", sourceSeeds=" + Arrays.toString(sourceSeeds) +
                '}';
    }

    /**
     * Returns initial board
     * @return
     */
    public Board getBoard() {
        Board board = new Board(width,height);

        for (Pivot p : filled) {
            board.setCell(p.x,p.y,1);
        }

        return board;
    }
}
