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
        HashMap<UnitState, Integer> unitStates = new HashMap<>();
        for (int j = 0; j < board.height; j++) {
            for (int i = 0; i < board.width; i++) {
                Point testPoint = new Point(i, board.height - j);
                UnitState testState = new UnitState(testPoint, 0, 0, 0);
                if (board.isValid(unit, testState)) {
                    UnitState stableState = new UnitState(testState);
                    int lockCounter = 0;
                    for (int k = 0; k < Command.commands.length; k++) {
                        if (board.isValid(unit, testState.applyCommand(Command.getCommand(k)))) {
                            lockCounter++;
                        }
                        testState = new UnitState(stableState);
                    }
                    unitStates.add(testState);
                }
            }
        }
        return null;
    }


}
