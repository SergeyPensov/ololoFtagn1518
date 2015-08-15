package finalStates;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by kirill.sidorchuk on 8/7/2015.
 */
public class Problem {
    public int id;
    public Unit[] units;
    public transient Board[] unitBoards;
    public int width;
    public int height;
    public Point[] filled;
    public int sourceLength;
    public int[] sourceSeeds;

    public static Problem read(String json) {
        Gson gson = new Gson();
        Problem result = gson.fromJson(json, Problem.class);

        // initializing problem
        result.unitBoards = new Board[result.units.length];
        int i=0;
        for (Unit unit : result.units) {
            result.unitBoards[i] = new Board(unit);
            i++;
        }

        // analyzing all units to detect symmetry
        final Point oddStart = new Point(0,1);
        for (Unit unit : result.units) {

            ArrayList<Integer> evenAngles = new ArrayList<>(6);

            evenAngles.add(0);

            Unit[] evenRotations = new Unit[6];
            for( int a=0; a<6; ++a) {
                evenRotations[a] = Board.transform(unit, new UnitState(Point.zero, a));

                if(a>0){
                    boolean dup=false;
                    for( int a2=0; a2<a; a2++) {
                        if( evenRotations[a2].equals(evenRotations[a])) {
                            dup = true;
                            break;
                        }
                    }
                    if( !dup) evenAngles.add(a);
                }
            }

            unit.maxAngle = evenAngles.size();
        }

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

        for (Point p : filled) {
            board.setCell(p.x,p.y,1);
        }

        return board;
    }

    public int[] getUnitsForTheGame(int seed) {
        final RandomGenerator randomGenerator = new RandomGenerator(seed);
        int[] result = new int[sourceLength];
        for( int i=0; i<sourceLength; ++i) {
            int index = randomGenerator.generate() % units.length;
            result[i] = index;
        }
        return result;
    }
}
