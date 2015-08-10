package finalStates;

import vis.BoardVis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

        System.out.println("Solving for seed " + seed);

        final int[] unitsForTheGame = problem.getUnitsForTheGame(seed);

        StringBuilder sb = new StringBuilder(1024);

        Board board = problem.getBoard();

        for (int f = 0; f < unitsForTheGame.length; ++f) {
            final int unitIndex = unitsForTheGame[f];
            final Unit unit = problem.units[unitIndex];
            final Board unitBoard = problem.unitBoards[unitIndex];
            final int[] nextUnits = Arrays.copyOfRange(unitsForTheGame, f + 1, unitsForTheGame.length);
            final String sequence = play(board, unit, unitBoard, nextUnits, f, seed);
            if (sequence == null) break; // GAME OVER
            sb.append(sequence).append("\n");
        }

        System.out.println("score=" + board.score);

        final String solution = sb.toString();
        return new SolverResult(problem.id, seed, "", solution);
    }

    private Command findDirection(Board board, Unit unit, UnitState startState, UnitState endState, int depth, Set<UnitState> visitedStates) {

        if (startState.equals(endState)) {
            // locking
            for (Command command : Command.commands) {
                if (!board.isValid(unit, command.apply(startState))) {
                    return command;
                }
            }

        }

        for (Command com : Command.commands) {
            if (com.apply(startState).equals(endState)) {
                return com;
            }
        }

        if (depth == 0) return null;

        for (Command com : Command.commands) {
            final UnitState nextState = com.apply(startState);
            if (visitedStates.contains(nextState) == false && board.isValid(unit, nextState)) {
                if (null != findDirection(board, unit, nextState, endState, depth - 1, visitedStates)) {
                    return com;
                }
            }
        }

        return null;
    }

    private String play(Board board, Unit unit, Board unitBoard, int[] nextUnits, int currentUnitIndex, int seed) {

        // creating graph 


        final UnitState spawnState = board.getSpawnState(unit, unitBoard);

        drawFrame(board, unit, currentUnitIndex, spawnState, 0, seed);

        // playing random
        Set<UnitState> states = new HashSet<>();
        states.add(spawnState);

        Random random = new Random(17);

        boolean live = board.isValid(unit, spawnState);
        if (!live) return null; // GAME OVER - spawn location is not valid

        List<Command> commands = new ArrayList<>(100);

        // searching for good lock states
        UnitState goodDestination = null;
        for (int y = board.height - 1; y >= 0 && goodDestination == null; y--) {
            for (int x = 0; x < board.width; ++x) {
                UnitState destination = new UnitState(new Point(x, y), 0, board.width, board.height);
                if (board.isValid(unit, destination)) {
                    goodDestination = destination;
                    break;
                }
            }
        }

        UnitState state = spawnState;
        int moveIndex = 1;
        FindFinalStates findFinalStates = new FindFinalStates(unit, board);
        ArrayList<OptimalUnitPosition> optimalUnitPositions = findFinalStates.getOptimalPositionInMap();
        ThreeNode threeNode = null;
        for (OptimalUnitPosition optimalUnitPosition : optimalUnitPositions) {
            threeNode = findFinalStates.getAllPath(optimalUnitPosition, unit, board, unitBoard);
            if (threeNode != null) {
                break;
            }

        }
        if (threeNode == null) throw new RuntimeException("Problem with threeNode");
        ArrayList<ThreeNode> nodes = FindFinalStates.getShortPath(threeNode.finalThreeNode);
        for (int i = nodes.size() - 1; 0 <= i; i--) {
            state = nodes.get(i).state;
            drawFrame(board, unit, currentUnitIndex, state, moveIndex++, seed);
        }

        // updating the board with locked unit
        board.updateBoard(unit, state);
        drawFrame(board, null, currentUnitIndex, state, moveIndex + 1, seed);

        return Command.encode(commands);
    }

    private void drawFrame(Board board, Unit unit, int currentUnitIndex, UnitState state, int moveIndex, int seed) {
        final BufferedImage image = BoardVis.draw(board, unit, state);
        final String fileNmae = String.format("%s_seed%d_play_u%03d_i%03d.png", path, seed, currentUnitIndex, moveIndex);
        try {
            ImageIO.write(image, "png", new File(fileNmae));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SolverResult[] solveAll(int[] seeds) {
        SolverResult[] result = new SolverResult[seeds.length];
        for (int i = 0; i < seeds.length; ++i) {
            result[i] = solve(seeds[i]);
        }
        return result;
    }

    public SolverResult[] solveAll() {
        return solveAll(problem.sourceSeeds);
    }
}
