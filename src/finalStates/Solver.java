package finalStates;

import vis.BoardVis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class Solver {

    private Problem problem;
    private String path;

    public Solver(Problem problem, String path) {
        this.problem = problem;
        this.path = path;
    }

    public SolverResult solve(int seed) {

        final int[] unitsForTheGame = problem.getUnitsForTheGame(seed);

        StringBuilder sb = new StringBuilder(1024);

        Board board = problem.getBoard();

        for( int f=0; f<unitsForTheGame.length; ++f) {
            final int unitIndex = unitsForTheGame[f];
            final Unit unit = problem.units[unitIndex];
            final Board unitBoard = problem.unitBoards[unitIndex];
            final int[] nextUnits = Arrays.copyOfRange(unitsForTheGame, f + 1, unitsForTheGame.length);
            final String sequence = play(board, unit, unitBoard, nextUnits, f);
            sb.append(sequence).append("\n");
        }

        final String solution = sb.toString();
        return new SolverResult(problem.id, seed, "", solution);
    }

    private String play(Board board, Unit unit, Board unitBoard, int[] nextUnits, int currentUnitIndex) {

        // creating graph 
        Graph graph = new Graph(board.width, board.height);

        final UnitState spawnState = board.getSpawnState(unit, unitBoard);

        final BufferedImage image = BoardVis.draw(board, unit, spawnState);
        final String fileNmae = String.format("%s_play_u%2d_i%03d.png", path, currentUnitIndex, 0);
        try {
            ImageIO.write(image, "png", new File(fileNmae));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "b";
    }

    public SolverResult[] solveAll(int[] seeds) {
        SolverResult[] result = new SolverResult[seeds.length];
        for( int i=0; i<seeds.length; ++i) {
            result[i] = solve(seeds[i]);
        }
        return result;
    }

    public SolverResult[] solveAll() {
        return solveAll(problem.sourceSeeds);
    }
}
