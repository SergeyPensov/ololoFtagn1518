package finalStates;

import vis.BoardVis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
            if( sequence == null ) break; // GAME OVER
            sb.append(sequence).append("\n");
        }

        final String solution = sb.toString();
        return new SolverResult(problem.id, seed, "", solution);
    }

    private String play(Board board, Unit unit, Board unitBoard, int[] nextUnits, int currentUnitIndex) {

        // creating graph 
        Graph graph = new Graph(board.width, board.height);

        final UnitState spawnState = board.getSpawnState(unit, unitBoard);

        drawFrame(board, unit, currentUnitIndex, spawnState, 0);

        // playing random
        Set<UnitState> states = new HashSet<>();
        states.add(spawnState);

        Random random = new Random(17);

        boolean live = board.isValid(unit, spawnState);
        if( !live) return null; // GAME OVER - spawn location is not valid

        List<Command> commands = new ArrayList<>(100);

        UnitState state = spawnState;
        int moveIndex = 1;
        while(live) {

            Command command = null;
            UnitState nextState = null;
            int it;
            for( it=10; it>=0; it--) {
                int commandIndex = (random.nextInt() & 0xFF) % 4;
                command = Command.getCommand(commandIndex);
                nextState = command.apply(state);
                if( !states.contains(nextState)) break;
            }

            if( it < 0 ) {
                // cannot find good command
                // issuing this command will result in game error and score = 0
                System.out.println("Failed to find valid command");
            }

            commands.add(command);
            states.add(nextState);
            live = board.isValid(unit, nextState);

            if( live ) {
                state = nextState; // updating state if unit is not locked

                drawFrame(board, unit, currentUnitIndex, state, moveIndex);
                moveIndex++;
            }
        }

        // updating the board with locked unit
        board.updateBoard(unit, state);

        return Command.encode(commands);
    }

    private void drawFrame(Board board, Unit unit, int currentUnitIndex, UnitState state, int moveIndex) {
        final BufferedImage image = BoardVis.draw(board, unit, state);
        final String fileNmae = String.format("%s_play_u%03d_i%03d.png", path, currentUnitIndex, moveIndex);
        try {
            ImageIO.write(image, "png", new File(fileNmae));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
