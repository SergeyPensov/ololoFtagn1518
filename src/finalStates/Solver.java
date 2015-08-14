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
    private final boolean saveImages;

    public Solver(Problem problem, String path, boolean saveImages) {
        this.problem = problem;
        this.path = path;
        this.saveImages = saveImages;
    }

    public SolverResult solve(int seed) throws Exception {

        System.out.println("Solving for seed " + seed);


        StringBuilder sb = new StringBuilder(1024);

        Board board = problem.getBoard();

        // creating all units
        final int[] unitsForTheGame = problem.getUnitsForTheGame(seed);
        final Unit[] allUnits = new Unit[unitsForTheGame.length];
        int index =0;
        for( int i: unitsForTheGame) {
            allUnits[index] = problem.units[i];
            ++index;
        }

        for (int f = 0; f < unitsForTheGame.length; ++f) {
            final int unitIndex = unitsForTheGame[f];
            final String sequence = play(board, allUnits, f, seed);
            if (sequence == null) break; // GAME OVER
            sb.append(sequence);
        }

        System.out.println("score=" + board.score);

        final String solution = sb.toString();
        return new SolverResult(problem.id, seed, "", solution);
    }

    private String play(Board board, Unit[] units, int currentUnitIndex, int seed) throws Exception {

        final Unit unit = units[currentUnitIndex];
        final UnitState spawnState = board.getSpawnState(unit);

        drawFrame(board, unit, currentUnitIndex, spawnState, 0, seed, null);

        boolean live = board.isValid(unit, spawnState);
        if (!live) return null; // GAME OVER - spawn location is not valid

        UnitState state = spawnState;
        int moveIndex = 1;

        // searching for all "locked" states for the unit
        FindFinalStates findFinalStates = new FindFinalStates(board, units, currentUnitIndex, null);
        ArrayList<OptimalUnitPosition> optimalUnitPositions = findFinalStates.getOptimalPositionInMap(4, 8, 1, 2);
        System.out.println("Count of possible positions for unit #" + currentUnitIndex + "=" + optimalUnitPositions.size()
        + ", line kills fulfulled=" + findFinalStates.isKilledLinesFulfilled());

        // sorting on combined criterion
        Collections.sort(optimalUnitPositions, (o1, o2) -> (o2.score+o2.posScore.addedScore*2) - (o1.score+o1.posScore.addedScore*2));


        // searching for paths that connects spawn position with locked position
        ThreeNode threeNode = null;
        for (OptimalUnitPosition optimalUnitPosition : optimalUnitPositions) {
            threeNode = findFinalStates.getAllPath(optimalUnitPosition, unit, board);
            if (threeNode != null) {
                System.out.println("using position scored " + optimalUnitPosition.score + ", max added score=" + optimalUnitPosition.posScore.addedScore);
                break;
            }
        }

        if (threeNode == null) {
            System.out.println("path not found");
            return null;
        }
        ArrayList<ThreeNode> nodes = FindFinalStates.getShortPath(threeNode.finalThreeNode);
        System.out.println("Moves for unit #" + currentUnitIndex + "=" + nodes.size());

        List<Command> commands = new ArrayList<>(100);
        for (int i = nodes.size() - 1; 0 <= i; i--) {
            state = nodes.get(i).state;

            commands.add(nodes.get(i).command);
//            if (saveImages)  drawFrame(board, unit, currentUnitIndex, state, moveIndex++, seed, nodes.get(i).command);
        }

        // generating lock command
        boolean lockFound = false;
        for (Command command : Command.commands) {
            if (!board.isValid(unit, command.apply(state))) {
                lockFound = true;
                commands.add(command);
                break;
            }
        }

        if (!lockFound) throw new RuntimeException("lock command cannot be found!");

        // updating the board with locked unit
        board.updateBoard(unit, state);
        System.out.println("score = " + board.score);
        if (saveImages) drawFrame(board, null, currentUnitIndex, state, moveIndex + 1, seed, null);
        return Command.encode(commands);
    }

    private void drawFrame(Board board, Unit unit, int currentUnitIndex, UnitState state, int moveIndex, int seed, Command command) {
        if (!saveImages) return;
        final BufferedImage image = BoardVis.draw(board, unit, state, command);
        final String baseFileName = new File(path).getParent() + "/images/" + new File(path).getName() + "/";
        new File(baseFileName).mkdirs();
        final String fileNmae = String.format("%sseed%d_play_u%03d_i%03d.png", baseFileName, seed, currentUnitIndex, moveIndex);
        try {
            ImageIO.write(image, "png", new File(fileNmae));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SolverResult[] solveAll(int[] seeds) throws Exception {
        SolverResult[] result = new SolverResult[seeds.length];
        for (int i = 0; i < seeds.length; ++i) {
            result[i] = solve(seeds[i]);
        }
        return result;
    }

    public SolverResult[] solveAll() throws Exception {
        return solveAll(problem.sourceSeeds);
    }
}
