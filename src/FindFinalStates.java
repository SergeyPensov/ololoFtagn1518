import finalStates.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author Sergey Pensov
 */
public class FindFinalStates {
    private Board board;
    private Unit unit;

    public FindFinalStates(Unit unit, Board board) {
        this.board = board;
        this.unit = unit;
    }

    public UnitState[] getOptimalPositionInMap() {
        ArrayList<OptimalUnitPosition> optimalUnitPositions = new ArrayList<>();
        for (int j = 0; j < board.height; j++) {
            for (int i = 0; i < board.width; i++) {
                Point testPoint = new Point(i, board.height - j - 1);
                UnitState testState = new UnitState(testPoint, 0, 0, 0);
                if (board.isValid(unit, testState)) {
                    int lockCounter = 0;
                    for (int k = 0; k < Command.commands.length; k++) {
                        UnitState state = testState.applyCommand(Command.getCommand(k));
                        if (!board.isValid(unit, state)) {
                            lockCounter++;
                        }
                    }
                    if (lockCounter != 0)
                        optimalUnitPositions.add(new OptimalUnitPosition(new UnitState(testState), lockCounter));

                }
            }
        }
        System.out.println(optimalUnitPositions.toString());
        return null;
    }


}
