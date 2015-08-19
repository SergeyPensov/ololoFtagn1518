package finalStates;

import sun.dc.pr.PRError;
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

    public static final int MAX_BEAM_SEARCH_DEPTH = 15;
    public static final int MAX_BEAM_WIDTH = 8;
    public static final int BEAM_SEARCH_GOAL_LINES_KILLED = 2;
    public static final int THREAD_COUNT = 8;

    private Problem problem;
    private String path;
    private final boolean saveImages;

    private static class PlayResult {
        final String sequence;
        final int nextUnitIndex;

        public PlayResult(String sequence, int nextUnitIndex) {
            this.sequence = sequence;
            this.nextUnitIndex = nextUnitIndex;
        }
    }

    public Solver(Problem problem, String path, boolean saveImages) {
        this.problem = problem;
        this.path = path;
        this.saveImages = saveImages;
    }

    public SolverResult solve(int seed) throws Exception {

        System.out.println("Solving for seed " + seed);

        // creating all units
        final int[] unitsForTheGame = problem.getUnitsForTheGame(seed);
        final Unit[] allUnits = new Unit[unitsForTheGame.length];
        int index = 0;
        for (int i : unitsForTheGame) {
            allUnits[index] = problem.units[i];
            ++index;
        }

        Board board = problem.getBoard();
        StringBuilder sb = new StringBuilder(1024);
        int unitIndex = 0;
        while (unitIndex < unitsForTheGame.length) {
            PlayResult pr = play(board, allUnits, unitIndex, seed);
            if (pr == null || pr.sequence == null) break; // GAME OVER
            sb.append(pr.sequence);
            unitIndex = pr.nextUnitIndex;
        }

        System.out.println("score=" + board.score);

        final String solution = sb.toString();
        return new SolverResult(problem.id, seed, "", solution);
    }

    private PlayResult play(Board board, Unit[] units, int currentUnitIndex, int seed) throws Exception {

        Unit unit = units[currentUnitIndex];
        UnitState spawnState = board.getSpawnState(unit);

        drawFrame(board, unit, currentUnitIndex, spawnState, 0, seed, null);

        boolean live = board.isValid(unit, spawnState);
        if (!live) return null; // GAME OVER - spawn location is not valid

        // searching for all "locked" states for the unit
        final OptimalUnitPosition startPosition = new OptimalUnitPosition(spawnState, null, 0);
        FindFinalStates findFinalStates = new FindFinalStates(board, units, currentUnitIndex, startPosition);
        ArrayList<OptimalUnitPosition> optimalUnitPositions =
                findFinalStates.getOptimalPositionInMap(
                        MAX_BEAM_SEARCH_DEPTH,
                        MAX_BEAM_WIDTH,
                        BEAM_SEARCH_GOAL_LINES_KILLED,
                        THREAD_COUNT);
        System.out.println("Count of possible positions for unit #" + currentUnitIndex + "=" + optimalUnitPositions.size()
                + ", line kills fulfulled=" + findFinalStates.isKilledLinesFulfilled());

        ArrayList<OptimalUnitPosition> playPositions = new ArrayList<>(3);
        if (findFinalStates.isKilledLinesFulfilled()) {
            // using known positions sequence

            OptimalUnitPosition sequenceStart = null;
            for (OptimalUnitPosition position : optimalUnitPositions) {
                if (position.next != null) {
                    sequenceStart = position;
                    break;
                }
            }

            if (sequenceStart == null) {
                playPositions.add(startPosition.next);
            } else {
                while (sequenceStart != null) {
                    playPositions.add(sequenceStart);
                    sequenceStart = sequenceStart.next;
                }
            }

            System.out.println("Sequence length to fulfull goal is " + playPositions.size());
        } else {
            // using best scored position

            // sorting on heuristic score, added score is main
//            Collections.sort(optimalUnitPositions,
//                    (o1, o2) -> (o2.score+(o2.posScore.gameScore-board.score) *1000) - (o1.score+(o1.posScore.gameScore-board.score) *1000));
            Collections.sort(optimalUnitPositions, (o1, o2) -> (o2.score - o1.score));

            playPositions.add(optimalUnitPositions.get(0));
        }

        // playing found sequence
        int moveIndex = 1;
        StringBuilder sequenceSb = new StringBuilder(100);
        for (OptimalUnitPosition position : playPositions) {

            // fetching current unit and testing if game can continue
            unit = units[currentUnitIndex];
            spawnState = board.getSpawnState(unit);

            drawFrame(board, unit, currentUnitIndex, spawnState, 0, seed, null);
            if (!board.isValid(unit, spawnState))
                throw new RuntimeException("playing sequence of positions leads to game end");

            final ArrayList<Command> commands = UnitPositionSearcher.searchPath(board, unit, spawnState, position.state);
            if (commands == null || commands.size() == 0)
                throw new RuntimeException("path to searched position not found");

//            ThreeNode threeNode = findFinalStates.getAllPath(position, unit, board);
//            if( threeNode == null) throw new RuntimeException("failed to find path to position");
//
//            // generating commands
//            ArrayList<ThreeNode> nodes = FindFinalStates.getShortPath(threeNode.finalThreeNode);
//            System.out.println("Moves for unit #" + currentUnitIndex + "=" + nodes.size());
//
//            List<Command> commands = new ArrayList<>(100);
//            for (int i = nodes.size() - 1; 0 <= i; i--) {
//                state = nodes.get(i).state;
//
//                commands.add(nodes.get(i).command);
////            if (saveImages)  drawFrame(board, unit, currentUnitIndex, state, moveIndex++, seed, nodes.get(i).command);
//            }

//            if( saveImages) {
//                UnitState curState = spawnState;
//                for (Command command : commands) {
//                    curState = command.apply(curState);
//                    drawFrame(board, unit, currentUnitIndex, curState, moveIndex++, seed, command);
//                }
//            }

            // generating lock command
            boolean lockFound = false;
            for (Command command : Command.commands) {
                if (!board.isValid(unit, command.apply(position.state))) {
                    lockFound = true;
                    commands.add(command);
                    break;
                }
            }
            if (!lockFound) throw new RuntimeException("lock command cannot be found!");

            // updating the board with locked unit
            board.updateBoard(unit, position.state);
            System.out.println("score = " + board.score);
            if (saveImages) drawFrame(board, null, currentUnitIndex, position.state, moveIndex + 1, seed, null);

            sequenceSb.append(Command.encode(commands));
            currentUnitIndex++;
        }

        return new PlayResult(sequenceSb.toString(), currentUnitIndex);
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
